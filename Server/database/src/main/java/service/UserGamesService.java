package service;

import dao.UserGamesRepository;
import entity.UserGames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGamesService {
    @Autowired
    UserGamesRepository userGamesRepository;

    public UserGames findUserGameByUserId(int userId)
    {
        return userGamesRepository.findByUserId(userId);
    }
    public List<UserGames> getByPoint(){return userGamesRepository.findAllByOrdeOrderByPointAsc();};
}
