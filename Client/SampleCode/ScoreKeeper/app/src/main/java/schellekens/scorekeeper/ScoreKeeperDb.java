package schellekens.scorekeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import java.util.Date;
import java.util.List;

/**
 * schellekens.scorekeeper Created by bschellekens on 11/29/2017.
 */
public class ScoreKeeperDb extends SQLiteOpenHelper {

    private static String TAG = "ScoreKeeperDB";

    // General DB Info
    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "ScoreKeeperDb";

    // Table Names, Create in this order due to Foreign Key constraints
    public static final String TABLE_LEAGUES = "Leagues";
    public static final String TABLE_TEAMS = "Teams";
    public static final String TABLE_GAMES = "Games";
    private static final String TABLE_CONFIG = "Config";

    // Columns for Leagues Table
    public static final String LEAGUE_ID = "Id";
    public static final String LEAGUE_NAME = "Name";

    // Columns for Teams Table
    public static final String TEAMS_ID = "Id";
    public static final String TEAMS_LEAGUE_ID = "LeagueId";
    public static final String TEAMS_NAME = "Name";
    public static final String TEAMS_HOME_CITY = "HomeCity";
    public static final String TEAMS_LATITUDE = "Latitude";
    public static final String TEAMS_LONGITUDE = "Longitude";

    // Columns for Games Table
    public static final String GAMES_ID = "Id";
    public static final String GAMES_HOME_TEAM_ID = "HomeTeamId";
    public static final String GAMES_HOSTING_TEAM_ID = "HostingTeamId";
    public static final String GAMES_KICKOFF_TIME = "KickOff";
    public static final String GAMES_DURATION = "PlannedDuration";
    public static final String GAMES_VISITING_TEAM_ID = "VisitingTeamId";
    public static final String GAMES_HOME_TEAM_SCORE = "HomeTeamScore";
    public static final String GAMES_VISITING_TEAM_SCORE = "VisitingTeamScore";

    // Columns for Config Table
    public static final String CONFIG_ID = "Id";
    public static final String CONFIG_NAME = "Name";
    public static final String CONFIG_VALUE = "Value";

