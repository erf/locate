package com.apptakk.locate_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apptakk.locate.GoogleLocate;
import com.apptakk.locate.Locate;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestLocations();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestLocations();
                    }
                });
    }

    void requestLocations(){
        GoogleLocate locate = new GoogleLocate(this);

        final TextView textView = (TextView)findViewById(R.id.hello);
        final String[] locationString = {""};
        /*
        if(lastLocation != null){
            locationString[0] = "last known\n" + lastLocation.toString() + "\n";
            textView.setText(locationString[0]);
        }
        */

        /*
        if(!locate.locationServicesEnabled()){
            showlocationServicesNotEnabledAlert();
            return;
        }
        */

        locate.request(new GoogleLocate.Handler() {
            @Override
            public void found(Location location) {
                locationString[0] += "\ncurrent\n" + location.toString() + "\n";
                textView.setText(locationString[0]);
            }
        });

    }

    private void showlocationServicesNotEnabledAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("GPS and network is not enabled");
        dialog.setPositiveButton("Enable GPS or Network in Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });
        dialog.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.show();
    }

}
