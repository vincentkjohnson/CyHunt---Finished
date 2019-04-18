package dao;

import entity.GameLocations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameLocationsRepository extends JpaRepository<GameLocations,Integer>{
    public GameLocations findById(int id);
}
