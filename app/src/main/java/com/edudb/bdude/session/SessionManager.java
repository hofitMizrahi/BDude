package com.edudb.bdude.session;

public class SessionManager {

    //TODO save user Uid

    public static SessionManager mInstance;

    //TODO add this check
    public static boolean isUserLogin(){
        //TODO add Condition
        return false;
    }

    public static SessionManager getInstance() {
        if(mInstance == null){
            mInstance = new SessionManager();
        }
        return mInstance;
    }
}
