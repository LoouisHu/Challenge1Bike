package com.example.noblenotebooklouis.challenge1;

/**
 * Created by Thomas on 7-9-2017.
 */

public class Filter {

    int length;

    public Filter(int length) {
        this.length = length;

    }

    public double[] filtering(double[] data) {
        double[] filtered = new double[data.length];
        for (int i = 0; i < data.length; i++) {

            double value = data[0];
            if (i < length) {
                for (int j = 0; j < i; j++) {
                    value = value + data[j+1];
                }
                value = value/i;
            } else {
                for (int j = i-length; j < i; j++) {
                    value = value + data[j+1];
                }
                value = value/length;
            }

            filtered[i] = value;
        }

        return filtered;

    }


}
