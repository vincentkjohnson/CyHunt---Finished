package com.example.cyhunt.cyhunt;

import java.lang.reflect.Array;

public class ApiAuthenticationClient implements GetTask.GetResultHandler {

    interface ApiResultHandler {
        void handleResult(UserResponse response);
        void handleResultArray(ObjectiveResponse response);
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
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(login + "/" + username + "/" + password, "", "GET");
    }

    public void AddUser(String username, String password) {
        UserRequest req = new UserRequest(username, password);
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(add, req.toJson(), "POST");
    }

    public void getObjectives() {
        GetTask task = new GetTask(ApiAuthenticationClient.this);
        task.execute(objectives, "GET");
    }

    @Override
    public void getResult(String result) {
        this.resultHandler.handleResult(UserResponse.fromJson(result));
    }

    @Override
    public void getResultArray(String result) {
        this.resultHandler.handleResultArray(ObjectiveResponse.fromJson(result));
    }
}
