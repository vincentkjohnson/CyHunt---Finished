package cyhunter.server.controller;

import cyhunter.server.businesslogic.IUserLogic;
import cyhunter.server.businesslogic.UserLogic;
import cyhunter.server.models.AddUserResult;
import cyhunter.server.models.User;
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
public class UserController {

    private IUserLogic userLogic;

    public UserController(){
        this.userLogic = new UserLogic();
    }

    public UserController(IUserLogic usrLogic){
        this.userLogic = usrLogic;
    }

    @PostMapping(path="/add")
    public AddUserResult addUser(@RequestBody User user){
        return this.userLogic.addNewUser(user.getUsername(), user.getPassword());
    }
}