    /**
     * The Constructor for the ScoreKeeperDB class.  Takes the context as its only parameter
     *
     * @param context Context to use to open or create the database
     */
    public ScoreKeeperDb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "Creating Leagues Table");
        String CREATE_LEAGUES_SQL = "CREATE TABLE " + TABLE_LEAGUES + "("
                + LEAGUE_ID + " INTEGER PRIMARY KEY, "
                + LEAGUE_NAME + " TEXT NOT NULL);";
        db.execSQL(CREATE_LEAGUES_SQL);

        Log.v(TAG, "Creating Teams Table");
        String CREATE_TEAMS_SQL = "CREATE TABLE " + TABLE_TEAMS + "("
                + TEAMS_ID + " INTEGER PRIMARY KEY, "
                + TEAMS_LEAGUE_ID + " INTEGER, "
                + TEAMS_NAME + " TEXT NOT NULL, "
                + TEAMS_HOME_CITY + " TEXT NOT NULL, "
                + TEAMS_LATITUDE + " REAL NOT NULL, "
                + TEAMS_LONGITUDE + " REAL NOT NULL, "
                + "FOREIGN KEY (" + TEAMS_LEAGUE_ID + ") REFERENCES " + TABLE_LEAGUES + "(" + LEAGUE_ID + "));";
        db.execSQL(CREATE_TEAMS_SQL);

        Log.v(TAG, "Creating Games Table");
        String CREATE_GAMES_SQL = "CREATE TABLE " + TABLE_GAMES + "("
                + GAMES_ID + " INTEGER PRIMARY KEY, "
                + GAMES_HOME_TEAM_ID + " INTEGER NOT NULL, "
                + GAMES_HOSTING_TEAM_ID + " INTEGER NOT NULL, "
                + GAMES_KICKOFF_TIME + " INTEGER, "
                + GAMES_DURATION + " INTEGER, "
                + GAMES_VISITING_TEAM_ID + " INTEGER, "
                + GAMES_HOME_TEAM_SCORE + " INTEGER, "
                + GAMES_VISITING_TEAM_SCORE + " INTEGER, "
                + "FOREIGN KEY (" + GAMES_HOME_TEAM_ID + ") REFERENCES " + TABLE_TEAMS + "(" + TEAMS_ID + "), "
                + "FOREIGN KEY (" + GAMES_HOSTING_TEAM_ID + ") REFERENCES " + TABLE_TEAMS + "(" + TEAMS_ID + "), "
                + "FOREIGN KEY (" + GAMES_VISITING_TEAM_ID + ") REFERENCES " + TABLE_TEAMS + "(" + TEAMS_ID + "));";
        db.execSQL(CREATE_GAMES_SQL);

        Log.v(TAG, "Creating Config Table");
        String CREATE_CONFIG_SQL = "CREATE TABLE " + TABLE_CONFIG + "("
                + CONFIG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CONFIG_NAME + " TEXT NOT NULL, "
                + CONFIG_VALUE + " TEXT);";
        db.execSQL(CREATE_CONFIG_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ScoreKeeperDb.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFIG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAGUES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);

        onCreate(db);
    }

    /*
     * Public Helper Methods
     *
     */

    /**
     * Updates the values for a table in the database.  This method just calls the array version by
     * converting the Lists to array's.
     *
     * @param tableName   The name of the table to update
     * @param columnNames The names of the columns to update
     * @param values      The new values for the columns passed.
     * @param idName      The name of the ID column for the table
     * @param id          The ID of the Row to update
     * @return The number of rows updated
     * @throws Exception Throws an exception if size of columnNames != size of values
     */
    public long updateValues(String tableName, List<String> columnNames, List<String> values, String idName, int id) throws Exception {
        return this.updateValues(tableName, columnNames.toArray(new String[columnNames.size()]), values.toArray(new String[values.size()]), idName, id);
    }

    /**
     * Updates the values for a table in the database
     *
     * @param tableName   The name of the table to update
     * @param columnNames The names of the columns to update
     * @param values      The new values for the columns passed.
     * @param idName      The name of the ID column for the table
     * @param id          The ID of the Row to update
     * @return The number of rows updated
     * @throws Exception Throws an exception if size of columnNames != size of values
     */
    public long updateValues(String tableName, String[] columnNames, String[] values, String idName, int id) throws Exception {
        if (columnNames.length != values.length) {
            throw new Exception("'names' and 'values' must have same number of elements.");
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vals = new ContentValues();

        for (int i = 0; i < columnNames.length; i++) {
            vals.put(columnNames[i], values[i]);
        }

        // SQLiteDatabase.update(...) actually returns an int but this seemed stupid since
        // SQLiteDatabase.insert(...) returns a long so we return a long for both.
        return db.update(tableName, vals, idName + " = " + id, null);
    }

    /**
     * Inserts values into a table in the database.  This method just calls the array version by
     * converting the Lists to array's.
     *
     * @param tableName   The name of the table to insert into
     * @param columnNames The names of the columns to insert
     * @param values      The new values for the columns passed.
     * @return The number of rows updated
     * @throws Exception Throws an exception if size of columnNames != size of values
     */
    public long insertValues(String tableName, List<String> columnNames, List<String> values) throws Exception {
        return this.insertValues(tableName, columnNames.toArray(new String[columnNames.size()]), values.toArray(new String[values.size()]));
    }

    /**
     * Inserts values into a table in the database.
     * converting the Lists to array's.
     *
     * @param tableName   The name of the table to insert into
     * @param columnNames The names of the columns to insert
     * @param values      The new values for the columns passed.
     * @return The number of rows updated
     * @throws Exception Throws an exception if size of columnNames != size of values
     */
    public long insertValues(String tableName, String[] columnNames, String[] values) throws Exception {
        if (columnNames.length != values.length) {
            throw new Exception("'names' and 'values' must have same number of elements.");
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vals = new ContentValues();
        long rowsChanged = 0;

        for (int i = 0; i < columnNames.length; i++) {
            vals.put(columnNames[i], values[i]);
        }

        try {
            rowsChanged = db.insertOrThrow(tableName, null, vals);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

        return rowsChanged;
    }

    /**
     * Executes the given SQL statement and returns the results as a Cursor
     *
     * @param sql A valid SQL statement to execute
     * @return A Cursor containing the results of the query
     */
    public Cursor executeQuery(String sql) {
        Cursor result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        result = db.rawQuery(sql, null);
        return result;
    }

    /**
     * Determines if the specified record exists
     *
     * @param tableName The name of the table to search
     * @param recordId  The ID of the record to search for
     * @return True if the record is found, otherwise false
     */
    public boolean doesRecordExist(String tableName, int recordId) {
        boolean result = false;

        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE Id = " + recordId;
        Cursor c = executeQuery(sql);
        result = (c != null && c.getCount() > 0);
        c.close();

        return result;
    }

    /*
    * Config Getters and Setters
    **/

    /**
     * Sets the last time the database was synced with the server
     *
     * @param date The Date object representing the last sync time
     */
    public void setLastUpdateTime(Date date) {
        setConfig(ConfigNames.LAST_UPDATED, Long.toString(date.getTime()));
    }

    /**
     * Gets the last time the database was synced with the server
     *
     * @return A Date object, if never synced this returns null
     */
    public Date getLastUpdateTime() {
        Date result = null;

        String resultString = getConfig(ConfigNames.LAST_UPDATED);
        if (resultString.length() > 0) {
            long resultAsMillis = Long.parseLong(resultString);
            result = new Date(resultAsMillis);
        }

        return result;
    }

    /**
     * Sets the last currently configured Team ID
     *
     * @param teamId The ID of the team to use
     */
    public void setSelectedTeamId(int teamId) {
        setConfig(ConfigNames.SELECTED_TEAM, Integer.toString(teamId));
    }

    /**
     * Gets the ID of the currently configured Team
     *
     * @return The ID of the Team
     */
    public int getSelectedTeamId() {
        int result = -1;

        String resultString = getConfig(ConfigNames.SELECTED_TEAM);
        if (resultString.length() > 0) {
            result = Integer.parseInt(resultString);
        }

        return result;
    }

    /**
     * Sets the ID of the configured Game
     *
     * @param gameId The ID of the Game
     */
    public void setSelectedGameId(int gameId) {
        setConfig(ConfigNames.SELECTED_GAME, Integer.toString(gameId));
    }

    /**
     * Gets the ID of the currently configured Game
     *
     * @return The ID of the Game
     */
    public int getSelectedGameId() {
        int result = -1;

        String resultString = getConfig(ConfigNames.SELECTED_GAME);
        if (resultString.length() > 0) {
            result = Integer.parseInt(resultString);
        }

        return result;
    }

    public void setSelectedTeamColor(int color){
        String value = String.format("#%06X", 0xFFFFFF & color);
        setConfig(ConfigNames.TEAM_COLOR, value);
    }

    public int getSelectedTeamColor() {
        int result = -1;

        String resultString = getConfig(ConfigNames.TEAM_COLOR);
        if(resultString != null && resultString.length() > 0){
            result = Color.parseColor(resultString);
        }

        return result;
    }

    /*
    * Private Helper Methods
    * */

    private long setConfig(String configName, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvs = new ContentValues();
        long rowsUpdated = 0;

        if (doesConfigExist(configName)) {
            cvs.put(CONFIG_VALUE, value);
            rowsUpdated = db.update(TABLE_CONFIG, cvs, CONFIG_NAME + " = '" + configName + "'", null);
        } else {
            cvs.put(CONFIG_NAME, configName);
            cvs.put(CONFIG_VALUE, value);
            try {
                rowsUpdated = db.insertOrThrow(TABLE_CONFIG, null, cvs);
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
        }

        return rowsUpdated;
    }

    private String getConfig(String configName) {
        String result = "";

        if (doesConfigExist(configName)) {
            String sql = "SELECT " + CONFIG_VALUE
                    + " FROM " + TABLE_CONFIG
                    + " WHERE " + CONFIG_NAME + " = '" + configName + "';";

            Cursor c = executeQuery(sql);

            if (c.moveToFirst()) {
                result = c.getString(0);
            }

            c.close();
        }

        return result;
    }

    private boolean doesConfigExist(String name) {
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = new String[]{CONFIG_NAME, CONFIG_VALUE};
        String sql = "SELECT * FROM " + TABLE_CONFIG + " WHERE " + CONFIG_NAME + " = '" + name + "';";
        Cursor c = db.rawQuery(sql, null);

        // Dump it in a variable first so we can check it when debugging
        result = (c != null && c.getCount() > 0);
        c.close();
        return result;
    }

    private class ConfigNames {
        public static final String LAST_UPDATED = "LAST_UPDATED";
        public static final String SELECTED_TEAM = "SELECTED_TEAM";
        public static final String SELECTED_GAME = "SELECTED_GAME";
        public static final String TEAM_COLOR = "TEAM_COLOR";
    }
}