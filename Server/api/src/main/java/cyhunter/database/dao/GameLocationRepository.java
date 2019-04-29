package cyhunter.database.dao;

import cyhunter.database.entity.GameLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameLocationRepository extends JpaRepository<GameLocation,Integer>{

    GameLocation findById(int id);

    @Query("SELECT gl FROM GameLocation gl WHERE gameDate = :dt AND building_id = :bldId")
    GameLocation findByDateAndBuildingId(
        @Param("dt") long dt,
        @Param("bldId") int buildingId
    );

    @Query("SELECT gl FROM GameLocation gl WHERE gameDate = :dt")
    List<GameLocation> findAllByGameDate(
        @Param("dt") long dt);
    }