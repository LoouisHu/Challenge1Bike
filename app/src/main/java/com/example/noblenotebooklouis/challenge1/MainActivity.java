package com.example.noblenotebooklouis.challenge1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.*;
import java.io.FileWriter;


public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private float linearAcceleration;
    private float gravity;
    private boolean running;
    private TextView yText;
    private TextView viewData;
    private Button record;
    private Sensor sensorAccelerometer;
    private Sensor sensorGravity;
    private SensorManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        running = false;

        gravity = (float) 9.81;

        // Create SensorManager
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        sensorAccelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGravity = manager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        // Register sensor listener
        manager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL);

        // Assign TextView
        yText = (TextView)findViewById(R.id.yAxis);
        viewData = (TextView)findViewById(R.id.viewData);
        record = (Button)findViewById(R.id.button);
        record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(running == false) {
                    running = true;
                    record.setText("Recording...");
                } else {
                    running = false;
                    record.setText("Record");

                }
            }
        });
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
            appendLog(Long.toString(event.timestamp) + ", " + Float.toString(event.values[1]));
//            readData();
            yText.setText("Y: " + linearAcceleration);
        }

    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void appendLog(String text) {

        FileOutputStream outputStream;

        File logFile = new File(getFilesDir(), "data.txt");
        if(!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            outputStream = openFileOutput("data.txt", Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }


    }

//    private void readData() {
//        String message;
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream = openFileInput("data.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        StringBuffer stringBuffer = new StringBuffer();
//        try {
//            while ((message = bufferedReader.readLine()) != null) {
//                stringBuffer.append(message + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        viewData.setText(stringBuffer.toString());
//
//
//    }
}
