package com.example.cyhunt.cyhunt;

import java.util.ArrayList;
import java.util.List;

public class Objective {

    private String name;
    private double longitude;
    private double latitude;
    private String infoNote;
    private double radius;
    private List<Objective> objectives = new ArrayList<Objective>();

    public Objective(double latitude, double longitude, double radius, String name, String infoNote) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.name = name;
        this.infoNote = infoNote;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getInfoNote() {
        return infoNote;
    }
}
