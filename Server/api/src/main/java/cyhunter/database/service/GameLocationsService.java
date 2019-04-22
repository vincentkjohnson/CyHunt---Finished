package cyhunter.database.service;

import cyhunter.database.dao.GameLocationRepository;
import cyhunter.database.entity.GameLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameLocationsService {

    @Autowired
    GameLocationRepository gameLocationsRepository;

    public GameLocation findById(int id){return gameLocationsRepository.findById(id);}

    public List<GameLocation> findByDate(long dt) { return gameLocationsRepository.findAllByGameDate(dt); }

    public GameLocation save(GameLocation gl) { return gameLocationsRepository.save(gl); }
}