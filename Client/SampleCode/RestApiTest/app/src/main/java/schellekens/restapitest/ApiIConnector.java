package schellekens.restapitest;

import schellekens.restapitest.models.Objective;
import schellekens.restapitest.models.UserRequest;
import schellekens.restapitest.models.UserResponse;

import java.util.List;

public class ApiIConnector implements GetTask.GetResultHandler {

    private static boolean getting_objectives = false;

    // This is created so that the calling class can get the response
    interface ApiResultHandler {
        public void handleResult(UserResponse response);
        public void handdleObjectiveListResult(List<Objective> objectives);
    }

    private final String baseUrl = "http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080/";
    // private final String baseUrl = "http://10.0.2.2:8080/";
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
        getting_objectives = false;
        GetTask task = new GetTask(ApiIConnector.this);
        task.execute(loginUrl + "/" + username + "/" + password, "", "GET", "0");
    }

    // Calls AddUser on the API
    public void AddUser(String username, String password){
        getting_objectives = false;
        UserRequest req = new UserRequest(username, password);
        GetTask task = new GetTask(ApiIConnector.this);
        task.execute(addUrl, req.toJson(), "POST", "0");
    }

    public void getObjectives(){
        getting_objectives = true;
        GetTask task = new GetTask(ApiIConnector.this);
        String url = "http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080/game/objectives";
        task.execute(url, "", "GET", "1");
    }

    @Override
    public void getResult(String result, int resultType) {
        if(resultType == 0) {
            this.resultHanlder.handleResult(UserResponse.fromJson(result));
        } else if(resultType == 1) {
            this.resultHanlder.handdleObjectiveListResult(Objective.getFromJson(result));
        }
    }
}