package cyhunter.server.businesslogic;

import cyhunter.server.entity.User;
import cyhunter.server.models.AddUserResult;
import cyhunter.server.models.LoginUserResult;
import cyhunter.server.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import cyhunter.server.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/***
 * Handles the logic behind User operations
 */
@Service
@Configurable
public class UserLogic implements IUserLogic {

    @Autowired
    private UserService uService;

    /***
     * Adds a new User to the system if a user by that name does not yet exist.
     * @param username The username for the new User
     * @param password The password for the new User
     * @return An AddUserResult indicating whether the operation succeeded or not.
     */
    @Override
    public AddUserResult addNewUser(String username, String password) {
        User user = this.uService.findByUserName(username);

        if(user != null){
            return new AddUserResult(false, String.format("User %s already exists", username));
        }

        user = new User();
        user.setUser_name(username);
        user.setPassword(this.hashPassword(username, password));

        this.uService.saveUser(user);

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
        User user = this.uService.findByUserName(username);

        if(user == null){
            return new LoginUserResult(false, String.format("User %s does not exist", username));
        }

        if(!validatePassword(username, password, user.getPassword())){
            return new LoginUserResult(false, String.format("Incorrect password entered for %s", username));
        }

        return new LoginUserResult(true, String.format("User %s has logged in succesfully", username));
    }

    private boolean validatePassword(String username, String password, String hash){
        return hash.equals(hashPassword(username, password));
    }

    private String hashPassword(String username, String password){
        return DigestUtils.sha256Hex(username + password);
    }
}