package com.example.noblenotebooklouis.challenge1;

import java.util.List;

/**
 * Created by Thomas on 6-9-2017.
 */

public class Anomaly {

    double normal;

    public Anomaly (double normal) {
        this.normal = normal;
    }

    public double mean (List<Double> buffer) {
        double total = 0.000000;
        for (int i = 0; i < buffer.size(); i++) {
            total = total + buffer.get(i);
        }
        return total/buffer.size();
    }

    public double variance (List<Double> buffer) {
        double variance = 0.000000;
        double mean = mean(buffer);
        for (int i = 0; i < buffer.size(); i++) {
            variance = variance + (buffer.get(i) - mean)*(buffer.get(i) - mean);
        }
        return variance/buffer.size();
    }

    public boolean anomaly (Buffers buffers) {
        boolean anomaly = true;
        for (int i = 0; i < buffers.getSize(); i++) {
            if (variance(buffers.getBuffer(i)) <= normal) {
                anomaly = false;
            }
        }
        return anomaly;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

}
