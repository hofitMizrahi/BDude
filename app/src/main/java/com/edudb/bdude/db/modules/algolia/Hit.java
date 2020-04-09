
package com.edudb.bdude.db.modules.algolia;

import com.edudb.bdude.db.modules.Product;
import com.edudb.bdude.db.modules.algolia.Geoloc;
import com.edudb.bdude.db.modules.algolia.HighlightResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hit {

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
    @SerializedName("user_name")
    @Expose
    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBody() {
        return body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAddressText() {
        return address;
    }

    public void setAddressText(String addressText) {
        this.address = addressText;
    }

    public Geoloc getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(Geoloc geoloc) {
        this.geoloc = geoloc;
    }

    public String getObjectID() {
        return id;
    }

    public void setObjectID(String objectID) {
        this.id = objectID;
    }

}
