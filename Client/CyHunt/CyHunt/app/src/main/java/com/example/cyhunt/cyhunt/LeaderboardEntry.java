package com.example.cyhunt.cyhunt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class LeaderboardEntry {
    private int position;
    private String username;
    private int score;

    public LeaderboardEntry(int position, String username, int score) {
        this.position = position;
        this.username = username;
        this.score = score;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static List<LeaderboardEntry> getFromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<LeaderboardEntry>>(){}.getType());
    }

}
