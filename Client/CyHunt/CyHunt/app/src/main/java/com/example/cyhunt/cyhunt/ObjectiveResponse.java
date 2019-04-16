package com.example.cyhunt.cyhunt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ObjectiveResponse {

    public static ObjectiveResponse fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<ObjectiveResponse>>(){}.getType());
    }
}
