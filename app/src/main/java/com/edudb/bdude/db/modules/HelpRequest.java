package com.edudb.bdude.db.modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.edudb.bdude.enums.EnumGender;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HelpRequest implements Serializable {

    @SerializedName("address_coords")
    @Expose
    GeoPoint address_coords;
    @SerializedName("address_text")
    @Expose
    String address_text;
    @SerializedName("body")
    @Expose
    String body;
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("phone_number")
    @Expose
    String phone_number;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("user_ID")
    @Expose
    String user_ID;
    @SerializedName("user_avatar")
    @Expose
    String user_avatar;
    @SerializedName("user_name")
    @Expose
    String user_name;

    public HelpRequest() {
    }

    public GeoPoint getAddress_coords() {
        return address_coords;
    }

    public void setGeoPoint(GeoPoint address_coords) {
        this.address_coords = address_coords;
    }

    public String getAddress_text() {
        return address_text;
    }

    public void setAddress_text(String address_text) {
        this.address_text = address_text;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUserAvatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }
}
