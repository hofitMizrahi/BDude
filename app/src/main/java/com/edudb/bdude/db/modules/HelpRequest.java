package com.edudb.bdude.db.modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.edudb.bdude.enums.EnumGender;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HelpRequest implements Parcelable {

    @SerializedName("address_coords")
    @Expose
    GeoPoint address_coords;
    @SerializedName("user_gender")
    @Expose
    String user_gender;
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
    @SerializedName("gender")
    @Expose
    String gender;

    public HelpRequest() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static final Creator<HelpRequest> CREATOR = new Creator<HelpRequest>() {
        @Override
        public HelpRequest createFromParcel(Parcel in) {
            return new HelpRequest(in);
        }

        @Override
        public HelpRequest[] newArray(int size) {
            return new HelpRequest[size];
        }
    };

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

//        if(EnumGender.MALE.getValue()){
//            //TODO icon avatar
//        }

        this.user_avatar = user_avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDouble(address_coords.getLatitude());
        parcel.writeDouble(address_coords.getLongitude());
        parcel.writeString(user_gender);
        parcel.writeString(address_text);
        parcel.writeString(body);
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(user_ID);
        parcel.writeString(user_avatar);
        parcel.writeString(user_name);
        parcel.writeString(phone_number);
    }

    public HelpRequest(Parcel in) {
        double lat = in.readDouble();
        double lng = in.readDouble();
        address_coords = new GeoPoint(lat, lng);
        user_gender = in.readString();
        this.address_text = in.readString();
        this.body =  in.readString();
        this.id = in.readString();
        this.title =  in.readString();
        this.user_ID =  in.readString();
        this.user_avatar =  in.readString();
        this.user_name =  in.readString();
        this.phone_number =  in.readString();
    }
}
