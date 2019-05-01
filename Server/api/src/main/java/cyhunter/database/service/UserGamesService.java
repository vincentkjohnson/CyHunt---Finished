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

    public UserGame findByUserIdAndGameDateAndLocation(int userId, long date, int locationId){
        UserGame result = null;
        List<UserGame> userGames = userGamesRepository.findForUserAndGameAndLocation(userId, date);

        for(UserGame game : userGames){
            if(game.getGameLocation().getBuilding().getId() == locationId){
                result = game;
                break;
            }
        }

        return result;
    }

    public UserGame save(UserGame ug ) { return userGamesRepository.save(ug); }

    public List<UserGame> getTop10Day(long gameDate){
        return userGamesRepository.getTop10Day(gameDate);
    }

    public UserGame getUserDailyScore (long gameDate, int userId) {
        return userGamesRepository.getUserDailyScore(gameDate, userId);
    }

    public List<UserGame> getTop10Week(long gameDate) { return userGamesRepository.getTop10Week(gameDate); }

    public UserGame getUserWeeklyScore(long gameDate, int userId) { return userGamesRepository.getUserWeeklyScore(gameDate, userId); }
}
