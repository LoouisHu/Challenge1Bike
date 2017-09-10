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
    private static double normal = 1.5;

    /**
     * Calculates the mean of a buffer
     *
     * @param buffer    The buffer of which the mean needs to be calculated
     * @return          The mean of this buffer
     */
    public static double mean (double[] buffer) {
        double total = 0;
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
        double variance = 0;
        double mean = mean(buffer);
        for (double data : buffer) {
            variance = variance + ((data - mean)*(data - mean));
        }
        return variance/buffer.length;
    }

    /**
     * Checks whether a value contains an anomaly
     *
     * @param buffers   The buffers that need to be checked for an anomaly
     * @return          True if there is an anomaly, false if there isn't
     */
    public static boolean anomaly (double var, List<double[]> buffers) {
        boolean anomaly = true;
        for (int i = 0; i < buffers.size(); i++) {
            double variance = variance(buffers.get(i));
            if (variance < var + normal) {
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
    public static List<Data> getAnomalies (File file) {
        List<Data> datas = ReadFile.readFile(file);
        List<Data> results = new ArrayList<Data>();
        if (!datas.isEmpty()) {
            double[] data = new double[datas.size()];
            double var = variance(data);
            for (int i = 0; i < datas.size(); i++) {
                data[i] = datas.get(i).getAccelerometer();
            }
            double[] filteredData = Filter.filtering(data);
            Buffers buffers = new Buffers(filteredData);
            for (int j = 0; j < filteredData.length; j++) {
                List<double[]> checkBuffers = new ArrayList<double[]>();
                for (int k = 0; k < buffers.getSize(); k++) {
                    double[] buffer = buffers.getBuffer(k);
                    for (int l = 0; l < buffer.length; l++) {
                        if (filteredData[j] == buffer[l]) {
                            checkBuffers.add(buffer);
                        }
                    }
                }

                if (anomaly(var, checkBuffers)) {
                    results.add(datas.get(j));
                }
            }
        }
        return results;

    }

}
