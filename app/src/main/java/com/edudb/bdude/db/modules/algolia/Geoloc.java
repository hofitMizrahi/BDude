
package com.edudb.bdude.db.modules.algolia;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Geoloc implements Parcelable {

    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lng")
    @Expose
    private double lng;

    public Geoloc(Parcel in) {

        lat = in.readDouble();
        lng = in.readDouble();
    }

    public Geoloc(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Geoloc> CREATOR = new Creator<Geoloc>() {
        @Override
        public Geoloc createFromParcel(Parcel in) {
            return new Geoloc(in);
        }

        @Override
        public Geoloc[] newArray(int size) {
            return new Geoloc[size];
        }
    };

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}
