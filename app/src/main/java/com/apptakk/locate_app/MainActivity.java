package com.apptakk.locate_app;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.apptakk.locate.Locate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Locate(this).request(new Locate.Handler() {
            @Override
            public void found(Location location) {
                Log.d("location", location.toString());
            }
        });
    }
}
