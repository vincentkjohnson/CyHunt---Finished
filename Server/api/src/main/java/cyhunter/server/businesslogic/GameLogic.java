package cyhunter.server.businesslogic;

import cyhunter.database.entity.Building;
import cyhunter.database.entity.GameLocation;
import cyhunter.database.entity.UserGame;
import cyhunter.database.service.BuildingService;
import cyhunter.database.service.GameLocationsService;
import cyhunter.database.service.UserGamesService;
import cyhunter.database.service.UserService;
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

    @Autowired
    UserService uService;

    /***
     * Gets the weekly Leader Board
     * @return A Set of LeaderBoardEntries
     */
    @Override
    public List<LeaderBoardEntry> getWeeklyLeaderBoard(){
        List<UserGame> leaders = this.ugService.getTop10Week(this.getNDaysAgoMillis(7));
        return this.convertLeaderBoard(leaders);
    }

    /***
     * Gets the daily Leader Board
     * @return A Set of LeaderBoardEntries
     */
    @Override
    public List<LeaderBoardEntry> getDailyLeaderBoard(){
        List<UserGame> leaders = this.ugService.getTop10Day(this.getNDaysAgoMillis(0));
        return convertLeaderBoard(leaders);
    }

    /***
     * Gets the daily Score for the User specified
     * @param username The username of the User to get the daily Score for
     * @return An int
     */
    @Override
    public int getDailyUserScore(String username){
        cyhunter.database.entity.User user = this.uService.findByUserName(username);
        cyhunter.database.entity.UserGame uGame = this.ugService.getUserWeeklyScore(this.getNDaysAgoMillis(7), user.getId());
        return uGame.getPoint();
    }

    /***
     * Gets the weekly Score for the User specified
     * @param username The username of the User to get the Weekly Score for
     * @return An int
     */
    @Override
    public int getWeeklyUserScore(String username){
        cyhunter.database.entity.User user = this.uService.findByUserName(username);
        cyhunter.database.entity.UserGame uGame = this.ugService.getUserDailyScore(this.getNDaysAgoMillis(0), user.getId());
        return uGame.getPoint();
    }

    @Override
    public List<Objective> getGameObjectives(){
        return getCurrentObjectives(true);
    }

    /***
     * Updates the Score for a Use by claiming completion of an objective
     * @param username The username of the User to update the Score for
     * @param locationName The name of the Location the User is at
     * @return A UpdateScoreResult object
     */
    @Override
    public UpdateUserScoreResult updateUserScore(String username, String locationName){
        cyhunter.database.entity.User user = this.uService.findByUserName(username);
        cyhunter.database.entity.Building building = this.bService.findByBuildingName(locationName);

        // Validate User
        if(user == null){
            return new UpdateUserScoreResult(false, "User does not exist", 0, 0, 0);
        }

        // Validate Location
        if(building == null){
            return new UpdateUserScoreResult(false,
                "Location does not exist",
                0,
                getDailyUserScore(user.getUserName()),
                getWeeklyUserScore(user.getUserName()));
        }

        List<Objective> objectives = this.getGameObjectives();
        Objective obj = null;

        for(Objective o : objectives){
            if(o.getLocationId() == building.getId()){
                obj = o;
                break;
            }
        }

        // Validate that Location is a valid Objective
        if(obj == null){
            return new UpdateUserScoreResult(false,
                "Location is not one of today's objectives",
                0,
                this.getDailyUserScore(user.getUserName()),
                this.getWeeklyUserScore(user.getUserName()));
        }

        // Ensure User hasn't achieved Objective yet.
        UserGame ug = this.ugService.findByUserIdAndGameDate(user.getId(), getNDaysAgoMillis(0));
        if(ug != null){
            return new UpdateUserScoreResult(false,
                "User Already Achieved Objective",
                0,
                this.getDailyUserScore(user.getUserName()),
                this.getWeeklyUserScore(user.getUserName()));
        }

        GameLocation gl = new GameLocation();
        gl.setBuilding(this.bService.findById(obj.getLocationId()));
        gl.setDate(getNDaysAgoMillis(0));
        int points = obj.getCurrentPoints();

        ug = new UserGame();
        ug.setDate(getNDaysAgoMillis(0));
        ug.setPoint(points);
        ug.setGameLocations(gl);
        this.ugService.save(ug);

        return new UpdateUserScoreResult(true,
            "User Score updated succesfully",
            points,
            this.getDailyUserScore(user.getUserName()),
            this.getWeeklyUserScore(user.getUserName())
        );
    }

    private List<Objective> getCurrentObjectives(boolean createIfNoGame){
        List<Objective> result = new ArrayList<>();
        long dt = this.getNDaysAgoMillis(0);

        List<GameLocation> gameLocations =  this.glService.findByDate(dt);

        if((gameLocations == null || gameLocations.size() == 0) && createIfNoGame){
            // Get the Buildings
            List<Integer> ids = this.bService.findAllIds();
            List<Building> buildings = new ArrayList<>();
            gameLocations = new ArrayList<>();
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
        if(gameLocations != null){
            for (GameLocation gl : gameLocations) {
                Building b = gl.getBuilding();
                String infoNote = "Built in " + b.getYearBuilt();
                Objective obj = new Objective(b.getId(), b.getLattitude(), b.getLongitude(), DEFAULT_RADIUS, b.getBuildingName(), b.getAbbreviation(), infoNote, getCurrentPointValue(new Date()));

                result.add(obj);
            }
        }

        return result;
    }

    private int getCurrentPointValue(Date dt){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(dt);

        return 24- calendar.get(Calendar.HOUR_OF_DAY);
    }

    private long getNDaysAgoMillis(int n) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1 * n);

        return calendar.getTime().getTime();
    }

    private List<LeaderBoardEntry> convertLeaderBoard(List<UserGame> board){
        List<LeaderBoardEntry> result = new ArrayList<>();
        int position = 1;

        for(UserGame leader : board){
            cyhunter.database.entity.User u = leader.getUser();
            LeaderBoardEntry lb = new LeaderBoardEntry(position, u.getUserName(), leader.getPoint());
            result.add(lb);
        }

        return result;
    }
}