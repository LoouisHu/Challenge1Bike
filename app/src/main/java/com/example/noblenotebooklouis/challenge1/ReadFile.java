package com.example.noblenotebooklouis.challenge1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 7-9-2017.
 */

public class ReadFile {

    /**
     * reads a file and puts the data in it in a map
     *
     * @param path      The path to the file that needs to be read
     * @return          The map containing the data in the file
     */
    public static List<Data> readFile(String path) {
        List<Data> datas = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            while (reader.readLine() != null) {
                String line = reader.readLine();
                String[] values = line.split(",");
                Data data = new Data(Double.parseDouble(values[0]), Double.parseDouble(values[1]),
                        Double.parseDouble(values[2]), Double.parseDouble(values[3]));
                datas.add(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;

    }
}
