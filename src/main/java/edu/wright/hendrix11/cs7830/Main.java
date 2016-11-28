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

<<<<<<< HEAD
        System.out.println("index\t" + data.getIndex());

        data.getStocks().forEach(stock -> {System.out.println(stock.getSymbol() + "\t" + stock.getPercentIncrease());});
=======
        System.out.println(data.getIndex());

        Stock stock = new Stock("dfsdfs");

        test(stock, StockData::getDayOfYear);
>>>>>>> e59a30a6b26487dfadece4ce8a3a425bc29fb8ab
    }

    private static void test(Stock stock, Function<StockData, Integer> getDayOfYear) {
        getDayOfYear.apply(stock.getData().get(0));
    }

}
