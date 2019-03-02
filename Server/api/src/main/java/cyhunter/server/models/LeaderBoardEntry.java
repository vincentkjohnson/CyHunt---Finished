package cyhunter.server.models;

public class LeaderBoardEntry {

    private int position;
    private String username;
    private int score;

    public LeaderBoardEntry(int position, String username, int score) {
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
}