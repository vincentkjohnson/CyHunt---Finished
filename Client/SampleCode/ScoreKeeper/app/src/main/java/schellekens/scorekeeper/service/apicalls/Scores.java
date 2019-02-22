package schellekens.scorekeeper.service.apicalls;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import schellekens.scorekeeper.service.types.Game;

/**
 * schellekens.scorekeeper.service.apicalls Created by bschellekens on 12/5/2017.
 */

public class Scores implements PostTask.PostResultHandler {
    private final String TAG = this.getClass().getSimpleName();

    private boolean _updatingScore = false;
    private Context _scoreUpdatingContext = null;

    public void submitScore(Context context, Game g) {
        if (this._updatingScore) return;

        this._updatingScore = true;
        this._scoreUpdatingContext = context;
        String url = ApiUrls.getPostScoreUrl();
        PostTask task = new PostTask(Scores.this);
        String[] params = new String[3];

        PostData pData = new PostData(
                url,
                String.format("{ \"GameId\" : %d, \"HomeTeamScore\" : %d, \"VisitingTeamScore\" : %d }", g.getId(), g.getHomeTeamScore(), g.getVisitingTeamScore())
        );

        task.execute(pData);
    }

    @Override
    public void getResult(String result) {
        if (this._updatingScore) {
            this._updatingScore = false;
            if (result.equals("200")) {
                Toast.makeText(this._scoreUpdatingContext, "Score Updated", Toast.LENGTH_SHORT).show();
                this._scoreUpdatingContext = null;
            } else {
                Toast.makeText(this._scoreUpdatingContext, "Error Updating Score: " + result, Toast.LENGTH_SHORT).show();
                this._scoreUpdatingContext = null;
                Log.e(TAG, "Error Updating Score: " + result);
            }
        }
    }
}