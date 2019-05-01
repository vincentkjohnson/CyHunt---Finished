package com.example.cyhunt.cyhunt;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;


public class ObjectiveScreen extends AppCompatActivity implements ApiAuthenticationClient.ApiResultHandler, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static ObjectiveScreen parent;
    private final ApiAuthenticationClient connector = new ApiAuthenticationClient((ApiAuthenticationClient.ApiResultHandler) this);
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<Objective> objectives = new ArrayList<>();
    Objective selObj = null;
    private double longitude = -93.64999;
    private double latitude = 42.02595;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String user;
    private int score;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective_screen);
        user = (String)getIntent().getExtras().get("username");
        final String username = user;
        parent = this;

        this.setTitle("Score: " + score);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

        listView = (ListView) findViewById(R.id.listview);
        final List<String> objectivesName = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, objectivesName);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    return;
                }
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    Toast.makeText(getApplicationContext(), "Latitude: " + latitude + " Longitude: " +longitude, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "error: null last location", Toast.LENGTH_LONG).show();
                    return;
                }
                String selectedObjective = objectivesName.get(position);
                for (int i = 0; i < objectivesName.size(); i++) {
                    if (objectives.get(i).getName() == selectedObjective) {
                        selObj = objectives.get(i);
                    }
                }
                double dist = distFrom(latitude, longitude, selObj.getLatitude(), selObj.getLongitude());
                Toast.makeText(getApplicationContext(), "dist: " + dist, Toast.LENGTH_LONG).show();
                if (dist < 0.005) {
                    Toast.makeText(getApplicationContext(), "You found " + selectedObjective + "!", Toast.LENGTH_LONG).show();
                    adapter.remove(selectedObjective);
                    connector.updateScore(user, selectedObjective);

                } else {
                    Toast.makeText(getApplicationContext(), "You're not close enough to " + selectedObjective + "!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        connector.getObjectives();
    }

    public void setLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static double distFrom(double userLat, double userLong, double objLat, double objLong) {
        /*
        Location l1 = new Location("");
        l1.setLatitude(userLat);
        l1.setLongitude(userLong);

        Location l2 = new Location("");
        l2.setLatitude(objLat);
        l2.setLongitude(objLong);

        return l1.distanceTo(l2);
        */

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
        if (response != null) {
            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (response.isSuccess()) {
                        score += selObj.getCurrentPoints();
                    } else {
                        Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void handleObjectiveListResult(final List<Objective> objectivesList) {
        if (objectivesList != null && !objectivesList.isEmpty()) {
            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    objectives = objectivesList;
                    Toast.makeText(getApplicationContext(), "Objectives Received", Toast.LENGTH_LONG).show();
                    adapter.clear();
                    for (int i = 0; i < objectives.size(); i++) {
                        Log.e("test", objectives.get(i).getName());
                        adapter.add(objectives.get(i).getName());
                    }
                }
            });
        }
    }

    @Override
    public void handleLeaderBoardListResult(List<LeaderboardEntry> leaderboards) {

    }

    @Override
    public void handleScoreUpdateResult(ScoreResponse response) {
        if (response != null) {
            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (response.getResult()) {
                        score = response.getDailyScore();
                        Toast.makeText(getApplicationContext(), "You earned " + response.getPointesEarned() + " points!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), response.getMessage().replace("User", "You"), Toast.LENGTH_LONG).show();
                    }

                    setTitle("Score: " + response.getDailyScore());
                }
            });
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.to_leaderboard:
                Intent intent = new Intent(  this, LeaderboardActivity.class);
                intent.putExtra("username", user);
                startActivity(intent);
                break;
        }

        return true;
    }
}
