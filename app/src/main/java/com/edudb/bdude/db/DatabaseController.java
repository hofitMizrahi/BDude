package com.edudb.bdude.db;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.interfaces.IExecutable;
import com.edudb.bdude.session.SessionManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
        post.setId(dr.getId());
        dr.set(post).addOnSuccessListener(snapshot -> {
            listener.execute(null);
        });
    }

    public void getAllRequestsList(IExecutable<List<HelpRequest>> listener) {

        db.collection("requests").get().addOnSuccessListener(snapshots -> {
            List<HelpRequest> list = new ArrayList<>();

            for (DocumentSnapshot document : snapshots.getDocuments()) {
                HelpRequest objectList = document.toObject(HelpRequest.class);
                list.add(objectList);
            }
            listener.execute(list);
        });
    }

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

            for (DocumentSnapshot document : snapshots.getDocuments()) {
                User user = document.toObject(User.class);
                listener.execute(user);
            }
        });
    }
}
