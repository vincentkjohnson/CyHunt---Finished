package cyhunter.server.businesslogic;

import cyhunter.database.entity.Building;
import cyhunter.database.entity.GameLocation;
import cyhunter.database.service.BuildingService;
import cyhunter.database.service.GameLocationsService;
import cyhunter.database.service.UserGamesService;
import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.Objective;
import cyhunter.server.models.UpdateUserScoreResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.*;

/***
 * A class to handle logic related to Scores
 */
@Service
@Configurable
public class GameLogic implements IGameLogic {

    private static int DEFAULT_RADIUS = 15;

    @Autowired
    GameLocationsService glService;

    @Autowired
    BuildingService bService;

    @Autowired
    UserGamesService ugService;

    /***
     * Gets the weekly Leader Board
     * @return A Set of LeaderBoardEntries
     */
    @Override
    public List<LeaderBoardEntry> getWeeklyLeaderBoard(){
        return this.getMockedLeaderBoardData();
    }

    /***
     * Gets the daily Leader Board
     * @return A Set of LeaderBoardEntries
     */
    @Override
    public List<LeaderBoardEntry> getDailyLeaderBoard(){
        return this.getMockedLeaderBoardData();
    }

    /***
     * Gets the daily Score for the User specified
     * @param userId The Id of the User to get the daily Score for
     * @return An int
     */
    @Override
    public int getDailyUserScore(int userId){
        if(userId == 10){
            throw new IllegalArgumentException("Could not find User with ID: " + userId );
        }

        return 10;
    }

    /***
     * Gets the weekly Score for the User specified
     * @param userId The Id of the User to get the Weekly Score for
     * @return An int
     */
    @Override
    public int getWeeklyUserScore(int userId){
        if(userId == 10){
            throw new IllegalArgumentException("Could not find User with ID: " + userId);
        }

        return 50;
    }

    @Override
    public List<Objective> getGameObjectives(){
        List<Objective> objectives = getCurrentObjectives(true);
        return objectives;
    }

    /***
     * Updates the Score for a Use by claiming completion of an objective
     * @param userId The Id of the User to update the Score for
     * @param locationId The Id of the Location the User is at
     * @return A UpdateScoreResult object
     */
    @Override
    public UpdateUserScoreResult updateUserScore(int userId, int locationId){
        if(userId == 2){
            return new UpdateUserScoreResult(false, "User Already Achieved Objective", 0, 10, 65);
        }

        return new UpdateUserScoreResult(true, "Score Updated Succesfully", 10, 20, 75);
    }

    private List<Objective> getCurrentObjectives(boolean createIfNoGame){
        List<Objective> result = new ArrayList<>();
        long dt = this.getTodaysDate();

        List<GameLocation> gameLocations =  this.glService.findByDate(dt);

        if((gameLocations == null || gameLocations.size() == 0) && createIfNoGame){
            // Get the Buildings
            List<Integer> ids = this.bService.findAllIds();
            List<Building> buildings = new ArrayList<>();
            Random rnd = new Random();

            for(int i = 0; i < 10; i++){
                Integer nextIndex = rnd.nextInt(ids.size());
                Integer nextId = ids.get(nextIndex);

                buildings.add(this.bService.findById(nextId));
            }

            // Add to GameLocations table
            for(Building building : buildings){
                GameLocation gl = new GameLocation();
                gl.setDate(dt);
                gl.setBuilding(building);

                gameLocations.add(glService.save(gl));
            }
        }

        // Get Objectives from GameLocations
        for(GameLocation gl : gameLocations){
            Building b = gl.getBuilding();
            String infoNote = "Built in " + b.getYearBuilt();
            Objective obj = new Objective(b.getId(), b.getLattitude(), b.getLongitude(), DEFAULT_RADIUS, b.getBuildingName(), b.getAbbreviation(), infoNote, getCurrentPointValue(new Date()));

            result.add(obj);
        }

        return result;
    }

