package com.example.noblenotebooklouis.challenge1;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private MyLocationListener myLocationListener;
    private float linearAcceleration;
    private float gravity;
    private boolean running;
    private String latitude, longitude;
    private TextView yText;
    private TextView locationView;
    private Button record;
    private StringBuilder sb;
    private Sensor sensorAccelerometer;
    private Sensor sensorGravity;
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        running = false;

        gravity = (float) 9.81;

        // Create SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        // Register sensor listener
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationView.setText("Long: " + location.getLongitude() + "\n" +
                                     "Lat: " + location.getLatitude());
                latitude = Double.toString(location.getLatitude());
                longitude = Double.toString(location.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        };


        // Assign TextView
        yText = (TextView)findViewById(R.id.yAxis);
        locationView = (TextView) findViewById(R.id.location);
        record = (Button)findViewById(R.id.button);
        record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(running == false) {
                    running = true;
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    sb = new StringBuilder();
                    record.setText("Recording...");
                } else {
                    running = false;
                    locationManager.removeUpdates(locationListener);
                    saveData(sb.toString());
                    record.setText("Record");

                }
            }
        });

        //Optional Google Maps

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate

        final float alpha = (float) 0.8;
        if (running) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_GRAVITY:
                    gravity = event.values[1];
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    gravity = alpha * gravity + (1 - alpha) * event.values[1];
                    linearAcceleration = event.values[1] - gravity;
                    break;
            }
            if (latitude != null && longitude != null) {
                sb.append(System.currentTimeMillis() + ", " +
                        linearAcceleration + ", " +
                        latitude + ", " +
                        longitude + "\n");
                yText.setText("Y: " + linearAcceleration);
            }

        }

    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void saveData(String text) {

//        FileOutputStream outputStream;
//
//        DateFormat currentDateTimeString = DateFormat.getDateTimeInstance();
//        currentDateTimeString.setTimeZone(TimeZone.getTimeZone("gmt"));
//        String filename = currentDateTimeString.format(new Date());
//
//        File logFile = new File(getFilesDir(), filename);
//        System.out.println(getFilesDir().getAbsolutePath());
//        if(!logFile.exists()) {
//            try {
//                logFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            outputStream = openFileOutput("data.txt", Context.MODE_PRIVATE);
//            outputStream.write(text.getBytes());
//            outputStream.close();
//        }  catch (IOException e) {
//            e.printStackTrace();
//        }

        String state;
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath()+"/MyChallenge1");
            if (!dir.exists()) {
                dir.mkdir();
            }
            DateFormat currentDateTimeString = DateFormat.getDateTimeInstance();
            currentDateTimeString.setTimeZone(TimeZone.getTimeZone("gmt"));
            String filename = currentDateTimeString.format(new Date());

            File file = new File(dir, filename + ".txt");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(text.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getApplicationContext(), "SD Card not found", Toast.LENGTH_SHORT).show();
        }


    }

}
