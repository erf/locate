package com.apptakk.locate;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by erlend on 05/12/15.
 */
public class Locate {

    private final LocationManager locationManager; // TODO make static ?
    private final LocateListener locateListener;
    private final String provider = "fused";
    private final Context context;
    private Handler locationHandler;

    public Locate(Context context) {
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.locateListener = new LocateListener();
    }

    public void request(Handler locationHandler) {
        this.locationHandler = locationHandler;
        checkPermission();
        locationManager.requestLocationUpdates(provider, 0, 0, locateListener);
    }

    public Location lastKnown() {
        checkPermission();
        return locationManager.getLastKnownLocation(this.provider);
    }

    // TODO should Locate handle this ..?
    public void checkPermission(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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

        return gps_enabled && network_enabled;
    }

    public interface Handler {
        void found(Location location);
    }

    private class LocateListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            checkPermission();
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
