package com.edudb.bdude.db.modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;

public class User implements Parcelable {

    private GeoPoint address_coords;
    private String address_text;
    private String name;
    private String phone_number;
    private String uid;

    public User() {
    }

    public GeoPoint getAddress_coords() {
        return address_coords;
    }

    public void setAddress_coords(GeoPoint address_coords) {
        this.address_coords = address_coords;
    }

    public String getAddress() {
        return address_text;
    }

    public void setAddress_text(String address_text) {
        this.address_text = address_text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    protected User(Parcel in) {

        double lat = in.readDouble();
        double lng = in.readDouble();
        address_coords = new GeoPoint(lat, lng);
        address_text = in.readString();
        name  = in.readString();
        phone_number = in.readString();
        uid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(address_coords.getLatitude());
        parcel.writeDouble(address_coords.getLongitude());
        parcel.writeString(address_text);
        parcel.writeString(name);
        parcel.writeString(phone_number);
        parcel.writeString(uid);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
