package schellekens.restapitest.models;

public class ResponseWrapper {

    public String response;

    public int responseType;

    public ResponseWrapper(String resp, int respType){
        this.response = resp;
        this.responseType = respType;
    }
}
