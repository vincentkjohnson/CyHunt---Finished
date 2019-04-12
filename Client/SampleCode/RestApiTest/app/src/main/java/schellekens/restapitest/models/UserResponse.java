package schellekens.restapitest.models;

import com.google.gson.Gson;

public class UserResponse {

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

    public static UserResponse fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, UserResponse.class);
    }
}