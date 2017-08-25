package com.example.kimichael.yamblz_forecast.domain.interactor.requests;

/**
 * Created by Kim Michael on 18.07.17
 */
public class PlacesRequest extends BaseRequest{

    private final String queue;

    public PlacesRequest(String queue) {
        this.queue = queue;
    }

    public String getQueue() {
        return queue;
    }
}
