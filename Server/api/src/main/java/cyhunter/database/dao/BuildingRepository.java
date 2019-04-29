package cyhunter.database.dao;

import cyhunter.database.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building,Integer> {

    List<Building> findByYearbuilt(int year);

    Building findByBuildingnameIgnoreCase(String buildingname);

    Building findById(int id);

    @Query("SELECT id FROM Building")
    List<Integer> findAllIds();
}