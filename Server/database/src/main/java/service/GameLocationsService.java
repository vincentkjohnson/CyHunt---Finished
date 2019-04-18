package service;

import dao.GameLocationsRepository;
import entity.GameLocations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameLocationsService {
    @Autowired
    GameLocationsRepository gameLocationsRepository;
    public GameLocations findById(int id){return gameLocationsRepository.findById(id);}
}
