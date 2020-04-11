package com.edudb.bdude.db;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.location.LocationHelper;
import com.edudb.bdude.session.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DatabaseController {

    private static DatabaseController mFirebaseDbInstance;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Inject
    DatabaseController() {
    }

    public static DatabaseController getInstance() {
        if (mFirebaseDbInstance == null) {
            mFirebaseDbInstance = new DatabaseController();
        }
        return mFirebaseDbInstance;
    }

    public void createRequest(HelpRequest post, IExecutable<Void> listener) {

        DocumentReference dr = db.collection("requests").document();
        post.setObjectID(dr.getId());
        dr.set(post).addOnSuccessListener(snapshot -> {
            listener.execute(null);
        });
    }

//    public void getAllRequestsList(IExecutable<List<HelpRequest>> listener) {
//
//        db.collection("requests").get().addOnSuccessListener(snapshots -> {
//            List<HelpRequest> list = new ArrayList<>();
//
//            for (DocumentSnapshot document : snapshots.getDocuments()) {
//                HelpRequest objectList = document.toObject(HelpRequest.class);
//                list.add(objectList);
//            }
//            listener.execute(list);
//        });
//    }

    public void deleteRequest(String documentId, IExecutable<Void> listener) {
        db.collection("requests")
                .document(documentId)
                .delete().addOnSuccessListener(snapsShot -> {
            listener.execute(null);
        });
    }

    public void getMyRequests(IExecutable<List<HelpRequest>> listener) {
        db.collection("requests").whereEqualTo("user_ID", SessionManager.getInstance().getUser().getUid()).get().addOnSuccessListener(snapshots -> {
            List<HelpRequest> list = new ArrayList<>();

            for (DocumentSnapshot document : snapshots.getDocuments()) {
                HelpRequest objectList = document.toObject(HelpRequest.class);
                list.add(objectList);
            }
            listener.execute(list);
        });
    }

    public void updateRequest() {
    }

    public void getCurrentUserDetails(String uId, IExecutable<User> listener) {
        db.collection("users").whereEqualTo("uid", uId).get().addOnSuccessListener(snapshots -> {

            if (listener != null) {

                for (DocumentSnapshot document : snapshots.getDocuments()) {
                    User user = document.toObject(User.class);
                    SessionManager.getInstance().setCurrentUser(user);
                    listener.execute(user);
                }
            }
        });
    }

    public void updateUserLocation(Context context, GeoPoint location, IExecutable<User> listener) {

        DocumentReference dr = db.collection("users").document(SessionManager.getInstance().getUser().getUid());

        dr.update("address_coords", location
                , "address_text", LocationHelper.getLocationName(context, location))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getCurrentUserDetails(SessionManager.getInstance().getUser().getUid(), listener);
                        }
                    }
                });
    }

    public void updateUserPhone(Context context, String phone, IExecutable<User> listener) {

        DocumentReference dr = db.collection("users").document(SessionManager.getInstance().getUser().getUid());

        dr.update("phone_number", phone)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getCurrentUserDetails(SessionManager.getInstance().getUser().getUid(), listener);
                        }
                    }
                });
    }

    public void updateUserName(Activity activity, String name, IExecutable<User> listener) {

        DocumentReference dr = db.collection("users").document(SessionManager.getInstance().getUser().getUid());

        dr.update("name", name)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getCurrentUserDetails(SessionManager.getInstance().getUser().getUid(), listener);
                        }
                    }
                });
    }
}
