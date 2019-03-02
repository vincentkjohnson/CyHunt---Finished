package cyhunter.server.models;

public class AddUserResult {

    private boolean success;

    private String message;

    public AddUserResult(boolean success, String msg){
        this.success = success;
        this.message = msg;
    }

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
}
