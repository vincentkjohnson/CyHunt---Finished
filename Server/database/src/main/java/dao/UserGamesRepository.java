package dao;


import entity.UserGames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGamesRepository extends JpaRepository<UserGames,Integer> {
    public UserGames findByUsername(String username);
}
