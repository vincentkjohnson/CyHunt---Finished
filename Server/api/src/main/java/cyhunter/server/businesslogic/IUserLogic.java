package cyhunter.server.businesslogic;

import cyhunter.server.models.AddUserResult;
import cyhunter.server.models.LoginUserResult;

/***
 * An interface for User centric operations
 */
public interface IUserLogic {

    /***
     * Adds a new User to the system if a user by that name does not yet exist.
     * @param username The username for the new User
     * @param password The password for the new User
     * @return An AddUserResult indicating whether the operation succeeded or not.
     */
    AddUserResult addNewUser(String username, String password);

    /***
     * Logs a User into the system.
     * @param username The username for the User to login
     * @param password The password for the User to login
     * @return An LoginUserResult indicating whether the operation succeeded or not.
     */
    LoginUserResult loginUser(String username, String password);
}