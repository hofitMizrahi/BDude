package com.edudb.bdude.db.modules;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HelpRequest implements Parcelable {

    @SerializedName("address_coords")
    @Expose
    GeoPoint address_coords;
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
    @SerializedName("address_text")
    @Expose
    String address_text;
    @SerializedName("body")
    @Expose
    String body;
    @SerializedName("objectID")
    @Expose
    String objectID;
    @SerializedName("phone_number")
    @Expose
    String phone_number;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("user_ID")
    @Expose
    String user_ID;
    @SerializedName("user_name")
    @Expose
    String user_name;

    public HelpRequest() {
    }

    protected HelpRequest(Parcel in) {
        double lat = in.readDouble();
        double lan = in.readDouble();
        address_coords = new GeoPoint(lat, lan);
        timestamp = in.readLong();
        status = in.readInt();
        views = in.readInt();
        products = in.readArrayList(Product.class.getClassLoader());
        category = in.readInt();
        ageAtRisk = in.readByte() != 0;
        inIsolation = in.readByte() != 0;
        address_text = in.readString();
        body = in.readString();
        objectID = in.readString();
        phone_number = in.readString();
        title = in.readString();
        user_ID = in.readString();
        user_name = in.readString();
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

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getUser_name() {
        return user_name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setAddress_coords(GeoPoint address_coords) {
        this.address_coords = address_coords;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

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
        return user_ID;
    }

    public void setId(String id) {
        this.user_ID = id;
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

    public String getUserName() {
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(address_coords.getLatitude());
        parcel.writeDouble(address_coords.getLongitude());
        parcel.writeLong(timestamp);
        parcel.writeInt(status);
        parcel.writeInt(views);
        parcel.writeList(products);
        parcel.writeInt(category);
        parcel.writeByte((byte) (ageAtRisk ? 1 : 0));
        parcel.writeByte((byte) (inIsolation ? 1 : 0));
        parcel.writeString(address_text);
        parcel.writeString(body);
        parcel.writeString(objectID);
        parcel.writeString(phone_number);
        parcel.writeString(title);
        parcel.writeString(user_ID);
        parcel.writeString(user_name);
    }
}
