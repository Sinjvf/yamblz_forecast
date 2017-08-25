package com.example.kimichael.yamblz_forecast.data.network.places.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sinjvf on 05.08.2017.
 * response for suggestions
 */

public class PlacesResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("predictions")
    @Expose
    private List<Prediction> predictions = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }
}
