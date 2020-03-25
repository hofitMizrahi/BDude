
package com.edudb.bdude.db.modules.algolia;

import com.edudb.bdude.db.modules.algolia.Geoloc;
import com.edudb.bdude.db.modules.algolia.HighlightResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hit {

    @SerializedName("timestamp")
    @Expose
    private long timestamp;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_avatar")
    @Expose
    private String userAvatar;
    @SerializedName("user_ID")
    @Expose
    private String userID;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("address_text")
    @Expose
    private String addressText;
    @SerializedName("_geoloc")
    @Expose
    private Geoloc geoloc;
    @SerializedName("objectID")
    @Expose
    private String objectID;
    @SerializedName("_highlightResult")
    @Expose
    private HighlightResult highlightResult;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public Geoloc getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(Geoloc geoloc) {
        this.geoloc = geoloc;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public HighlightResult getHighlightResult() {
        return highlightResult;
    }

    public void setHighlightResult(HighlightResult highlightResult) {
        this.highlightResult = highlightResult;
    }

}
