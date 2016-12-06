package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Joe Hendrix
 */
public class StockChangeMachineTest {
    private static DowData data;

    @BeforeClass
    public static void setUp() throws IOException, ParseException {
        data = new DowData("dow_jones_index.csv");
    }

    @Test
    public void test() {
        StockChangeMachine machine = new StockChangeMachine(data.getStocks());
        machine.runMachines(0.0001);

        Map<Stock, Integer> results = machine.getResults();

        for(Map.Entry<Stock, Integer> result : results.entrySet()) {
            System.out.println(result.getKey() + "\t" + result.getValue() / 100.0);
        }
    }
}