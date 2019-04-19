package com.example.cyhunt.cyhunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ObjectiveScreen extends AppCompatActivity {

    private ListView listView;
    private List<Objective> objectives = new ArrayList<>();
    private double longitude = -93.65095;
    private double latitude = 42.02965;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective_screen);


        listView = (ListView) findViewById(R.id.listview);

        final List<String> objectivesName = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, objectivesName);
        setUp();
        for (int i = 0; i < objectives.size(); i++) {
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
                    Toast.makeText(getApplicationContext(), "You found the " + selectedObjective + "!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Not close enough to: " + selectedObjective + "!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void setUp(){
        objectives.add(new Objective(42.02965, -93.65095, 5.0, "Armory", ""));
        objectives.add(new Objective(42.02856, -93.64467, 5.0, "Bessey Hall", ""));
        objectives.add(new Objective(42.03469, -93.64558, 5.0, "Administrative Building", ""));
        objectives.add(new Objective(42.02523, -93.64931, 5.0, "Enrollment Services Center", "" ));
        objectives.add(new Objective(42.02531, -93.64839, 5.0, "Carver Hall", ""));
        objectives.add(new Objective(42.02992, -93.64016, 5.0, "Agronomy Greenhouse", ""));
        objectives.add(new Objective(42.02363, -93.64155, 5.0, "Birch Residence Hall", ""));
        objectives.add(new Objective(42.02992, -93.64203, 5.0, "Crop Genome Informatics Laboratory", ""));
        objectives.add(new Objective(42.02439, -93.64154, 5.0, "Barton Residence Hall", ""));
        objectives.add(new Objective(42.03109, -93.65119, 5.0, "Communications Building", ""));
        objectives.add(new Objective(42.02595, -93.64999, 5.0, "Pearson Hall", ""));
    }

    public static double distFrom(double userLat, double userLong, double objLat, double objLong) {
        double earthRadius = 3958.75; // miles (or 6371.0 kilometers)
        double dLat = Math.toRadians(objLat-userLat);
        double dLng = Math.toRadians(objLong-userLong);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(objLat));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }

}
