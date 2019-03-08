package cyhunter.server.businesslogic;

import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.UpdateUserScoreResult;

import java.util.*;

/***
 * A class to handle logic related to Scores
 */
public class GameLogic implements IGameLogic {

    /***
     * Gets the weekly Leader Board
     * @return A Set of LeaderBoardEntries
     */
    @Override
    public Set<LeaderBoardEntry> getWeeklyLeaderBoard(){
        return this.getMockedData();
    }

    /***
     * Gets the daily Leader Board
     * @return A Set of LeaderBoardEntries
     */
    @Override
    public Set<LeaderBoardEntry> getDailyLeaderBoard(){
        return this.getMockedData();
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

    /***
     * A Mock result for testing
     * @return A fixed Set of 10 LeaderBoardEntries
     */
    private Set<LeaderBoardEntry> getMockedData(){
    Set<LeaderBoardEntry> result = new HashSet<>();

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
}