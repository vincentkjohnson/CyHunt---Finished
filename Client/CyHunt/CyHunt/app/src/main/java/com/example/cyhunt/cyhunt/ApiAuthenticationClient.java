package com.example.cyhunt.cyhunt;


import java.util.List;

public class ApiAuthenticationClient implements GetTask.GetResultHandler {

    private static boolean gettingObjectives = false;

    interface ApiResultHandler {
        void handleResult(UserResponse response);
        void handleObjectiveListResult(List<Objective> objectives);
    }

    private final String baseURL = "http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080/";
    private final String add = baseURL + "user/add";
    private final String login = baseURL + "user/login";
    private final String objectives = baseURL + "game/objectives";

    private ApiResultHandler resultHandler;

    public ApiAuthenticationClient(ApiResultHandler handler) {
        this.resultHandler = handler;
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    public void LoginUser(String username, String password) {
        gettingObjectives = false;
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(login + "/" + username + "/" + password, "", "GET", "0");
    }

    public void AddUser(String username, String password) {
        gettingObjectives = false;
        UserRequest req = new UserRequest(username, password);
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(add, req.toJson(), "POST", "0");
    }

    public void getObjectives() {
        gettingObjectives = true;
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(objectives, "", "GET", "1");
    }

    @Override
    public void getResult(String result, int resultType) {
        if (resultType == 0) {
            this.resultHandler.handleResult(UserResponse.fromJson(result));
        } else if (resultType == 1) {
            this.resultHandler.handleObjectiveListResult(Objective.getFromJson(result));
        }
    }
}
