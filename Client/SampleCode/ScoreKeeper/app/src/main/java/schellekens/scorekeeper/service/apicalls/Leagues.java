package schellekens.scorekeeper.service.apicalls;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import schellekens.scorekeeper.ScoreKeeperDb;
import schellekens.scorekeeper.service.types.League;

/**
 * schellekens.scorekeeper.service.apicalls Created by bschellekens on 11/30/2017.
 */
public class Leagues implements GetTask.GetResultHandler {
    private final String TAG = this.getClass().getSimpleName();

    private ScoreKeeperDb _db;
    private boolean _leaguesRefreshing = false;
    private Context _appContext;
    private Teams _teamConnection = null;
    private Games _gameConnection = null;

    public Leagues(Context context, ScoreKeeperDb db) {
        this._appContext = context;
        this._db = db;
    }

    /**
     * Starts an async task to refresh the data in the Leagues table from the server
     */
    public void refreshLeagues(Teams teamConnection, Games gameConnection) {
        if (this._leaguesRefreshing) return;

        this._leaguesRefreshing = true;
        try {
            this._teamConnection = teamConnection;
            this._gameConnection = gameConnection;
            String url = ApiUrls.getGetAllLeaguesUrl();
            GetTask task = new GetTask(Leagues.this);
            task.execute(url);
        } catch (java.net.MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void getResult(String result) {
        if (this._leaguesRefreshing) {
            try {
                List<League> data = League.DeserializeList(result);
                this.updateLeaguesLocal(data);
                this._db.setLastUpdateTime(new Date());
                Toast.makeText(this._appContext, "Leagues Updated", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
                Toast.makeText(this._appContext, "League Updates Failed: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                this._leaguesRefreshing = false;

                if(this._teamConnection != null){
                    this._teamConnection.refreshTeams(this._gameConnection);
                }
            }
        }
    }

    // Helper method, updates the Leagues in the local SQLite DB
    private void updateLeaguesLocal(List<League> data) {
        List<String> values;
        List<String> names = new ArrayList<>();
        names.add(ScoreKeeperDb.LEAGUE_ID);
        names.add(ScoreKeeperDb.LEAGUE_NAME);

        for (League l : data) {
            values = new ArrayList<>();
            values.add(Integer.toString(l.getId()));
            values.add("'" + l.getName() + "'");

            try {
                if (this._db.doesRecordExist(ScoreKeeperDb.TABLE_LEAGUES, l.getId())) {
                    // If it already exists we'll update it
                    this._db.updateValues(ScoreKeeperDb.TABLE_LEAGUES, names, values, ScoreKeeperDb.LEAGUE_ID, l.getId());
                } else {
                    // If it does not yet exist, we will create it
                    this._db.insertValues(ScoreKeeperDb.TABLE_LEAGUES, names, values);
                }
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
        }
    }
}