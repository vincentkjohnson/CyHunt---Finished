/**
 * @author: Zechen Huang
 */package cyhunter.database.service;

import cyhunter.database.dao.UserRepository;
import cyhunter.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> AllUsers(){
        return userRepository.findAll();
    }

    public User findByUserName(String username){
        User result = userRepository.findByUsernameIgnoreCase(username);
        return result;
    }

    public User saveUser(User newUser)
    {
        return userRepository.save(newUser);
    }

    public Boolean deleteUserByUserName(String username) {
        User target = findByUserName(username);
        if(target != null){
            userRepository.delete(target);
            return true;
        }
        return false;
    }

    public User findByUserId(int userId) { return userRepository.findFirstById(userId); }
}