    /***
     * A Mock result for testing
     * @return A fixed Set of 10 LeaderBoardEntries
     */
    private List<LeaderBoardEntry> getMockedLeaderBoardData(){
    List<LeaderBoardEntry> result = new ArrayList<>();

    result.add(new LeaderBoardEntry(1, "bobby", 25));
    result.add(new LeaderBoardEntry(2, "jane", 30));
    result.add(new LeaderBoardEntry(3, "john", 35));
    result.add(new LeaderBoardEntry(4, "janet", 40));
    result.add(new LeaderBoardEntry(5, "user2", 45));
    result.add(new LeaderBoardEntry(6, "tom", 50));
    result.add(new LeaderBoardEntry(7, "sarah", 55));
    result.add(new LeaderBoardEntry(8, "jessie", 60));
    result.add(new LeaderBoardEntry(9, "josh", 65));
    result.add(new LeaderBoardEntry(10, "tom2", 70));

    return result;
}

    /***
     * A mock result for testing
     * @return A fixed Set of 20 Objectives
     */
    private List<Objective> getMockedObjectives(){
        List<Objective> result = new ArrayList<>();

        result.add(new Objective(1, 42.03469, -93.64558, 5.0, "Administrative Services Building", "ASB", "Built in 1998", 10));
        result.add(new Objective(4, 42.02992, -93.64016, 5.0, "Agronomy Greenhouse","AGRO+GH", "Built in 1985", 15));
        result.add(new Objective(5, 42.02838, -93.64248, 5.0, "Agronomy Hall","AGRON", "Built in 1952", 20));
        result.add(new Objective(6, 42.02992, -93.64203, 5.0, "Crop Genome Informatics Laboratory","CGIL", "Built in 1961", 25));
        result.add(new Objective(7, 42.02523, -93.64931, 5.0, "Enrollment Services Center","ENRL_SC", "Built in 1907", 30));
        result.add(new Objective(10, 42.02965, -93.65095, 5.0, "Armory","ARMORY", "Built in 1924", 35));
        result.add(new Objective(11, 42.02824, -93.64991, 5.0, "Atanasoff Hall","ATANSFF", "Built in 1969", 40));
        result.add(new Objective(12, 42.02439, -93.64154, 5.0, "Barton Residence Hall","BARTON", "Built in 1918", 45));
        result.add(new Objective(13, 42.02623, -93.64859, 5.0, "Beardshear Hall","BDSHR", "Built in 1906", 50));
        result.add(new Objective(14, 42.02856, -93.64467, 5.0, "Bessey Hall","BESSEY", "Built in 1967", 55));
        result.add(new Objective(15, 42.02591, -93.65296, 5.0, "Beyer Hall","BEYER", "Built in 1964", 60));
        result.add(new Objective(16, 42.02363, -93.64155, 5.0, "Birch Residence Hall","BIRCH", "Built in 1923", 65));
        result.add(new Objective(17, 42.02612, -93.6515, 5.0, "Black Engineering","BLACK", "Built in 1985", 70));
        result.add(new Objective(18, 42.02236, -93.64388, 5.0, "Buchanan Residence Hall","BUCHAN", "Built in 1964", 75));
        result.add(new Objective(19, 42.02544, -93.64602, 5.0, "Campanile","CAMPANI", "Built in 1898", 80));
        result.add(new Objective(20, 42.02531, -93.64839, 5.0, "Carver Hall","CARVER", "Built in 1969", 85));
        result.add(new Objective(21, 42.028, -93.64565, 5.0, "Catt Hall","CATT", "Built in 1893", 90));
        result.add(new Objective(24, 42.03109, -93.65119, 5.0, "Communications Building","COM+BDG", "Built in 1964", 95));
        result.add(new Objective(25, 42.02853, -93.65104, 5.0, "Coover Hall","COOVER", "Built in 1950", 100));

        return result;
    }

    private int getCurrentPointValue(Date dt){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(dt);

        return 24- calendar.get(Calendar.HOUR_OF_DAY);
    }

    private long getTodaysDate(){
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime().getTime();
    }
}