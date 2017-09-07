package com.example.noblenotebooklouis.challenge1;

/**
 * Created by Thomas on 7-9-2017.
 */

public class Filter {

    /**
     * The length the filter uses to calculate the moving average
     */
    private int length;

    /**
     * Creates a new Filter which calculates moving averages
     *
     * @param length    The length this filter uses to
     *                  calculate the moving average
     */
    public Filter(int length) {
        this.length = length;

    }

    /**
     * Filters the data based on the moving average
     *
     * @param data      The data that needs to be filtered
     * @return          The filtered data
     */
    public double[] filtering(double[] data) {
        double[] filtered = new double[data.length];
        for (int i = 0; i < data.length; i++) {

            double value = 0;
            if (i < length) {
                value = data[0];
                for (int j = 0; j < i; j++) {
                    value = value + data[j+1];
                }
                value = value/(i+1);
            } else {
                for (int j = i - length; j < i; j++) {
                    value = value + data[j+1];
                }
                value = value / length;
            }

            filtered[i] = value;
        }

        return filtered;

    }


}
