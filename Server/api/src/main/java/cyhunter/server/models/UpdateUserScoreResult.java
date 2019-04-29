package cyhunter.server.models;

public class UpdateUserScoreResult {

    private boolean result;

    private String message;

    private int pointesEarned;

    private int dailyScore;

    private int weeklyScore;

    public UpdateUserScoreResult(boolean result, String message, int pointesEarned, int dailyScore, int weeklyScore) {
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
}