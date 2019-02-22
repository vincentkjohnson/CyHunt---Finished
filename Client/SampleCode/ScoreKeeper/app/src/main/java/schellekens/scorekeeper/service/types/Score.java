package schellekens.scorekeeper.service.types;

/**
 * Created by bschellekens on 10/27/2017.
 */

public class Score extends ApiResult {

    private int gameId;
    private int HomeTeamScore;
    private int VisitingTeamScore;


    public DataTypes getDataType(){
        return DataTypes.Score;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getHomeTeamScore() {
        return HomeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        HomeTeamScore = homeTeamScore;
    }

    public int getVisitingTeamScore() {
        return VisitingTeamScore;
    }

    public void setVisitingTeamScore(int visitingTeamScore) {
        VisitingTeamScore = visitingTeamScore;
    }

    @Override
    public Score Deserialize(String jsonString) {
        return gson.fromJson(jsonString, Score.class);
    }

    @Override
    public String toString(){
        return gson.toJson(this);
    }
}