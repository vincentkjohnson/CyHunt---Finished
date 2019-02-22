package schellekens.scorekeeper;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import schellekens.scorekeeper.adapters.ApiResultAdapter;
import schellekens.scorekeeper.service.apicalls.Games;
import schellekens.scorekeeper.service.apicalls.Teams;
import schellekens.scorekeeper.service.types.Game;
import schellekens.scorekeeper.service.types.Team;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SelectActivity extends ListActivity {

    public static final String TEAM_KEY = "TEAM_KEY";
    public static final String GAME_KEY = "GAME_KEY";
    public static final String RESULT_KEY = "RESULT_KEY";
    public static final String LIST_KEY = "LIST_KEY";
    public static final String LIST_TYPE_KEY = "TYPE_KEY";
    public static final String TEAM_LIST = "TEAM_LIST";
    public static final String GAME_LIST = "GAME_LIST";

    private Button mSaveButton;
    private ListView mListView;
    private int _selectedId = -1;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Intent intent = getIntent();
        this.mSaveButton = (Button) findViewById(R.id.btnSet);
        this.mListView = (ListView) findViewById(android.R.id.list);
        this.mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        if (intent.hasExtra(LIST_TYPE_KEY)) {
            action = intent.getStringExtra(LIST_TYPE_KEY);

            if (action.equals(TEAM_LIST)) {
                this.prepForTeamSelect(intent);
            } else if (action.equals(GAME_LIST)) {
                this.prepForGameSelect(intent);
            } else {
                this.finish();
            }
        } else {
            this.finish();
        }

        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
                String v = "";
                TextView idView = (TextView) view.findViewById(R.id.tvId);
                _selectedId = Integer.parseInt(idView.getText().toString());
            }
        });

        this.mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveButton.setEnabled(false);
                Intent intent = getIntent();
                intent.putExtra(RESULT_KEY, _selectedId);
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
    }

    private void prepForTeamSelect(Intent intent) {
        List<Team> teams = intent.getParcelableArrayListExtra(LIST_KEY);

        int teamId = -1;
        if (intent.hasExtra(TEAM_KEY)) {
            teamId = intent.getIntExtra(TEAM_KEY, -1);
        }

        ApiResultAdapter adapter = new ApiResultAdapter(SelectActivity.this, R.layout.select_entry, teams, teamId);
        setListAdapter(adapter);

        if (teamId > -1) {
            int position = adapter.getPositionForId(teamId);
            if (position > -1) {
                mListView.setItemChecked(position, true);
                _selectedId = teamId;
            }
        }
    }

    private void prepForGameSelect(Intent intent) {
        int teamId = -1;
        int gameId = -1;

        if (intent.hasExtra(TEAM_KEY)) {
            teamId = intent.getIntExtra(TEAM_KEY, -1);
        }

        if (intent.hasExtra(GAME_KEY)) {
            gameId = intent.getIntExtra(GAME_KEY, -1);
        }

        List<Game> games = intent.getParcelableArrayListExtra(LIST_KEY);
        ApiResultAdapter adapter = new ApiResultAdapter(SelectActivity.this, R.layout.select_entry, games, teamId);
        setListAdapter(adapter);

        if (gameId > -1) {
            int position = adapter.getPositionForId(gameId);
            if (position > -1) {
                mListView.setItemChecked(position, true);
                _selectedId = gameId;
            }
        }
    }
}