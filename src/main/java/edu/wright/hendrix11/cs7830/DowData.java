package edu.wright.hendrix11.cs7830;

import edu.wright.hendrix11.StringParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
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
    private List<Integer> previousWeekVolume = new ArrayList<>();
    private List<Integer> nextWeekOpen = new ArrayList<>();
    private List<Integer> nextWeekClose = new ArrayList<>();
    private List<Double> percentChangeNextWeekPrice = new ArrayList<>();
    private List<Integer> daysToNextDividend = new ArrayList<>();
    private List<Double> percentReturnNextDividend = new ArrayList<>();

    private List<List> everything = new ArrayList<>();

    public DowData(String filename) throws IOException, ParseException {
        List<String> lines = Files.readAllLines(Paths.get(filename));

        everything.add(quarter);
        everything.add(stock);
        everything.add(dayOfYear);
        everything.add(open);
        everything.add(high);
        everything.add(low);
        everything.add(close);
        everything.add(volume);
        everything.add(percentChangePrice);
        everything.add(percentChangeVolume);
        everything.add(previousWeekVolume);
        everything.add(nextWeekOpen);
        everything.add(nextWeekClose);
        everything.add(percentChangeNextWeekPrice);
        everything.add(daysToNextDividend);
        everything.add(percentReturnNextDividend);

        for(String line : lines) {
            if(!line.startsWith("quarter")) {
                String items[] = line.split(",");

                int i = 0;

                quarter.add(edu.wright.hendrix11.StringParser.parseInt(items[i++]));
                stock.add(items[i++]);
                dayOfYear.add(StringParser.getDayOfYear(items[i++]));
                open.add(StringParser.parseDollars(items[i++]));
                high.add(StringParser.parseDollars(items[i++]));
                low.add(StringParser.parseDollars(items[i++]));
                close.add(StringParser.parseDollars(items[i++]));
                volume.add(StringParser.parseInt(items[i++]));
                percentChangePrice.add(StringParser.parseDouble(items[i++]));
                percentChangeVolume.add(StringParser.parseDouble(items[i++]));
                previousWeekVolume.add(StringParser.parseInt(items[i++]));
                nextWeekOpen.add(StringParser.parseDollars(items[i++]));
                nextWeekClose.add(StringParser.parseDollars(items[i++]));
                percentChangeNextWeekPrice.add(StringParser.parseDouble(items[i++]));
                daysToNextDividend.add(StringParser.parseInt(items[i++]));
                percentReturnNextDividend.add(StringParser.parseDouble(items[i++]));
            }
        }

        assert quarter.size() == lines.size() - 1 && classInvariant();
    }

    private boolean classInvariant() {
        List previous = null;

        for(List current : everything) {
            if(previous != null && previous.size() != current.size()) {
                return false;
            }

            previous = current;
        }

        return true;
    }

}
