package schellekens.scorekeeper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import schellekens.scorekeeper.service.apicalls.Games;
import schellekens.scorekeeper.service.apicalls.Leagues;
import schellekens.scorekeeper.service.apicalls.Scores;
import schellekens.scorekeeper.service.apicalls.Teams;
import schellekens.scorekeeper.service.types.Game;
import schellekens.scorekeeper.service.types.Team;

public class ScoreActivity extends AppCompatActivity {

    private static final String OUR_SCORE_STATE_KEY = "OUR_SCORE";
    private static final String THEIR_SCORE_STATE_KEY = "THEIR_SCORE";
    public static final int SELECT_GAME_RESULT_KEY = 10;
    public static final int SELECT_TEAM_RESULT_KEY = 20;
    public static final int SELECT_TEAM_COLOR_KEY = 30;

    Button mAddUs;
    Button mAddThem;
    Button mSubUs;
    Button mSubThem;
    TextView mOurScoreView;
    TextView mTheirScoreView;
    TextView mUsCaption;
    TextView mThemCaption;
    android.support.v7.widget.Toolbar mToolBar;

    int mOurScore = 0;
    int mTheirScore = 0;

    // Db Interfaces
    private ScoreKeeperDb mScoreKeeperDb;
    private Leagues mLeaguesConnection;
    private Teams mTeamsConnection;
    private Games mGamesConnection;
    private Scores mScoreConnection;
    private Team mMyTeam;
    private Game mMyGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mAddUs = (Button) findViewById(R.id.btnAddUs);
        mAddThem = (Button) findViewById(R.id.btnAddThem);
        mSubUs = (Button) findViewById(R.id.btnSubUs);
        mSubThem = (Button) findViewById(R.id.btnSubThem);
        mOurScoreView = (TextView) findViewById(R.id.tvUs);
        mTheirScoreView = (TextView) findViewById(R.id.tvThem);
        mUsCaption = (TextView) findViewById(R.id.tvUsCaption);
        mThemCaption = (TextView) findViewById(R.id.tvThemCaption);
        mToolBar = (android.support.v7.widget.Toolbar) findViewById(R.id.tbMain);
        mToolBar.setTitleTextAppearance(getApplicationContext(), R.style.AppTheme_MyActionBarStyle_TitleTextStyle);

        setSupportActionBar(mToolBar);

        if (savedInstanceState != null) {
            mOurScore = savedInstanceState.getInt(OUR_SCORE_STATE_KEY);
            mTheirScore = savedInstanceState.getInt(THEIR_SCORE_STATE_KEY);
        }

        mOurScoreView.setText(Integer.toString(mOurScore));
        mTheirScoreView.setText(Integer.toString(mTheirScore));

        mAddUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOurScore++;
                mOurScoreView.setText(Integer.toString(mOurScore));

