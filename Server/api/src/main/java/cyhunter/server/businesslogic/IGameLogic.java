package cyhunter.server.businesslogic;

import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.Objective;
import cyhunter.server.models.UpdateUserScoreResult;

import java.util.List;
import java.util.Set;

public interface IGameLogic {
    List<LeaderBoardEntry> getWeeklyLeaderBoard();

    List<LeaderBoardEntry> getDailyLeaderBoard();

    int getDailyUserScore(String username);

    int getWeeklyUserScore(String username);

    UpdateUserScoreResult updateUserScore(String username, String locationName);

    List<Objective> getGameObjectives();
}