package com.example.noblenotebooklouis.challenge1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 6-9-2017.
 */

public class Anomaly {

    /**
     *
     */
    private static double normal = 3;

    /**
     * Calculates the mean of a buffer
     *
     * @param buffer    The buffer of which the mean needs to be calculated
     * @return          The mean of this buffer
     */
    public static double mean (double[] buffer) {
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
    public static double variance (double[] buffer) {
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
    public static boolean anomaly (double mean, List<double[]> buffers) {
        boolean anomaly = true;
        for (int i = 0; i < buffers.size(); i++) {
            double variance = variance(buffers.get(i));
            if (variance > mean-normal && variance < mean+normal) {
                anomaly = false;
            }
        }
        return anomaly;
    }

    /**
     * Checks for anomalies in a file containing
     *
     * @param file         The file that needs to be checked
     * @return                  A list of all data in the file that contain anomalies
     */
    public static List<Data> getAnomalies(File file) {
        List<Data> datas = ReadFile.readFile(file);
        List<Data> results = new ArrayList<Data>();
        if (!datas.isEmpty()) {
            double[] data = new double[datas.size()];
            double mean = mean(data);
            for (int i = 0; i < datas.size(); i++) {
                data[i] = datas.get(i).getAccelerometer();
            }
            double[] filteredData = Filter.filtering(data);
            Buffers buffers = new Buffers(filteredData);
            for (int j = 0; j < filteredData.length; j++) {
                List<double[]> checkBuffers = new ArrayList();
                for (int k = 0; k < buffers.getSize(); k++) {
                    for (double value : buffers.getBuffer(k)) {
                        if (filteredData[j] == value) {
                            checkBuffers.add(buffers.getBuffer(k));
                        }
                    }
                }
                if (anomaly(mean, checkBuffers)) {
                    results.add(datas.get(j));
                }
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
