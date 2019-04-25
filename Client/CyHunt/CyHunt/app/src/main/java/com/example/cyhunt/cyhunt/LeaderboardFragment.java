package com.example.cyhunt.cyhunt;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;


public class LeaderboardFragment extends Fragment {
    int position;
    private ListView listView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leader_board_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.leaderboard_list);
        final List<String> list = new ArrayList<>();

        final ArrayAdapter adapter = new ArrayAdapter(this.getActivity(), R.layout.activity_listview, list);
        if(position == 0) {
            adapter.add("daily");
        } else if (position == 1 ) {
            adapter.add("weekly");
        } else {
            adapter.add("error");
        }
        listView.setAdapter(adapter);

        //focus on user ranking if possible



    }
}