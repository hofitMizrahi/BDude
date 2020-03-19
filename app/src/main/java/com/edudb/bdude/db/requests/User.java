package com.edudb.bdude.db.requests;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;

public class User {

    String fullAddress;
    String name;
    String phone;
    LatLng latLng;
    String uId;

    public User(String fullAddress, String name, String phone, LatLng latLng, String uId) {
        this.fullAddress = fullAddress;
        this.name = name;
        this.phone = phone;
        this.latLng = latLng;
        this.uId = uId;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
