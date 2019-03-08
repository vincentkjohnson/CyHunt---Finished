package cyhunter.server.businesslogic;

import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.UpdateUserScoreResult;

import java.util.Set;

public interface IGameLogic {
    Set<LeaderBoardEntry> getWeeklyLeaderBoard();

    Set<LeaderBoardEntry> getDailyLeaderBoard();

    int getDailyUserScore(int userId);

    int getWeeklyUserScore(int userId);

    UpdateUserScoreResult updateUserScore(int userId, int locationId);
}
