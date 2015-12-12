package com.apptakk.locate_app;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        Locate locate = new Locate(this);
        Location lastLocation = locate.lastKnown();

        final TextView textView = (TextView)findViewById(R.id.hello);
        final String[] locationString = {""};
        if(lastLocation != null){
            locationString[0] = "last known\n" + lastLocation.toString() + "\n";
            textView.setText(locationString[0]);
        }

        locate.request(new Locate.Handler() {
            @Override
            public void found(Location location) {
                locationString[0] += "\ncurrent\n" + location.toString() + "\n";
                textView.setText(locationString[0]);
            }
        });
    }
}
