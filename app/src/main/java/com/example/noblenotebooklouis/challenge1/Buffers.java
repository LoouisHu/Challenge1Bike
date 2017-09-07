package com.example.noblenotebooklouis.challenge1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 6-9-2017.
 */

public class Buffers {

    List<List<Double>> buffers = new ArrayList();

    public Buffers(int size, double[] data) {
        for (int i = 0; i < data.length - size; i++) {
            List<Double> buffer = new ArrayList();
            for (int j = 0; j < size; j++) {
                buffer.add(data[i+j]);
            }
            buffers.add(buffer);
        }
    }

    public int getSize() {
        return buffers.size();
    }

    public List getBuffer(int i) {
        return buffers.get(i);
    }
}
