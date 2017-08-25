package com.example.kimichael.yamblz_forecast.data.network.places.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sinjvf on 06.08.2017.
 * response of getDetailes request
 */

public class DetailResponse {
    @SerializedName("result")
    @Expose
    private Result result;


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
