package com.example.cyhunt.cyhunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ObjectiveScreen extends AppCompatActivity implements ApiAuthenticationClient.ApiResultHandler {

    private static ObjectiveScreen parent;
    private final ApiAuthenticationClient connector = new ApiAuthenticationClient((ApiAuthenticationClient.ApiResultHandler)this);
    private ListView listView;
    private List<Objective> objectives = new ArrayList<>();
    private double longitude = -93.64999;
    private double latitude = 42.02595;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective_screen);
        parent = this;
        connector.getObjectives();

        listView = (ListView) findViewById(R.id.listview);
        final List<String> objectivesName = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, objectivesName);
        for (int i = 0; i < objectives.size(); i++) {
            Log.e("test", objectives.get(i).getName());
            adapter.add(objectives.get(i).getName());
        }
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedObjective = objectivesName.get(position);
                Objective selObj = null;
                for (int i = 0; i < objectivesName.size(); i++) {
                    if (objectives.get(i).getName() == selectedObjective) {
                        selObj = objectives.get(i);
                    }
                }
                if (distFrom(latitude, longitude, selObj.getLatitude(), selObj.getLongitude()) < 0.005) {
                    Toast.makeText(getApplicationContext(), "You found " + selectedObjective + "!", Toast.LENGTH_LONG).show();
                    adapter.remove(selectedObjective);
                } else {
                    Toast.makeText(getApplicationContext(), "You're not close enough to " + selectedObjective + "!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static double distFrom(double userLat, double userLong, double objLat, double objLong) {
        double earthRadius = 3958.75; // miles (or 6371.0 kilometers)
        double dLat = Math.toRadians(objLat - userLat);
        double dLng = Math.toRadians(objLong - userLong);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(objLat));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist;
    }

    @Override
    public void handleResult(UserResponse response) {

    }

    @Override
    public void handleObjectiveListResult(final List<Objective> objectivesList) {
        if (objectivesList != null && !objectivesList.isEmpty()) {
            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    objectives = objectivesList;
                    Toast.makeText(getApplicationContext(), "Objectives Received", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
