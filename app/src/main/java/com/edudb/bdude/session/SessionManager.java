package com.edudb.bdude.session;

import com.edudb.bdude.db.modules.CurrentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.GeoPoint;

public class SessionManager {

    private static SessionManager mInstance;
    private CurrentUser mCurrentUser;
    private GeoPoint mGeoPint;

    public boolean isUserLogin(){
        return FirebaseAuth.getInstance().getCurrentUser() != null && mCurrentUser != null && mCurrentUser.getUId() != null;
    }

    public static boolean isUserAcceptedTerms(){
        //TODO add condition
        return false;
    }

    public static SessionManager getInstance() {
        if(mInstance == null){
            mInstance = new SessionManager();
        }
        return mInstance;
    }

    public CurrentUser getUser(){
        return mCurrentUser;
    }

    public void setUserLocation(GeoPoint geoPoint){
        mGeoPint = geoPoint;
    }

    public void setCurrentUser(String uId){
        mCurrentUser = new CurrentUser(uId);
    }
}
