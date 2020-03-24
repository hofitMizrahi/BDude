
package com.edudb.bdude.db.modules.algolia;

import com.edudb.bdude.db.modules.algolia.AddressText;
import com.edudb.bdude.db.modules.algolia.Body;
import com.edudb.bdude.db.modules.algolia.Geoloc_;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HighlightResult {

    @SerializedName("user_name")
    @Expose
    private UserName userName;
    @SerializedName("user_avatar")
    @Expose
    private UserAvatar userAvatar;
    @SerializedName("user_ID")
    @Expose
    private UserID userID;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("phone_number")
    @Expose
    private PhoneNumber phoneNumber;
    @SerializedName("body")
    @Expose
    private Body body;
    @SerializedName("address_text")
    @Expose
    private AddressText addressText;
    @SerializedName("_geoloc")
    @Expose
    private Geoloc_ geoloc;

    public UserName getUserName() {
        return userName;
    }

    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    public UserAvatar getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(UserAvatar userAvatar) {
        this.userAvatar = userAvatar;
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public AddressText getAddressText() {
        return addressText;
    }

    public void setAddressText(AddressText addressText) {
        this.addressText = addressText;
    }

    public Geoloc_ getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(Geoloc_ geoloc) {
        this.geoloc = geoloc;
    }

}
