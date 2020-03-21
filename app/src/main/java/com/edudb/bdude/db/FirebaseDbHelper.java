package com.edudb.bdude.db;

import com.edudb.bdude.db.modules.HelpRequest;
import com.edudb.bdude.db.modules.User;
import com.edudb.bdude.interfaces.IExecutable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class FirebaseDbHelper {

    private static FirebaseDbHelper mFirebaseDbInstance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Inject
    FirebaseDbHelper() {
    }

    public static FirebaseDbHelper getInstance(){
        if (mFirebaseDbInstance == null) {
            mFirebaseDbInstance = new FirebaseDbHelper();
        }
        return mFirebaseDbInstance;
    }

    public void createRequest(){
    }

    public void getAllRequestsList(IExecutable<List<HelpRequest>> listener){

        db.collection("posts").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot snapshots) {

                List<HelpRequest> list = new ArrayList<>();

                for(DocumentSnapshot document : snapshots.getDocuments()){
                    HelpRequest objectList = document.toObject(HelpRequest.class);
                    list.add(objectList);
                }
                listener.execute(list);
            }
        });
    }

    public void deleteRequest(String documentId, IExecutable<Void> listener){

        //TODO fix - not delete from firebase db
        db.collection("Posts")
                .document(documentId)
                .delete().addOnSuccessListener(snapsShot -> {
            listener.execute(null);
        });
    }

    public void getMyRequests(IExecutable<List<HelpRequest>> listener){
//        db.collection("posts").whereEqualTo("user_ID", SessionManager.getInstance().getUser().getUId()).get().addOnSuccessListener(snapshots -> {

        db.collection("posts").whereEqualTo("user_ID", "gSweaRxNsCf8m6QeNdzrzwEVO2I3").get().addOnSuccessListener(snapshots -> {
            List<HelpRequest> list = new ArrayList<>();

            for(DocumentSnapshot document : snapshots.getDocuments()){
                HelpRequest objectList = document.toObject(HelpRequest.class);
                list.add(objectList);
            }
            listener.execute(list);
        });
    }

    public void updateRequest(){
    }

    public void getCurrentUserDetails(String uId ,IExecutable<User> listener) {
        db.collection("users").whereEqualTo("uid", uId).get().addOnSuccessListener(snapshots -> {

            for(DocumentSnapshot document : snapshots.getDocuments()){
                User user = snapshots.getDocuments().get(0).toObject(User.class);
                listener.execute(user);
            }
        });
    }
}
