package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * @author Joe Hendrix
 */
public class StockMachineTest {

    private static DowData data;

    @BeforeClass
    public static void setUp() throws IOException, ParseException {
        data = new DowData("dow_jones_index.csv");
    }

    @Test
    public void testStockMachine() throws Exception {
        StockMachine machine = new StockMachine(data.getStocks(), 4);

        machine.runMachines(0.01);

        Map<Stock, Integer> results = machine.getResults();

        for(Map.Entry<Stock, Integer> result : results.entrySet()) {
            System.out.println(result.getKey() + "\t" + result.getValue() / 100.0);
        }
    }
}