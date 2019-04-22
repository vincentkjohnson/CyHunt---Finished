package cyhunter.database.dao;

import cyhunter.database.entity.GameLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameLocationRepository extends JpaRepository<GameLocation,Integer>{

    GameLocation findById(int id);

    @Query("SELECT gl FROM GameLocation gl WHERE gameDate = :dt")
    List<GameLocation> findAllByGameDate(
        @Param("dt") long dt);
    }