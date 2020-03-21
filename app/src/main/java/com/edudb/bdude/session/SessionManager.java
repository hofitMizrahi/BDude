package com.edudb.bdude.session;

import com.edudb.bdude.db.modules.User;
import com.google.firebase.firestore.GeoPoint;

public class SessionManager {

    private static SessionManager mInstance;
    private User mCurrentUser;

    public boolean isUserLogin(){
        return mCurrentUser != null && mCurrentUser.getUid() != null;
    }

    public static SessionManager getInstance() {
        if(mInstance == null){
            mInstance = new SessionManager();
        }
        return mInstance;
    }

    public User getUser(){
        return mCurrentUser;
    }

    public void setUserLocation(GeoPoint geoPoint){
        if(mCurrentUser != null){
            mCurrentUser.setAddress_coords(geoPoint);
        }
    }

    public void setCurrentUser(User user){
        mCurrentUser = user;
    }
}
