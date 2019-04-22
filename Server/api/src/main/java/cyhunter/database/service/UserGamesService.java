package cyhunter.database.service;

import cyhunter.database.dao.UserGameRepository;
import cyhunter.database.entity.UserGame;
import cyhunter.server.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGamesService {
    @Autowired
    UserGameRepository userGamesRepository;

    public UserGame findUserGameByUserId(User user)
    {
        return userGamesRepository.findByUser(user);
    }
    public List<UserGame> getByPoint(){return userGamesRepository.findAllByOrderByPointAsc();};
}
