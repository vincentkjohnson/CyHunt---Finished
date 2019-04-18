package com.example.cyhunt.cyhunt;

import java.util.ArrayList;
import java.util.List;

public class Objective {

    private String name;
    private double longitude;
    private double latitude;
    private String infoNote;
    private double radius;
    private List<Objective> objectives = new ArrayList<>();

    public Objective() {

    }

    public Objective(double latitude, double longitude, double radius, String name, String infoNote) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.name = name;
        this.infoNote = infoNote;
    }

    public void setObjectiveList() {
        objectives.add(new Objective(42.02965, -93.65095, 5.0, "Armory", ""));
        objectives.add(new Objective(42.02856, -93.64467, 5.0, "Bessey Hall", ""));
        objectives.add(new Objective(42.03469, -93.64558, 5.0, "Administrative Building", ""));
        objectives.add(new Objective(42.02523, -93.64931, 5.0, "Enrollment Services Center", "" ));
        objectives.add(new Objective(42.02531, -93.64839, 5.0, "Carver Hall", ""));
        objectives.add(new Objective(42.02992, -93.64016, 5.0, "Agronomy Greenhouse", ""));
        objectives.add(new Objective(42.02363, -93.64155, 5.0, "Birch Residence Hall", ""));
        objectives.add(new Objective(42.02992, -93.64203, 5.0, "Crop Genome Informatics Laboratory", ""));
        objectives.add(new Objective(42.02439, -93.64154, 5.0, "Barton Residence Hall", ""));
        objectives.add(new Objective(42.03109, -93.65119, 5.0, "Communications Building", ""));
        objectives.add(new Objective(42.02595, -93.64999, 5.0, "Pearson Hall", ""));
    }

    public List<Objective> getObjectives() {
        return objectives;
    }
}
