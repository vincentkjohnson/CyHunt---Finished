package com.example.cyhunt.cyhunt;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class LeaderboardFragment extends Fragment implements ApiAuthenticationClient.ApiResultHandler{

    private OnFragmentInteractionListener mListener;
    int position;
    private ListView listView;
    private String user;

    List<LeaderboardEntry> list = new ArrayList<>();
    ArrayAdapter adapter;
    private final ApiAuthenticationClient connector = new ApiAuthenticationClient((ApiAuthenticationClient.ApiResultHandler)this);

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
        leaderboardFragment.setArguments(bundle);
        return leaderboardFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onFragmentInteraction("Custom Title");
        }

        return inflater.inflate(R.layout.leader_board_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.leaderboard_list);
        //final List<String> list = new ArrayList<>();

        adapter = new ArrayAdapter(this.getActivity(), R.layout.activity_listview, list);

        if(position == 0) {
            connector.getLeaderBoard("daily");
        } else if (position == 1 ) {
            connector.getLeaderBoard("weekly");
        } else {
            adapter.add("error: position does not exist: " + Integer.toString(position));
        }
        listView.setAdapter(adapter);

        //focus on user ranking if possible



    }

    @Override
    public void handleResult(UserResponse response) {

    }

    @Override
    public void handleObjectiveListResult(List<Objective> objectives) {

    }

    @Override
    public void handleLeaderBoardListResult(final List<LeaderboardEntry> leaderboards) {
        if (leaderboards != null && !leaderboards.isEmpty()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //get list of users in position order
                    adapter.clear();
                    for(LeaderboardEntry leader : leaderboards){
                        String nextEntry = leader.getPosition() + ". " + leader.getUsername() +
                                "\t\t\tScore: " + leader.getScore();
                        adapter.add(nextEntry);
                    }

                }
            });
        }
    }

    @Override
    public void handleScoreUpdateResult(ScoreResponse result) {

    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }
}