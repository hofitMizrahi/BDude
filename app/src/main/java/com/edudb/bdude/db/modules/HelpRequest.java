package com.edudb.bdude.db.modules;

import com.google.android.gms.maps.model.LatLng;

public class HelpRequest {

    String address_coords;
    String address_text;
    String body;
    LatLng id;
    String phone_number;
    long timestamp;
    String title;
    String user_ID;
    String user_avatar;
    String user_name;

    public HelpRequest(String address_coords, String address_text, String body, LatLng id, String phone_number, long timestamp, String title, String user_ID, String user_avatar, String user_name) {
        this.address_coords = address_coords;
        this.address_text = address_text;
        this.body = body;
        this.id = id;
        this.phone_number = phone_number;
        this.timestamp = timestamp;
        this.title = title;
        this.user_ID = user_ID;
        this.user_avatar = user_avatar;
        this.user_name = user_name;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LatLng getId() {
        return id;
    }

    public void setId(LatLng id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
