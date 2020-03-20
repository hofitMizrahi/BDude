package com.edudb.bdude.db;

import com.edudb.bdude.db.modules.HelpRequest;
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

    public void deleteRequest(){

    }

    public void updateRequest(){
    }
}
