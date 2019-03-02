package cyhunter.server.businesslogic;

import cyhunter.server.models.AddUserResult;

public class UserLogic implements IUserLogic {

    @Override
    public AddUserResult addNewUser(String username, String password) {
        if(username.equals("bob")){
            return new AddUserResult(false, "User " + username + " already exists.");
        }

        return new AddUserResult(true, "User " + username + " added succesfully.");
    }
}
