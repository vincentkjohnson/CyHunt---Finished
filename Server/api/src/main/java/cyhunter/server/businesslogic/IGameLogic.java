package cyhunter.server.businesslogic;

import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.Objective;
import cyhunter.server.models.UpdateUserScoreResult;

import java.util.List;
import java.util.Set;

public interface IGameLogic {
    List<LeaderBoardEntry> getWeeklyLeaderBoard();

    List<LeaderBoardEntry> getDailyLeaderBoard();

    int getDailyUserScore(int userId);

    int getWeeklyUserScore(int userId);

    UpdateUserScoreResult updateUserScore(int userId, int locationId);

    List<Objective> getGameObjectives();
}