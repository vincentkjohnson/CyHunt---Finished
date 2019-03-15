package cyhunter.server.service;

import cyhunter.server.dao.BuildingRepository;
import cyhunter.server.entity.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {
    @Autowired
    BuildingRepository buildingRepository;
    public List<Building> findByYearBuilt(int year){return buildingRepository.findByYearbuilt(year);}
    public Building findByBuildingName(String buildingname){return  buildingRepository.findByBuildingname(buildingname);}
    public Building findById(int id){return buildingRepository.findById(id);}
}
