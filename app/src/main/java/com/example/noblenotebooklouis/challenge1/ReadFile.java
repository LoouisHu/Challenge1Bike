package com.example.noblenotebooklouis.challenge1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

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
    public Map readFile (String path) {
        Map data = new HashMap();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            while (reader.readLine() != null) {
                String line = reader.readLine();
                String[] values = line.split(",");
                data.put(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }
}
