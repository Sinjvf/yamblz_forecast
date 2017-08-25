
package com.example.kimichael.yamblz_forecast.data.network.forecast.response;

import com.google.gson.annotations.SerializedName;
class Clouds {

    @SerializedName("all")
    private int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
