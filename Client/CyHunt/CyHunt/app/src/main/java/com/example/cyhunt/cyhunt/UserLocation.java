package com.example.cyhunt.cyhunt;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class UserLocation implements LocationListener {

    public Double latitude;
    public Double longitude;

    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
