package com.example.noblenotebooklouis.challenge1;

import java.util.List;

/**
 * Created by Thomas on 6-9-2017.
 */

public class Anomaly {

    /**
     *
     */
    double normal;

    /**
     * Creates a new anomaly detection, based on the value of normal
     *
     * @param normal    The value that is considered normal, under which
     *                  it is not considered an anomaly
     */
    public Anomaly (double normal) {
        this.normal = normal;
    }

    /**
     * Calculates the mean of a buffer
     *
     * @param buffer    The buffer of which the mean needs to be calculated
     * @return          The mean of this buffer
     */
    public double mean (List<Double> buffer) {
        double total = 0.000000;
        for (int i = 0; i < buffer.size(); i++) {
            total = total + buffer.get(i);
        }
        return total/buffer.size();
    }

    /**
     * Calculates the variance of a buffer
     *
     * @param buffer    The buffer of which the variance needs to be calculated
     * @return          The variance of this buffer
     */
    public double variance (List<Double> buffer) {
        double variance = 0.000000;
        double mean = mean(buffer);
        for (int i = 0; i < buffer.size(); i++) {
            variance = variance + (buffer.get(i) - mean)*(buffer.get(i) - mean);
        }
        return variance/buffer.size();
    }

    /**
     * Checks whether a value contains an anomaly
     *
     * @param buffers   The buffers that need to be checked for an anomaly
     * @return          True if there is an anomaly, false if there isn't
     */
    public boolean anomaly (Buffers buffers) {
        boolean anomaly = true;
        for (int i = 0; i < buffers.getSize(); i++) {
            if (variance(buffers.getBuffer(i)) <= normal) {
                anomaly = false;
            }
        }
        return anomaly;
    }

    /**
     *
     * @return          The normal value that checks for anomalies
     */
    public double getNormal() {
        return normal;
    }

    /**
     *
     * @param normal    The value to which normal should be set
     */
    public void setNormal(double normal) {
        this.normal = normal;
    }

}
