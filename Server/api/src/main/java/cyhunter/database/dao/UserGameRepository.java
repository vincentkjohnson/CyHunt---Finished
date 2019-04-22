package cyhunter.database.dao;


import cyhunter.database.entity.UserGame;
import cyhunter.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGameRepository extends JpaRepository<UserGame,Integer> {
    public UserGame findByUser(User user);
    public List<UserGame> findAllByOrderByPointAsc();
}