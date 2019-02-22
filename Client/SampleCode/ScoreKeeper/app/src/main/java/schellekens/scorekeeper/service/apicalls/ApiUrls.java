package schellekens.scorekeeper.service.apicalls;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bschellekens on 10/26/2017.
 */
public class ApiUrls {
    private static final String BASE_API_URL = "http://scorekeeperservice.azurewebsites.net/api/";
    private static final String BASE_TEAM_URL = BASE_API_URL + "team/";
    private static final String BASE_GAME_URL = BASE_API_URL + "game/";
    private static final String BASE_LEAGUE_URL = BASE_API_URL + "league/";

    // Team Controller URLs
    public static String getGetAllTeamsUrl(long lastUpdatedTimestamp) throws MalformedURLException {
        return BASE_TEAM_URL + "GetAll?lastUpdated=" + Long.toString(lastUpdatedTimestamp);
    }

    public static String getGetTeamsInLeaugeUrl(int leagueId) throws MalformedURLException {
        return BASE_TEAM_URL + "GetForLeague?leagueId=" + Integer.toString(leagueId);
    }

    public static String getGetCompetingTeamsUrl(int teamId) throws MalformedURLException {
        return BASE_TEAM_URL + "GetCompetingTeams?teamId=" + Integer.toString(teamId);
    }

    // League Controller Urls
    public static String getGetAllLeaguesUrl() throws MalformedURLException {
        return BASE_LEAGUE_URL + "GetAll";
    }

    // Game Controller Urls
    public static String getGetAllGamesurl(long lastUpdatedTimestamp) throws MalformedURLException {
        return BASE_GAME_URL + "GetAll?lastUpdated=" + Long.toString(lastUpdatedTimestamp);
    }

    public static String getGetMyGamesUrl(int teamId) throws MalformedURLException {
        return BASE_GAME_URL + "GetMyGames?teamId=" + Integer.toString(teamId);
    }

    public static String getGetLeagueGamesUrl(int leagueId) throws MalformedURLException {
        return BASE_GAME_URL + "GetLeagueGames?leagueId=" + Integer.toString(leagueId);
    }

    public static String getPostScoreUrl() {
        return BASE_GAME_URL + "PostScore";
    }
}