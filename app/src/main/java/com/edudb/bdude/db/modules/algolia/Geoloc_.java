
package com.edudb.bdude.db.modules.algolia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geoloc_ {

    @SerializedName("lat")
    @Expose
    private Lat lat;
    @SerializedName("lng")
    @Expose
    private Lng lng;

    public Lat getLat() {
        return lat;
    }

    public void setLat(Lat lat) {
        this.lat = lat;
    }

    public Lng getLng() {
        return lng;
    }

    public void setLng(Lng lng) {
        this.lng = lng;
    }

}
