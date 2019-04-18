package com.example.cyhunt.cyhunt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ObjectiveResponse {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static ObjectiveResponse fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<ObjectiveResponse>>(){}.getType());
    }
}
