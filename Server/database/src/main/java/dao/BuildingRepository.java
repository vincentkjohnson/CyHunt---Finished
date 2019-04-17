package dao;

import entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building,Integer> {
    public List<Building> findByYearbuilt(int year);
    public Building findByBuildingname(String buildingname);
    public Building findById(int id);
}
