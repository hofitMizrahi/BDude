package com.edudb.bdude.db.modules;

import com.edudb.bdude.db.modules.algolia.Geoloc;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {

    @SerializedName("user_gender")
    @Expose
    String userGender;
    @SerializedName("_geoloc")
    @Expose
    Geoloc geoloc;
    @SerializedName("address_text")
    @Expose
    String address;
    @SerializedName("body")
    @Expose
    String body;
    @SerializedName("objectID")
    @Expose
    String id;
    @SerializedName("phone_number")
    @Expose
    String phoneNumber;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("user_ID")
    @Expose
    String userID;
    @SerializedName("user_avatar")
    @Expose
    String userAvatar;
    @SerializedName("user_name")
    @Expose
    String userName;

    public Post() {
    }

    public Geoloc getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(Geoloc location) {
        this.geoloc = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
