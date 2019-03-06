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
            return new AddUserResult(false, "User " + username + " already exists.");
        }

        return new AddUserResult(true, "User " + username + " added succesfully.");
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
            return new LoginUserResult(false, "User " + username + " already exists.");
        }

        return new LoginUserResult(true, "User " + username + " added succesfully.");
    }
}