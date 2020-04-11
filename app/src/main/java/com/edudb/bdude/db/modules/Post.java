package com.edudb.bdude.db.modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.edudb.bdude.db.modules.algolia.Geoloc;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post implements Parcelable {

    @SerializedName("timestamp")
    @Expose
    long timestamp;
    @SerializedName("status")
    @Expose
    int status;
    @SerializedName("views")
    @Expose
    int views;
    @SerializedName("products")
    @Expose
    List<Product> products;
    @SerializedName("category")
    @Expose
    int category;
    @SerializedName("ageAtRisk")
    @Expose
    boolean ageAtRisk;
    @SerializedName("inIsolation")
    @Expose
    boolean inIsolation;
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

    protected Post(Parcel in) {
        timestamp = in.readLong();
        status = in.readInt();
        views = in.readInt();
        products = in.createTypedArrayList(Product.CREATOR);
        category = in.readInt();
        ageAtRisk = in.readByte() != 0;
        inIsolation = in.readByte() != 0;
        double lat = in.readDouble();
        double lng = in.readDouble();
        geoloc = new Geoloc(lat, lng);
        address = in.readString();
        body = in.readString();
        id = in.readString();
        phoneNumber = in.readString();
        title = in.readString();
        userID = in.readString();
        userAvatar = in.readString();
        userName = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isAgeAtRisk() {
        return ageAtRisk;
    }

    public void setAgeAtRisk(boolean ageAtRisk) {
        this.ageAtRisk = ageAtRisk;
    }

    public boolean isInIsolation() {
        return inIsolation;
    }

    public void setInIsolation(boolean inIsolation) {
        this.inIsolation = inIsolation;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(timestamp);
        parcel.writeInt(status);
        parcel.writeInt(views);
        parcel.writeTypedList(products);
        parcel.writeInt(category);
        parcel.writeByte((byte) (ageAtRisk ? 1 : 0));
        parcel.writeByte((byte) (inIsolation ? 1 : 0));
        parcel.writeDouble(geoloc.getLat());
        parcel.writeDouble(geoloc.getLng());
        parcel.writeString(address);
        parcel.writeString(body);
        parcel.writeString(id);
        parcel.writeString(phoneNumber);
        parcel.writeString(title);
        parcel.writeString(userID);
        parcel.writeString(userAvatar);
        parcel.writeString(userName);
    }
}
