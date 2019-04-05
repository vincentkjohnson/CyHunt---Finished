package schellekens.restapitest.models;

import com.google.gson.Gson;

public class UserRequest {

    private String username;
    private String password;

    public UserRequest(){
        this.username = "";
        this.password = "";
    }

    public UserRequest(String uname, String pword){
        this.username = uname;
        this.password = pword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static UserRequest fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, UserRequest.class);
    }
}