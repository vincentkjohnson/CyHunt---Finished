package cyhunter.server.controller;

import cyhunter.server.businesslogic.IUserLogic;
import cyhunter.server.businesslogic.UserLogic;
import cyhunter.server.models.AddUserResult;
import cyhunter.server.models.LoginUserResult;
import cyhunter.server.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

/*
 * GET : Should not update anything. Should be idempotent (same result in multiple calls). Possible Return Codes 200 (OK) + 404 (NOT FOUND) +400 (BAD REQUEST)
 * POST : Should create new resource. Ideally return JSON with link to newly created resource. Same return codes as get possible. In addition : Return code 201 (CREATED) is possible.
 * PUT : Update a known resource. ex: update client details. Possible Return Codes : 200(OK)
 * DELETE : Used to delete a resource.
 * From: http://www.springboottutorial.com/creating-rest-service-with-spring-boot
 * */

@RestController
@RequestMapping(path="/user")
@Api(value="users", description="Operations pertaining to Users")
public class UserController {

    private IUserLogic userLogic;

    public UserController(){
        this.userLogic = new UserLogic();
    }

    public UserController(IUserLogic usrLogic){
        this.userLogic = usrLogic;
    }

    /***
     * Attempts to add a new User to the system
     * @param user The User object to add: { username: String, password: String }
     * @return A message indicating the result of the operation
     */
    @PostMapping(path="/add", produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Succesfully retrieved result message.")
    })
    public AddUserResult addUser(@RequestBody User user){
        return this.userLogic.addNewUser(user.getUsername(), user.getPassword());
    }

    /***
     * Attempts to log a User into the system
     * @param uname The User name of the user to login
     * @param pwd the Password of the User to login
     * @return A message indicating the result of the operation
     */
    @GetMapping(path="/login/{username}/{password}", produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Succesfully retrieved result message.")
    })
    public LoginUserResult loginUser(
        @PathVariable(value="username") final String uname,
        @PathVariable(value="password") final String pwd){
        User user = new User();
        user.setUsername(uname);
        user.setPassword(pwd);

        return this.userLogic.loginUser(user.getUsername(), user.getPassword());
    }
}