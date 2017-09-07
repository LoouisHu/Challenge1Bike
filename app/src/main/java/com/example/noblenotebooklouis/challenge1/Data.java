package com.example.noblenotebooklouis.challenge1;

/**
 * Created by Thomas on 7-9-2017.
 */

public class Data {

    private double time;
    private double accelerometer;
    private double latitude;
    private double longitude;

    public Data(double time, double accelerometer, double latitude, double longitude) {
        this.time = time;
        this.accelerometer = accelerometer;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getAccelerometer() {
        return accelerometer;
    }

    public void setAccelerometer(double accelerometer) {
        this.accelerometer = accelerometer;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
