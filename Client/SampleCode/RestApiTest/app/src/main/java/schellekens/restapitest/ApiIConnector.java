package schellekens.restapitest;

import schellekens.restapitest.models.UserRequest;
import schellekens.restapitest.models.UserResponse;

public class ApiIConnector implements GetTask.GetResultHandler {

    // This is created so that the calling class can get the response
    interface ApiResultHandler {
        public void handleResult(UserResponse response);
    }

    private final String baseUrl = "http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080/";
    private final String addUrl = baseUrl + "user/add";
    private final String loginUrl = baseUrl + "user/login";
    private ApiResultHandler resultHanlder;

    // Constructor, takes a class that implements the above interface as an argument, this is used to
    // return results asynchronously
    public ApiIConnector(ApiResultHandler handler){
        this.resultHanlder = handler;
    }

    // Calls LoginUser on the API
    public void LoginUser(String username, String password){
        UserRequest req = new UserRequest(username, password);
        GetTask task = new GetTask(ApiIConnector.this);
        task.execute(loginUrl, req.toJson(), "POST");
    }

    // Calls AddUser on the API
    public void AddUser(String username, String password){
        UserRequest req = new UserRequest(username, password);
        GetTask task = new GetTask(ApiIConnector.this);
        task.execute(addUrl, req.toJson(), "POST");
    }

    @Override
    public void getResult(String result) {
        this.resultHanlder.handleResult(UserResponse.fromJson(result));
    }
}