package com.example.cyhunt.cyhunt;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements Runnable{

    static private String[] locationList;

    private ListView locations;
    private Button checkLocation;
    private Button leaderBoard;
    private TextView scoreText;

    ArrayAdapter adapter;
    /**
     * Only a method to test if the locations are fully imported into the locationList array.
     * @return
     */
    public static String[] getLocationList() {
        return locationList;
    }

    public static void setLocationList(String[] list) {
        locationList = list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLocation = (Button) findViewById(R.id.check_location);
        leaderBoard = (Button) findViewById(R.id.leaderboard);

        scoreText = (TextView) findViewById(R.id.score);

        setLocationList(new String[]{"1","2","3"});

        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, locationList);
        locations = (ListView) findViewById(R.id.locations);
        locations.setAdapter(adapter);

        //(new Thread(new MainActivity())).start();
    }

    /**
     * used to update the location list whenever possible. Can also be used to notify anything
     * automatically
     */
    @Override
    public void run() {
        while(true) {
            adapter.notifyDataSetChanged();
        }
    }
}
