package cyhunter.server.businesslogic;

import cyhunter.server.models.AddUserResult;
import cyhunter.server.models.LoginUserResult;

/***
 * Handles the logic behind User operations
 */
public class UserLogic implements IUserLogic {

    /***
     * Adds a new User to the system if a user by that name does not yet exist.
     * @param username The username for the new User
     * @param password The password for the new User
     * @return An AddUserResult indicating whether the operation succeeded or not.
     */
    @Override
    public AddUserResult addNewUser(String username, String password) {
        if(username.equals("bob")){
            return new AddUserResult(false, String.format("User %s already exists", username));
        }

        return new AddUserResult(true, String.format("User %s added succesfully", username));
    }

    /***
     * Logs a User into the system.
     * @param username The username for the User to login
     * @param password The password for the User to login
     * @return An LoginUserResult indicating whether the operation succeeded or not.
     */
    @Override
    public LoginUserResult loginUser(String username, String password){
        if(username.equals("bob")){
            return new LoginUserResult(false, String.format("User %s does not exist", username));
        }

        return new LoginUserResult(true, String.format("User %s has logged in succesfully", username));
    }
}