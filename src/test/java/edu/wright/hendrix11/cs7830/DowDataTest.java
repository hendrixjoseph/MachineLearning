package edu.wright.hendrix11.cs7830;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Joe on 12/3/2016.
 */
public class DowDataTest {

    private static DowData data;

    @BeforeClass
    public static void beforeClass() throws IOException, ParseException {
        data = new DowData("dow_jones_index.csv");
    }

    @Test
    public void testGets() {
        System.out.println("First day of year:\t" + data.getFirstDayOfYear());
        System.out.println("Last day of year:\t" + data.getLastDayOfYear());
        System.out.println("Index return:\t" + data.getIndex());
        System.out.println("Best stock:\t" + data.getBestStock().getSymbol() + " at " + data.getBestStock().getPercentIncrease());
        System.out.println("Worst stock:\t" + data.getWorstStock().getSymbol() + " at " + data.getWorstStock().getPercentIncrease());
    }

    @Test
    public void testInvest() {
        for(Stock stock : data.getStocks()) {
            System.out.println("Invest $1000.00 in " + stock.getSymbol() + " get $" + stock.invest(1000_00) / 100.0);
        }
    }
}