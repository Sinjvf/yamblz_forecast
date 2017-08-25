package com.example.kimichael.yamblz_forecast.data.network.places.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sinjvf on 06.08.2017.
 * data class
 */

public class Geometry {

    @SerializedName("location")
    @Expose
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
