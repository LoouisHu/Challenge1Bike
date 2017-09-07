package com.example.noblenotebooklouis.challenge1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 6-9-2017.
 */

public class Anomaly {

    /**
     *
     */
    private double normal;

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
    public double mean (double[] buffer) {
        double total = 0.000000;
        for (double data : buffer) {
            total = total + data;
        }
        return total/buffer.length;
    }

    /**
     * Calculates the variance of a buffer
     *
     * @param buffer    The buffer of which the variance needs to be calculated
     * @return          The variance of this buffer
     */
    public double variance (double[] buffer) {
        double variance = 0.000000;
        double mean = mean(buffer);
        for (double data : buffer) {
            variance = variance + (data - mean)*(data - mean);
        }
        return variance/buffer.length;
    }

    /**
     * Checks whether a value contains an anomaly
     *
     * @param buffers   The buffers that need to be checked for an anomaly
     * @return          True if there is an anomaly, false if there isn't
     */
    public boolean anomaly (List<double[]> buffers) {
        boolean anomaly = true;
        for (int i = 0; i < buffers.size(); i++) {
            if (variance(buffers.get(i)) <= normal) {
                anomaly = false;
            }
        }
        return anomaly;
    }

    /**
     * Checks for anomalies in a file containing
     *
     * @param filename          The file that needs to be checked
     * @param filterLength      The length based on which needs to be filtered
     * @param bufferSize        The size the buffer needs to be
     * @return                  A list of all data in the file that contain anomalies
     */
    public List<Data> getAnomalies(String filename, int filterLength, int bufferSize) {
        List<Data> datas = ReadFile.readFile(filename);
        double[] data = new double[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            data[i] = datas.get(i).getAccelerometer();
        }
        double[] filteredData = new Filter(filterLength).filtering(data);
        Buffers buffers = new Buffers(bufferSize, filteredData);
        List<Data> results = new ArrayList();
        for (int j = 0; j < filteredData.length; j++) {
            List<double[]> checkBuffers = new ArrayList();
            for (int k = 0; k < buffers.getSize(); k++) {
                for (double value : buffers.getBuffer(k)) {
                    if (filteredData[j] == value) {
                        checkBuffers.add(buffers.getBuffer(k));
                    }
                }
            }
            if (anomaly(checkBuffers)) {
                results.add(datas.get(j));
            }
        }
        return results;

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
