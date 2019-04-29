package com.example.cyhunt.cyhunt;

import com.google.gson.Gson;


public class ScoreRequest {

    private String username;
    private String locationName;

    public ScoreRequest() {
        this.username = "";
        this.locationName = "";
    }

    public ScoreRequest(String username, String location) {
        this.username = username;
        this.locationName = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static ScoreRequest fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ScoreRequest.class);
    }
}
