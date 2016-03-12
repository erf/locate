package com.apptakk.locate;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by erlend on 05/12/15.
 */
public class Locate {
    private final LocationManager locationManager;
    private final LocateListener locateListener;
    private final String provider = "fused";
    private Handler locationHandler;

    public Locate(Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.locateListener = new LocateListener();
    }

    public void request(Handler locationHandler) {
        this.locationHandler = locationHandler;
        locationManager.requestLocationUpdates(provider, 0, 0, locateListener);
    }

    public Location lastKnown() {
        return locationManager.getLastKnownLocation(this.provider);
    }

    public boolean locationServicesEnabled(){

        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        return gps_enabled || network_enabled;
    }

    public interface Handler {
        void found(Location location);
    }

    private class LocateListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            locationManager.removeUpdates(this);
            locationHandler.found(location);
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
}
