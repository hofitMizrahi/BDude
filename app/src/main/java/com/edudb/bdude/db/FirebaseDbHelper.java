package com.edudb.bdude.db;

import com.google.firebase.database.FirebaseDatabase;
import javax.inject.Inject;

public class FirebaseDbHelper {

    public static FirebaseDbHelper mFirebaseDbInstance;
    public FirebaseDatabase mFirebaseDataBase;

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

    public void getAllRequestsList(){
    }

    public void deleteRequest(){

    }

    public void updateRequest(){
    }
}
