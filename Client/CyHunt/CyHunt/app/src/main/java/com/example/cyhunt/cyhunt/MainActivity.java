package com.example.cyhunt.cyhunt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;


public class MainActivity extends AppCompatActivity implements Runnable {

    static private String[] locationList;

    private ListView locations;
    private Button checkLocation;
    private Button leaderBoard;
    private TextView scoreText;

    private List<Objective> objectives = new Objective().getObjectives();

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

        /**
         * Push checkLocation button,
         * compare userLocation with locations from building location
         */
        checkLocation = (Button) findViewById(R.id.check_location);
        checkLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UserLocation location = new UserLocation();
                    //Compare location
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        leaderBoard = (Button) findViewById(R.id.leaderboard);

        scoreText = (TextView) findViewById(R.id.score);

        setLocationList(new String[]{"1","2","3"});

        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, locationList);
        locations = (ListView) findViewById(R.id.locations);
        locations.setAdapter(adapter);

        //(new Thread(new MainActivity())).start();
    }

    @Override
    public void run() {

    }

    /**
     * used to update the location list whenever possible. Can also be used to notify anything
     * automatically
     */

}
