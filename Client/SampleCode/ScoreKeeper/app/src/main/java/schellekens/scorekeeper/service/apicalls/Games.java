package schellekens.scorekeeper.service.apicalls;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import schellekens.scorekeeper.ScoreKeeperDb;
import schellekens.scorekeeper.service.types.Game;

/**
 * schellekens.scorekeeper.service.Games Created by bschellekens on 11/3/2017.
 */
public class Games implements GetTask.GetResultHandler {
    private final String TAG = this.getClass().getSimpleName();

    private boolean _gamesRefreshing = false;
    private ScoreKeeperDb _db;
    private Context _appContext;

    public Games(Context context, ScoreKeeperDb db) {
        this._appContext = context;
        this._db = db;
    }

    /**
     * Starts an async task to refresh the data in the Games table from the server
     */
    public void refreshGames() {
        if (this._gamesRefreshing) return; // We're already refreshing

        this._gamesRefreshing = true;
        try {
            Date lastUpdate = this._db.getLastUpdateTime();
            long lastUpdateTime = lastUpdate == null ? 0 : lastUpdate.getTime();

            String url = ApiUrls.getGetAllGamesurl(lastUpdateTime);
            GetTask task = new GetTask(Games.this);
            task.execute(url);
        } catch (java.net.MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Gets a List of Games in which the Team with the specified ID is playing
     *
     * @param teamId       The ID of the Team to get Games for
     * @param refreshFirst If true, the Games table will be refreshed from the server before
     *                     returning results.
     * @return A List of Games
     */
    public List<Game> getTeamGames(int teamId, boolean refreshFirst) {
        List<Game> result = new ArrayList<>();

        if (refreshFirst) {
            refreshGames();
            while (this._gamesRefreshing) {
            }
        }

        String sql = "SELECT * FROM " + ScoreKeeperDb.TABLE_GAMES
                + " WHERE " + ScoreKeeperDb.GAMES_HOME_TEAM_ID + " = " + teamId
                + " OR " + ScoreKeeperDb.GAMES_HOSTING_TEAM_ID + " = " + teamId
                + " OR " + ScoreKeeperDb.GAMES_VISITING_TEAM_ID + " = " + teamId
                + ";";

        Cursor c = this._db.executeQuery(sql);

        while (c.moveToNext()) {
            result.add(getGameFromCursor(this._db, c));
        }

        c.close();
        return result;
    }

    /**
     * Gets an object representing the currently selected Game
     *
     * @return An instance of the Game class if a Game has been selected, otherwise null
     */
    public Game getSelectedGame() {
        int gameId = this._db.getSelectedGameId();

        if (gameId == -1) {
            return null;  // No Game selected yet
        }

        return getGameForId(this._db, gameId);
    }

    @Override
    public void getResult(String result) {
        if (this._gamesRefreshing) {
            try {
                List<Game> data = Game.DeserializeList(result);
                this.updatedGamesLocal(data);
                this._db.setLastUpdateTime(new Date());
                Toast.makeText(this._appContext, "Games Updated", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
                Toast.makeText(this._appContext, "Game Update Failed: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                this._gamesRefreshing = false;
            }
        }
    }

    /**
     * Updates the local DB with the data from the Game object.
     *
     * @param g A populated Game object
     */
    public void updateGameLocal(Game g) {
        List<String> names = new ArrayList<>();
        names.add(ScoreKeeperDb.GAMES_ID);
        names.add(ScoreKeeperDb.GAMES_VISITING_TEAM_ID);
        names.add(ScoreKeeperDb.GAMES_HOSTING_TEAM_ID);
        names.add(ScoreKeeperDb.GAMES_HOME_TEAM_ID);
        names.add(ScoreKeeperDb.GAMES_KICKOFF_TIME);
        names.add(ScoreKeeperDb.GAMES_DURATION);
        names.add(ScoreKeeperDb.GAMES_HOME_TEAM_SCORE);
        names.add(ScoreKeeperDb.GAMES_VISITING_TEAM_SCORE);

        List<String> values = new ArrayList<>();
        values.add(Integer.toString(g.getId()));
        values.add(Integer.toString(g.getVisitingTeamId()));
        values.add(Integer.toString(g.getHostingTeamId()));
        values.add(Integer.toString(g.getHomeTeamId()));
        values.add(Long.toString(g.getKickOff()));
        values.add(Integer.toString(g.getDuration()));
        values.add(Integer.toString(g.getHomeTeamScore()));
        values.add(Integer.toString(g.getVisitingTeamScore()));

        try {
            if (this._db.doesRecordExist(ScoreKeeperDb.TABLE_GAMES, g.getId())) {
                this._db.updateValues(ScoreKeeperDb.TABLE_GAMES, names, values, ScoreKeeperDb.GAMES_ID, g.getId());
            } else {
                this._db.insertValues(ScoreKeeperDb.TABLE_GAMES, names, values);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * Gets an instance of the Game class that represents the Game for the ID provided
     *
     * @param db     An instance of the ScoreKeeperDb class used to get the data
     * @param gameId The ID of the game to get the data for
     * @return An instance of the Game object if a Game by that ID is found, otherwise null
     */
    public static Game getGameForId(ScoreKeeperDb db, int gameId) {
        String sql = "SELECT *" + " FROM " + ScoreKeeperDb.TABLE_GAMES + " WHERE " + ScoreKeeperDb.GAMES_ID + " = " + gameId + ";";
        Cursor c = db.executeQuery(sql);
        Game result = new Game();

        if(c.moveToFirst()) {
            result = getGameFromCursor(db, c);
        }

        c.close();
        return result;
    }

    /**
     * Gets a Game object from a Cursor returned from the Local SQLite Databases Games table
     *
     * @param db An instance of the ScoreKeeperDB class.  This is used to fetch data needed for the
     *           object not stored in the Games table
     * @param c  A valid Cursor that is a result set of a select on the Games table
     * @return An instance of the Game class with all data populated.
     */
    public static Game getGameFromCursor(ScoreKeeperDb db, Cursor c) {
        Game result = new Game();

        if (c != null && c.getCount() > 0) {
            result.setId(c.getInt(c.getColumnIndex(ScoreKeeperDb.GAMES_ID)));
            result.setHomeTeamScore(c.getInt(c.getColumnIndex(ScoreKeeperDb.GAMES_HOME_TEAM_SCORE)));
            result.setHomeTeamId(c.getInt(c.getColumnIndex(ScoreKeeperDb.GAMES_HOME_TEAM_ID)));
            result.setHomeTeamName(Teams.getTeamForId(db, result.getHomeTeamId()).getName());
            result.setHostingTeamId(c.getInt(c.getColumnIndex(ScoreKeeperDb.GAMES_HOSTING_TEAM_ID)));
            result.setHostingTeamName(Teams.getTeamForId(db, result.getHostingTeamId()).getName());
            result.setVisitingTeamScore(c.getInt(c.getColumnIndex(ScoreKeeperDb.GAMES_VISITING_TEAM_SCORE)));
            result.setVisitingTeamId(c.getInt(c.getColumnIndex(ScoreKeeperDb.GAMES_VISITING_TEAM_ID)));
            result.setVisitingTeamName(Teams.getTeamForId(db, result.getVisitingTeamId()).getName());
            result.setDuration(c.getInt(c.getColumnIndex(ScoreKeeperDb.GAMES_DURATION)));
            result.setKickOff(c.getLong(c.getColumnIndex(ScoreKeeperDb.GAMES_KICKOFF_TIME)));
            result.setLastUpdated(db.getLastUpdateTime().getTime());
        }

        return result;
    }

    // Helper method, updates the Games in the local SQLite DB
    private void updatedGamesLocal(List<Game> data) {
        for (Game g : data) {
            updateGameLocal(g);
        }
    }
}