                if (mMyGame != null) {
                    if (mMyGame.getHomeTeamId() == mMyTeam.getId()) {
                        mMyGame.setHomeTeamScore(mOurScore);
                    } else {
                        mMyGame.setVisitingTeamScore(mOurScore);
                    }

                    mGamesConnection.updateGameLocal(mMyGame);
                }
            }
        });

        mAddThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTheirScore++;
                mTheirScoreView.setText(Integer.toString(mTheirScore));

                if (mMyGame != null) {
                    if (mMyGame.getHomeTeamId() == mMyTeam.getId()) {
                        mMyGame.setVisitingTeamScore(mTheirScore);
                    } else {
                        mMyGame.setHomeTeamScore(mTheirScore);
                    }

                    mGamesConnection.updateGameLocal(mMyGame);
                }
            }
        });

        mSubUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOurScore > 0) {
                    mOurScore--;
                    mOurScoreView.setText(Integer.toString(mOurScore));

                    if (mMyGame != null) {
                        if (mMyGame.getHomeTeamId() == mMyTeam.getId()) {
                            mMyGame.setHomeTeamScore(mOurScore);
                        } else {
                            mMyGame.setVisitingTeamScore(mOurScore);
                        }

                        mGamesConnection.updateGameLocal(mMyGame);
                    }
                }
            }
        });

        mSubThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTheirScore > 0) {
                    mTheirScore--;
                    mTheirScoreView.setText(Integer.toString(mTheirScore));

                    if (mMyGame != null) {
                        if (mMyGame.getHomeTeamId() == mMyTeam.getId()) {
                            mMyGame.setVisitingTeamScore(mTheirScore);
                        } else {
                            mMyGame.setHomeTeamScore(mTheirScore);
                        }

                        mGamesConnection.updateGameLocal(mMyGame);
                    }
                }
            }
        });

        this.mScoreKeeperDb = new ScoreKeeperDb(getApplicationContext());
        this.mLeaguesConnection = new Leagues(this.getApplicationContext(), this.mScoreKeeperDb);
        this.mTeamsConnection = new Teams(this.getApplicationContext(), this.mScoreKeeperDb);
        this.mGamesConnection = new Games(this.getApplicationContext(), this.mScoreKeeperDb);
        this.mScoreConnection = new Scores();
        this.mMyTeam = this.mTeamsConnection.getSelectedTeam();
        this.mMyGame = this.mGamesConnection.getSelectedGame();
        this.updateScores();
        this.applyTeamColor(this.getTeamColor());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(OUR_SCORE_STATE_KEY, mOurScore);
        outState.putInt(THEIR_SCORE_STATE_KEY, mTheirScore);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_game:
                this.launchGameSelector();
                return true;
            case R.id.action_refresh:
                refreshDb();
                return true;
            case R.id.action_save_game:
                // Save the current Score back up to the server
                // We need to make sure we have a Game selected first
                if (this.mMyGame != null) {
                    Toast.makeText(this, "Submitting Score", Toast.LENGTH_SHORT).show();
                    this.mScoreConnection.submitScore(this.getApplicationContext(), this.mMyGame);
                }
                return true;
            case R.id.action_team:
                // Select a Team
                this.launchTeamSelector();
                return true;
            case R.id.action_color:
                Intent intent = new Intent(this, ColorActivity.class);
                intent.putExtra(ColorActivity.TEAM_COLOR, getTeamColor());
                startActivityForResult(intent, SELECT_TEAM_COLOR_KEY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_GAME_RESULT_KEY:
                    if (data.hasExtra(SelectActivity.RESULT_KEY)) {
                        this.mScoreKeeperDb.setSelectedGameId(data.getIntExtra(SelectActivity.RESULT_KEY, -1));
                        this.mMyGame = this.mGamesConnection.getSelectedGame();

                        if (this.mMyGame == null) {
                            Toast.makeText(this, "Error Selecting Game", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Game Selected", Toast.LENGTH_SHORT).show();
                            this.updateScores();
                        }
                    } else {
                        Toast.makeText(this, "Error Selecting Game", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case SELECT_TEAM_RESULT_KEY:
                    // We set the current team in the db, confirm it worked and show success.
                    if (data.hasExtra(SelectActivity.RESULT_KEY)) {
                        this.mScoreKeeperDb.setSelectedTeamId(data.getIntExtra(SelectActivity.RESULT_KEY, -1));
                        this.mMyTeam = this.mTeamsConnection.getSelectedTeam();

                        if (this.mMyTeam == null) {
                            Toast.makeText(this, "Error Selecting Team", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Team Selected", Toast.LENGTH_SHORT).show();
                            this.launchGameSelector();
                        }
                    } else {
                        Toast.makeText(this, "Error Selecting Team", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case SELECT_TEAM_COLOR_KEY:
                    int teamColor = getTeamColor();
                    if (data != null && data.hasExtra(ColorActivity.TEAM_COLOR)) {
                        teamColor = data.getIntExtra(ColorActivity.TEAM_COLOR, getTeamColor());
                    }

                    this.applyTeamColor(teamColor);
                    break;
                default:
                    Toast.makeText(this, "Unknown Request Code: " + requestCode, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /*
     * Helper Methods
     */
    private void launchGameSelector() {
        // we need to make sure we have a Team selected first
        if (this.mMyTeam == null) {
            this.mMyTeam = mTeamsConnection.getSelectedTeam();
        }

        if (this.mMyTeam == null) {
            Toast.makeText(this, "You must select a Team first", Toast.LENGTH_SHORT).show();
        } else {
            List<Game> games = this.mGamesConnection.getTeamGames(this.mMyTeam.getId(), false);
            Bundle mBundle = new Bundle();
            mBundle.putParcelableArrayList(SelectActivity.LIST_KEY, new ArrayList<Game>(games));

            Intent intent = new Intent(this, SelectActivity.class);
            intent.putExtras(mBundle);
            intent.putExtra(SelectActivity.LIST_TYPE_KEY, SelectActivity.GAME_LIST);
            intent.putExtra(SelectActivity.TEAM_KEY, this.mMyTeam.getId());

            if (this.mMyGame == null) {
                this.mMyGame = this.mGamesConnection.getSelectedGame();
            }

            if (this.mMyGame != null) {
                intent.putExtra(SelectActivity.GAME_KEY, this.mMyGame.getId());
            }

            startActivityForResult(intent, SELECT_GAME_RESULT_KEY);
        }
    }

    private void launchTeamSelector() {
        Intent intent = new Intent(this, SelectActivity.class);
        intent.putExtra(SelectActivity.LIST_TYPE_KEY, SelectActivity.TEAM_LIST);

        if (this.mMyTeam == null) {
            this.mMyTeam = this.mTeamsConnection.getSelectedTeam();
        }

        List<Team> teams = this.mTeamsConnection.getAllTeams();
        Bundle mBundle = new Bundle();
        mBundle.putParcelableArrayList(SelectActivity.LIST_KEY, new ArrayList<Team>(teams));
        intent.putExtras(mBundle);

        if (this.mMyTeam != null) {
            intent.putExtra(SelectActivity.TEAM_KEY, this.mMyTeam.getId());
        }

        startActivityForResult(intent, SELECT_TEAM_RESULT_KEY);
    }

    private void updateScores() {
        if (this.mMyTeam != null && this.mMyGame != null) {
            if (this.mMyTeam.getId() == this.mMyGame.getHomeTeamId()) {
                this.mOurScore = this.mMyGame.getHomeTeamScore();
                this.mTheirScore = this.mMyGame.getVisitingTeamScore();
                this.mOurScoreView.setText(Integer.toString(this.mOurScore));
                this.mTheirScoreView.setText(Integer.toString(this.mTheirScore));
            } else {
                this.mOurScore = this.mMyGame.getVisitingTeamScore();
                this.mTheirScore = this.mMyGame.getHomeTeamScore();
                this.mOurScoreView.setText(Integer.toString(this.mOurScore));
                this.mTheirScoreView.setText(Integer.toString(this.mTheirScore));
            }
        }
    }

    private void refreshDb() {
        // TODO: need to notify when Refresh is done and update local Score, do something like
        // refreshLeagues(Context context, boolean propogate); --> Calls refreshTeams();
        // refreshTeams(Context context, boolean propogate);   --> Calls refreshGames();
        // refreshGames(Context context);
        // OR
        // We add the call to ScoreKeeperDB and call it "refresh" and then do it in that order?
        // this won't work since we need an async task to do this and we don't ScoreKeeperDB isn't
        mLeaguesConnection.refreshLeagues(this.mTeamsConnection, this.mGamesConnection);
    }

    private int getTeamColor() {
        int colorInt = this.mScoreKeeperDb.getSelectedTeamColor();
        if (colorInt == -1) {
            colorInt = ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null);
        }

        return colorInt;
    }

    private void applyTeamColor(int teamColor) {
        this.mOurScoreView.setTextColor(teamColor);
        this.mUsCaption.setTextColor(teamColor);
        this.mToolBar.setBackgroundColor(teamColor);
        this.mScoreKeeperDb.setSelectedTeamColor(teamColor);
    }
}