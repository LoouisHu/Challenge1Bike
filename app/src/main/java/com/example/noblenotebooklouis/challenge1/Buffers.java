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
    List<List<Double>> buffers = new ArrayList();

    /**
     * Creates a buffer around the data
     *
     * @param size      The size of each buffer
     * @param data      The data that needs to be
     *                  put in these buffers
     */
    public Buffers(int size, double[] data) {
        for (int i = 0; i < data.length - size; i++) {
            List<Double> buffer = new ArrayList();
            for (int j = 0; j < size; j++) {
                buffer.add(data[i+j]);
            }
            buffers.add(buffer);
        }
    }

    /**
     *
     * @return          The size of each buffer
     */
    public int getSize() {
        return buffers.size();
    }

    /**
     *
     * @param i         The buffer that needs to be found
     * @return          The buffer that was requested
     */
    public List getBuffer(int i) {
        return buffers.get(i);
    }
}
