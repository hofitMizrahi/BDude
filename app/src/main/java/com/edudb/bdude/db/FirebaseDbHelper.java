package com.edudb.bdude.db;

public class FirebaseDbHelper {

    public static FirebaseDbHelper mFirebaseDbInstance;

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
