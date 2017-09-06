package com.example.noblenotebooklouis.challenge1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import static android.R.attr.gravity;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView yText;
    private Sensor sensor;
    private SensorManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create SensorManager
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        sensor = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // Register sensor listener
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        // Assign TextView
        yText = (TextView)findViewById(R.id.yAxis);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
