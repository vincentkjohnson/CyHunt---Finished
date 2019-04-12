package com.example.cyhunt.cyhunt;

import com.google.gson.Gson;

public class ObjectiveRequest {

    public static ObjectiveRequest fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson( json, ObjectiveRequest.class);

    }

}
