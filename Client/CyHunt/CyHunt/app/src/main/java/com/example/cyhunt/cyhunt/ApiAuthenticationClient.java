package com.example.cyhunt.cyhunt;



import android.util.Log;

import java.util.List;

public class ApiAuthenticationClient implements GetTask.GetResultHandler {

    private static boolean gettingObjectives = false;
    private static boolean gettingLeaderboard = false;

    interface ApiResultHandler {
        void handleResult(UserResponse response);
        void handleObjectiveListResult(List<Objective> objectives);
        void handleLeaderBoardListResult(List<LeaderboardEntry> leaderboards);
        void handleScoreUpdateResult(ScoreResponse result);
    }

    private final String baseURL = "http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080/";
    private final String add = baseURL + "user/add";
    private final String login = baseURL + "user/login";
    private final String objectives = baseURL + "game/userobjectives";
    private final String updateScore = baseURL + "/game/updateuserscore";
    private final String dailyleaders = baseURL + "/game/dailyleaders";
    private final String weeklyleaders = baseURL + "/game/weeklyleaders";

    private ApiResultHandler resultHandler;

    public ApiAuthenticationClient(ApiResultHandler handler) {
        this.resultHandler = handler;
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    public void LoginUser(String username, String password) {
        gettingObjectives = false;
        gettingLeaderboard = false;
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(login + "/" + username + "/" + password, "", "GET", "0");
    }

    public void AddUser(String username, String password) {
        gettingObjectives = false;
        gettingLeaderboard = false;
        UserRequest req = new UserRequest(username, password);
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(add, req.toJson(), "POST", "0");
    }

    public void getObjectives(String username) {
        gettingObjectives = true;
        gettingLeaderboard = false;
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(objectives + "/" + username, "", "GET", "1");
    }

    public void updateScore(String username, String location) {
        gettingObjectives = false;
        gettingLeaderboard = false;
        ScoreRequest req = new ScoreRequest(username, location);
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(updateScore, req.toJson(), "PUT", "3");

    }

    public void getLeaderBoard(String choice) {
        gettingObjectives = false;
        gettingLeaderboard = true;
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        if (choice == "daily") {
            task.execute(dailyleaders, "", "GET", "2");
        } else if (choice == "weekly") {
            task.execute(weeklyleaders, "", "GET", "2");
        }
    }

    @Override
    public void getResult(String result, int resultType) {
        if (resultType == 0) {
            this.resultHandler.handleResult(UserResponse.fromJson(result));
        } else if (resultType == 1) {
            this.resultHandler.handleObjectiveListResult(Objective.getFromJson(result));
        } else if (resultType == 2) {
            this.resultHandler.handleLeaderBoardListResult(LeaderboardEntry.getFromJson(result));
        } else if(resultType == 3){
            this.resultHandler.handleScoreUpdateResult(ScoreResponse.fromJson(result));
        } else if(resultType == -1){
            Log.e("ApiAuthenticationClient", result);
        }
    }
}
