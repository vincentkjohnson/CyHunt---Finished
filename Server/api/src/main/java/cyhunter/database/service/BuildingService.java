/**
 * @author: Zechen Huang
 */package cyhunter.database.service;

import cyhunter.database.dao.BuildingRepository;
import cyhunter.database.entity.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;

    public List<Building> findByYearBuilt(int year){return buildingRepository.findByYearbuilt(year);}

    public Building findByBuildingName(String buildingname){return  buildingRepository.findByBuildingnameIgnoreCase(buildingname);}

    public Building findById(int id){return buildingRepository.findById(id);}

    public List<Integer> findAllIds() {
        return buildingRepository.findAllIds();
    }
}
