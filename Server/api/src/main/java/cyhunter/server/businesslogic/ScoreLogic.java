package cyhunter.server.businesslogic;

import com.sun.javaws.exceptions.InvalidArgumentException;
import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.UpdateUserScoreResult;

import java.util.*;

public class ScoreLogic implements IScoreLogic {

    @Override
    public Set<LeaderBoardEntry> getWeeklyLeaderBoard(){
        return this.getMockedData();
    }

    @Override
    public Set<LeaderBoardEntry> getDailyLeaderBoard(){
        return this.getMockedData();
    }

    @Override
    public int getDailyUserScore(int userId){
        if(userId == 10){
            throw new IllegalArgumentException("Could not find User with ID: " + userId );
        }

        return 10;
    }

    @Override
    public int getWeeklyUserScore(int userId){
        if(userId == 10){
            throw new IllegalArgumentException("Could not find User with ID: " + userId);
        }

        return 50;
    }

    @Override
    public UpdateUserScoreResult updateUserScore(int userId, int locationId){
        if(userId == 2){
            return new UpdateUserScoreResult(false, "User Already Achieved Objective", 0, 10, 65);
        }

        return new UpdateUserScoreResult(true, "Score Updated Succesfully", 10, 20, 75);
    }

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
