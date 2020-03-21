package com.edudb.bdude.db.modules;

public class CurrentUser {

    String uId;

    public CurrentUser(String uId) {
        this.uId = uId;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }
}
