package com.edudb.bdude.db;

import javax.inject.Inject;

public class FirebaseDbHelper {

//    // Write a message to the database
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("message");

    public static FirebaseDbHelper mFirebaseDbInstance;
    //FirebaseFirestore db = FirebaseFirestore.getInstance();

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
