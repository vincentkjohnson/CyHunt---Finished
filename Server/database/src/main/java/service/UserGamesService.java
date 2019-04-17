package service;

import dao.UserGamesRepository;
import entity.UserGames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGamesService {
    @Autowired
    UserGamesRepository userGamesRepository;

    public UserGames findUserGameByUserName(String username)
    {
        return userGamesRepository.findByUsername(username);
    }
}
