package com.COMS309.demo2retry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.COMS309.demo2retry.dao.UserRepository;
import com.COMS309.demo2retry.domain.User;


@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
    @GetMapping(path="/")
    public String welcomePage(String what){
            return "validation";
    }
    @GetMapping(path = "/register")
    public @ResponseBody String register(@RequestParam String name, @RequestParam String email,@RequestParam String password,User newUser)
    {
    	List<User> users = userRepository.findByName(name);
    	for(User eachUser:users)
		{
			if(eachUser.getPassword().equals(password) && eachUser.getEmail().equals(email))
			{
				return "You already registered";
			}
		}
    	newUser.setEmail(email);
    	newUser.setName(name);
    	newUser.setPassword(password);
    	userRepository.save(newUser);
    	return "New User " + name+ " registered.";
    }
    @GetMapping(path = "/login")
    public @ResponseBody String login(@RequestParam String name,@RequestParam String email, @RequestParam String password,Model model)
    {
    	List<User> users = userRepository.findByName(name);
    	if(users.size() == 0)
    	{
    		return "You need to register first";
    	}
    	else 
    	{
    		for(User eachUser:users)
    		{
    			if(eachUser.getPassword().equals(password) && eachUser.getEmail().equals(email))
    			{
    				model.addAttribute("name", eachUser.getName());
    				return "Login successful";
    			}
    		}
    		return "Does not match the record";
    	}
    }
    
    @GetMapping(path="/allUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    
}
