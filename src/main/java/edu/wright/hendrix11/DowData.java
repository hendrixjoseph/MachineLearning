package edu.wright.hendrix11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 10/21/2016.
 */
public class DowData {
    private List<Integer> quarter = new ArrayList<>();
    private List<String> stock = new ArrayList<>();
    private List<Integer> dayOfYear = new ArrayList<>();
    private List<Integer> open = new ArrayList<>();
    private List<Integer> high = new ArrayList<>();
    private List<Integer> low = new ArrayList<>();
    private List<Integer> close = new ArrayList<>();
    private List<Integer> volume = new ArrayList<>();
    private List<Double> percentChangePrice = new ArrayList<>();
    private List<Double> percentChangeVolume = new ArrayList<>();
    private List<Integer> nextWeekOpen = new ArrayList<>();
    private List<Integer> nextWeekClose = new ArrayList<>();
    private List<Double> percentChangeNextWeekPrice = new ArrayList<>();
    private List<Integer> daysToNextDividend = new ArrayList<>();
    private List<Double> percentReturnNextDividend = new ArrayList<>();

    public DowData(String filename) throws IOException {
        System.out.println(Paths.get(filename).toAbsolutePath());

        List<String> lines = Files.readAllLines(Paths.get(filename));

        for(String line : lines) {
            if(!line.startsWith("quarter")) {
                String items[] = line.split(",");

                quarter.add(Integer.parseInt(items[0]));
            }
        }
    }

}
