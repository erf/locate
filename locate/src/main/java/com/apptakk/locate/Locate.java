package com.apptakk.locate;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by erlend on 05/12/15.
 */
public class Locate {
    private final LocationManager locationManager;
    private final LocateListener locateListener;
    private Handler locationHandler;
    private String provider;

    public Locate(Context context) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.locateListener = new LocateListener();

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);
    }

    public void request(Handler locationHandler) {
        this.locationHandler = locationHandler;

        if(provider == null)
            return;

        locationManager.requestLocationUpdates(provider, 0, 0, locateListener);
    }

    public Location lastKnown() {
        if (this.provider == null)
            return null;

        return locationManager.getLastKnownLocation(this.provider);
    }

    public boolean locationServicesEnabled(){
        if(provider == null)
            return false;

        try {
            if (locationManager.isProviderEnabled(provider))
                return true;
        } catch (Exception e){
        }

        return false;
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
