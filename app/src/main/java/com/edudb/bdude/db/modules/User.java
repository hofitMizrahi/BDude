package com.edudb.bdude.db.modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("address_text")
    @Expose
    String address_text;
    @SerializedName("address_coords")
    @Expose
    GeoPoint address_coords;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("phone_number")
    @Expose
    String phone_number;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("uid")
    @Expose
    String uid;

    public User() {
    }

    public String getAddress_text() {
        return address_text;
    }

    public void setAddress_text(String address_text) {
        this.address_text = address_text;
    }

    public GeoPoint getAddress_coords() {
        return address_coords;
    }

    public void setAddress_coords(GeoPoint address_coords) {
        this.address_coords = address_coords;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
