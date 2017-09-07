package com.example.noblenotebooklouis.challenge1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 6-9-2017.
 */

public class Buffers {

    /**
     * A list of buffers, which contain data
     */
    private List<double[]> buffers = new ArrayList();

    /**
     * Creates a buffer around the data
     *
     * @param size      The size of each buffer
     * @param data      The data that needs to be
     *                  put in these buffers
     */
    public Buffers(int size, double[] data) {
        for (int i = 0; i < data.length - size; i++) {
            double[] bufferData = new double[size];
            for (int j = 0; j < size; j++) {
                bufferData[j] = data[i + j];
            }
        }
    }

    public int getSize() {
        return buffers.size();
    }

    public double[] getBuffer(int i) {
        return buffers.get(i);
    }
}

