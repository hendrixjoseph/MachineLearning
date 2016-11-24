package edu.wright.hendrix11.cs7830;

import java.io.IOException;
import java.text.ParseException;
import java.util.function.Function;

/**
 * @author Joe Hendrix
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        DowData data = new DowData("dow_jones_index.csv");

        System.out.println(data.getIndex());

        Stock stock = new Stock("dfsdfs");

        test(stock, StockData::getDayOfYear);
    }

    private static void test(Stock stock, Function<StockData, Integer> getDayOfYear) {
        getDayOfYear.apply(stock.getData().get(0));
    }

}
