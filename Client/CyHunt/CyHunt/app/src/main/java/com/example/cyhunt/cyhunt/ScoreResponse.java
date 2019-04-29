package com.example.cyhunt.cyhunt;

import com.google.gson.Gson;

public class ScoreResponse {

    private boolean result;

    private String message;

    private int pointesEarned;

    private int dailyScore;

    private int weeklyScore;

    public ScoreResponse(boolean result, String message, int pointesEarned, int dailyScore, int weeklyScore) {
        this.result = result;
        this.message = message;
        this.pointesEarned = pointesEarned;
        this.dailyScore = dailyScore;
        this.weeklyScore = weeklyScore;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPointesEarned() {
        return pointesEarned;
    }

    public void setPointesEarned(int pointesEarned) {
        this.pointesEarned = pointesEarned;
    }

    public int getDailyScore() {
        return dailyScore;
    }

    public void setDailyScore(int dailyScore) {
        this.dailyScore = dailyScore;
    }

    public int getWeeklyScore() {
        return weeklyScore;
    }

    public void setWeeklyScore(int weeklyScore) {
        this.weeklyScore = weeklyScore;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static ScoreResponse fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, ScoreResponse.class);
    }
}