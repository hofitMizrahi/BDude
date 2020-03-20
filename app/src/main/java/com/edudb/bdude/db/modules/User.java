package com.edudb.bdude.db.modules;

import com.google.android.gms.maps.model.LatLng;

public class User {

//    address_coords
//[32.081166° N, 34.785302° E]

    String address_coords;
    String address_text;
    String avatar;
    LatLng name;
    String phone_number;
    String uid;

    public User(String address_coords, String address_text, String avatar, LatLng name, String phone_number, String uid) {
        this.address_coords = address_coords;
        this.address_text = address_text;
        this.avatar = avatar;
        this.name = name;
        this.phone_number = phone_number;
        this.uid = uid;
    }

    public String getAddress_coords() {
        return address_coords;
    }

    public void setAddress_coords(String address_coords) {
        this.address_coords = address_coords;
    }

    public String getAddress_text() {
        return address_text;
    }

    public void setAddress_text(String address_text) {
        this.address_text = address_text;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LatLng getName() {
        return name;
    }

    public void setName(LatLng name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
