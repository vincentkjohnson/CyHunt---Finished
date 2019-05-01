package cyhunter.database.dao;


import cyhunter.database.entity.UserGame;
import cyhunter.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGameRepository extends JpaRepository<UserGame,Integer> {

    UserGame findByUser(User user);

    List<UserGame> findAllByOrderByPointAsc();

    @Query(value = "SELECT ug FROM UserGame ug WHERE game_date = :gameDate AND user_id = :userId",
        nativeQuery =  false)
    public List<UserGame> findForUserAndGameAndLocation(
        @Param("userId") int userId,
        @Param("gameDate") long gameDate);


    @Query(
        value="SELECT user_id AS user_id, SUM(points) AS points, MAX(id) AS id, MAX(game_date) AS game_date, MAX(game_location_id) AS game_location_id FROM usergame WHERE game_date = :gameDate GROUP BY user_id ORDER BY SUM(points) DESC LIMIT 10",
        nativeQuery = true
    )
    List<UserGame> getTop10Day(@Param("gameDate") long gameDate);

    @Query(
        value="SELECT user_id AS user_id, SUM(points) AS points, MAX(id) AS id, MAX(game_date) AS game_date, MAX(game_location_id) AS game_location_id FROM usergame WHERE game_date = :gameDate AND user_id = :userId GROUP BY user_id",
        nativeQuery = true
    )
     UserGame getUserDailyScore(@Param("gameDate") long gameDate, @Param("userId") int userId);

    @Query(
        value="SELECT user_id AS user_id, SUM(points) AS points, MAX(id) AS id, MAX(game_date) AS game_date, MAX(game_location_id) AS game_location_id FROM usergame WHERE game_date >= :gameDate GROUP BY user_id ORDER BY SUM(points) DESC LIMIT 10",
        nativeQuery = true
    )
    List<UserGame> getTop10Week(@Param("gameDate") long gameDate);

    @Query(
        value="SELECT user_id AS user_id, SUM(points) AS points, MAX(id) AS id, MAX(game_date) AS game_date, MAX(game_location_id) AS game_location_id FROM usergame WHERE game_date >= :gameDate AND user_id = :userId GROUP BY user_id",
        nativeQuery = true
    )
    UserGame getUserWeeklyScore(@Param("gameDate") long gameDate, @Param("userId") int userId);
}