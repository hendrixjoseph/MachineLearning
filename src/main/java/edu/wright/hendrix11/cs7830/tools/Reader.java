package edu.wright.hendrix11.cs7830.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public class Reader {
    public static List<List<Double>> getData(String filename, int[] columns) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<List<Double>> data = new ArrayList<>();

        for (int i = 0; i < columns.length; i++) {
            data.add(new ArrayList<>());
        }

        for (String line : lines) {
            if (!line.startsWith("ID")) {
                String items[] = line.split(",");

                for (int i = 0; i < columns.length; i++) {
                    int column = columns[i];
                    double datum = Double.parseDouble(items[column]);
                    data.get(i).add(datum);
                }
            }
        }

        return data;
    }
}
