package com.example.cyhunt.cyhunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        list = (ListView) findViewById(R.id.leaderboard_list);
        ArrayList<String> leaders = new ArrayList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, leaders);

        list.setAdapter(adapter);

    }
}
