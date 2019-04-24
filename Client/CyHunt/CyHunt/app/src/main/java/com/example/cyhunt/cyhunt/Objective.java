package com.example.cyhunt.cyhunt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Objective {
    private int locationId;
    private double latitude;
    private double longitude;
    private double radius;
    private String name;
    private String shortName;
    private String infoNote;
    private int currentPoints;

    public Objective(int locationId, double latitude, double longitude, double radius, String name, String shortName, String infoNote, int currentPoints) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.name = name;
        this.shortName = shortName;
        this.infoNote = infoNote;
        this.currentPoints = currentPoints;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getInfoNote() {
        return infoNote;
    }

    public void setInfoNote(String infoNote) {
        this.infoNote = infoNote;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public static List<Objective> getFromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Objective>>(){}.getType());
    }
}

