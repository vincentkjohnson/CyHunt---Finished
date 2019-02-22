package schellekens.scorekeeper.service.apicalls;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import schellekens.scorekeeper.ScoreKeeperDb;
import schellekens.scorekeeper.service.types.Team;

/**
 * scorekeeper.service.apicalls Created by bschellekens on 11/3/2017.
 */

public class Teams implements GetTask.GetResultHandler {
    private final String TAG = this.getClass().getSimpleName();

    private boolean _teamsRefreshing = false;
    private ScoreKeeperDb _db;
    private Context _appContext;
    private Games _gamesConnection;

    public Teams(Context context, ScoreKeeperDb db) {
        this._appContext = context;
        this._db = db;
    }

    /**
     * Starts an async task to refresh the data in the Teams table from the server
     */
    public void refreshTeams(Games gamesConnection) {
        if (this._teamsRefreshing) return; // We're already refreshing

        this._teamsRefreshing = true;
        try {
            this._gamesConnection = gamesConnection;
            Date lastUpdate = this._db.getLastUpdateTime();
            long lastUpdateTime = lastUpdate == null ? 0 : lastUpdate.getTime();

            String url = ApiUrls.getGetAllTeamsUrl(lastUpdateTime);
            GetTask task = new GetTask(Teams.this);
            task.execute(url);
        } catch (java.net.MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Gets a List of all Teams in the local SQLite DB
     *
     * @return A List of Teams
     */
    public List<Team> getAllTeams() {
        List<Team> result = new ArrayList<>();

        // Get all the Team IDs
        String sql = "SELECT " + ScoreKeeperDb.TEAMS_ID
                + " FROM " + ScoreKeeperDb.TABLE_TEAMS + ";";

        Cursor c = this._db.executeQuery(sql);

        // Add them to the List
        while (c.moveToNext()) {
            int id = c.getInt(0);
            result.add(getTeamForId(this._db, id));
        }

        c.close();

        return result;
    }

    /**
     * Gets an object representing the currently selected Team
     *
     * @return An instance of the Team class if a Team has been selected, otherwise null
     */
    public Team getSelectedTeam() {
        int teamId = this._db.getSelectedTeamId();

        if (teamId == -1) {
            return null;  // No team selected yet
        }

        return getTeamForId(this._db, teamId);
    }

    @Override
    public void getResult(String result) {
        if (this._teamsRefreshing) {
            try {
                List<Team> data = Team.DeserializeList(result);
                this.updateTeamsLocal(data);
                this._db.setLastUpdateTime(new Date());
                Toast.makeText(this._appContext, "Teams Updated", Toast.LENGTH_SHORT).show();
            } catch (Exception ex){
                Log.e(TAG, ex.getMessage());
                Toast.makeText(this._appContext, "Team Update Failed: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                this._teamsRefreshing = false;

                if(this._gamesConnection != null){
                    this._gamesConnection.refreshGames();
                }
            }
        }
    }

    /**
     * Gets an instance of the Team class that represents the Team for the ID provided
     *
     * @param db     An instance of the ScoreKeeperDb class used to get the data
     * @param teamId The ID of the Team to get the data for
     * @return An instance of the Team class if a Team by that ID is found, otherwise null
     */
    public static Team getTeamForId(ScoreKeeperDb db, int teamId) {
        String sql = "SELECT *" + " FROM " + ScoreKeeperDb.TABLE_TEAMS + " WHERE " + ScoreKeeperDb.TEAMS_ID + " = " + teamId + ";";
        Cursor c = db.executeQuery(sql);

        Team result = getTeamForCursor(c);
        c.close();
        return result;
    }

    /**
     * Gets a Team object from a Cursor returned from the Local SQLite Databases Teams table
     *
     * @param c  A valid Cursor that is a result set of a select on the Teams table
     * @return An instance of the Team class with all data populated.
     */
    public static Team getTeamForCursor(Cursor c) {
        Team result = new Team();

        if (c != null && c.moveToFirst()) {
            result.setHomeCity(c.getString(c.getColumnIndex(ScoreKeeperDb.TEAMS_HOME_CITY)));
            result.setId(c.getInt(c.getColumnIndex(ScoreKeeperDb.TEAMS_ID)));
            result.setLatitude(c.getDouble(c.getColumnIndex(ScoreKeeperDb.TEAMS_LATITUDE)));
            result.setLongitude(c.getDouble(c.getColumnIndex(ScoreKeeperDb.TEAMS_LONGITUDE)));
            result.setLeagueId(c.getInt(c.getColumnIndex(ScoreKeeperDb.TEAMS_LEAGUE_ID)));
            result.setName(c.getString(c.getColumnIndex(ScoreKeeperDb.TEAMS_NAME)));
        }

        return result;
    }

    private void updateTeamsLocal(List<Team> data) {
        List<String> values;
        List<String> names = new ArrayList<>();
        names.add(ScoreKeeperDb.TEAMS_ID);
        names.add(ScoreKeeperDb.TEAMS_HOME_CITY);
        names.add(ScoreKeeperDb.TEAMS_LATITUDE);
        names.add(ScoreKeeperDb.TEAMS_LONGITUDE);
        names.add(ScoreKeeperDb.TEAMS_NAME);
        names.add(ScoreKeeperDb.TEAMS_LEAGUE_ID);

        for (Team t : data) {
            values = new ArrayList<>();
            values.add(Integer.toString(t.getId()));
            values.add("'" + t.getHomeCity() + "'");
            values.add(Double.toString(t.getLatitude()));
            values.add(Double.toString(t.getLongitude()));
            values.add("'" + t.getName() + "'");
            values.add(Integer.toString(t.getLeagueId()));

            try {
                if (this._db.doesRecordExist(ScoreKeeperDb.TABLE_TEAMS, t.getId())) {
                    // If it already exists we'll update it
                    this._db.updateValues(ScoreKeeperDb.TABLE_TEAMS, names, values, ScoreKeeperDb.TEAMS_ID, t.getId());
                } else {
                    // If it does not yet exist, we will create it
                    this._db.insertValues(ScoreKeeperDb.TABLE_TEAMS, names, values);
                }
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
        }
    }
}