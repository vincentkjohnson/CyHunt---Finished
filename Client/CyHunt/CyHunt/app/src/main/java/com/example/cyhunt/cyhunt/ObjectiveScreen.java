package com.example.cyhunt.cyhunt;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class ObjectiveScreen extends AppCompatActivity implements ApiAuthenticationClient.ApiResultHandler, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static ObjectiveScreen parent;
    private final ApiAuthenticationClient connector = new ApiAuthenticationClient((ApiAuthenticationClient.ApiResultHandler) this);
    private ListView listView;
    private List<Objective> objectives = new ArrayList<>();
    private double longitude = -93.64999;
    private double latitude = 42.02595;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective_screen);
        final String username = getIntent().getExtras().toString();
        parent = this;

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

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
                    connector.updateScore(username, selectedObjective);
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
}
