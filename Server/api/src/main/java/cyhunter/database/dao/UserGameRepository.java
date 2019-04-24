package cyhunter.database.dao;


import cyhunter.database.entity.UserGame;
import cyhunter.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGameRepository extends JpaRepository<UserGame,Integer> {

    public UserGame findByUser(User user);

    public List<UserGame> findAllByOrderByPointAsc();

    @Query("SELECT ug FROM UserGame ug WHERE game_date = :gameDate AND user_id = :userId")
    public UserGame findForUserAndGame(
        @Param("userId") int userId,
        @Param("gameDate") long gameDate);
}