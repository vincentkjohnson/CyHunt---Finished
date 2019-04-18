package dao;


import entity.UserGames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGamesRepository extends JpaRepository<UserGames,Integer> {
    public UserGames findByUserId(int userId);
    public List<UserGames> findAllByOrdeOrderByPointAsc();

}
