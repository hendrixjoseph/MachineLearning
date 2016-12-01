package edu.wright.hendrix11.cs7830;

import edu.wright.hendrix11.cs7830.tools.StringParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Joe on 10/21/2016.
 */
public class DowData {
    private Map<String, Stock> stocks = new HashMap<>();

    public DowData(String filename) throws IOException, ParseException {

        List<String> lines = Files.readAllLines(Paths.get(filename));

        for(String line : lines) {
            if(!line.startsWith("quarter")) {
                StockData data = new StockData();
                String symbol;

                String items[] = line.split(",");

                int i = 0;

                data.setQuarter(StringParser.parseInt(items[i++]));

                symbol = items[i++];

                data.setDayOfYear(StringParser.getDayOfYear(items[i++]));
                data.setOpen(StringParser.parseDollars(items[i++]));
                data.setHigh(StringParser.parseDollars(items[i++]));
                data.setLow(StringParser.parseDollars(items[i++]));
                data.setClose(StringParser.parseDollars(items[i++]));
                data.setVolume(StringParser.parseInt(items[i++]));
                data.setPercentChangePrice(StringParser.parseDouble(items[i++]));
                data.setPercentChangeVolume(StringParser.parseDouble(items[i++]));
                data.setPreviousWeekVolume(StringParser.parseInt(items[i++]));
                data.setNextWeekOpen(StringParser.parseDollars(items[i++]));
                data.setNextWeekClose(StringParser.parseDollars(items[i++]));
                data.setPercentChangeNextWeekPrice(StringParser.parseDouble(items[i++]));
                data.setDaysToNextDividend(StringParser.parseInt(items[i++]));
                data.setPercentReturnNextDividend(StringParser.parseDouble(items[i++]));

                Stock stock = stocks.containsKey(symbol) ? stocks.get(symbol) : null;

                if(stock == null) {
                    stock = new Stock(symbol);
                    stocks.put(symbol, stock);
                }

                stock.addData(data);
            }
        }

        stocks.values().forEach(Stock::sortData);
    }

    public List<Stock> getStocks() {
        return new ArrayList<>(stocks.values());
    }

    public int getFirstDayOfYear() {
        return getStocks().get(0).getDayOfYearFor(0);
    }

    public int getLastDayOfYear() {
        Stock example = getStocks().get(0);

        return example.getDayOfYearFor(example.numDataPoints() - 1);
    }

    public int getAverageYearOpen() {
        int sumYearOpens = 0;

        for(Stock stock : stocks.values()) {
            sumYearOpens += stock.getYearOpen();
        }

        return sumYearOpens / stocks.size();
    }

    public int getAverageYearClose() {
        int sumYearCloses = 0;

        for(Stock stock : stocks.values()) {
            sumYearCloses += stock.getYearClose();
        }

        return sumYearCloses / stocks.size();
    }

    public double getIndex() {
        int sumYearOpens = 0;
        int sumYearCloses = 0;

        for(Stock stock : stocks.values()) {
            sumYearOpens += stock.getYearOpen();
            sumYearCloses += stock.getYearClose();
        }

        return (sumYearCloses - sumYearOpens) / (double)sumYearOpens;
    }
}
