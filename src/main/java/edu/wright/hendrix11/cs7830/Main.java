package edu.wright.hendrix11.cs7830;

import edu.wright.hendrix11.cs7830.machine.LinearStockMachine;

import java.io.IOException;
import java.text.ParseException;
import java.util.function.Function;

/**
 * @author Joe Hendrix
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        DowData data = new DowData("dow_jones_index.csv");

        Stock stock = data.getStocks().iterator().next();

        LinearStockMachine machine = new LinearStockMachine(stock, stock.getPercentIncrease(), StockData::getDayOfYear, StockData::getOpen);

        machine.learn(0.25);

    }

}
