package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * @author Joe Hendrix
 */
public class StockMachineFactoryTest {

    private static DowData data;

    @BeforeClass
    public static void setUp() throws IOException, ParseException {
        data = new DowData("dow_jones_index.csv");
    }

    @Test
    public void testGetLinearStockMachine() throws Exception {
        Machine machine = StockMachineFactory.getLinearStockMachine(data.getStocks(), StockData::getDayOfYear, StockData::getClose);

        machine.learn(0.25);


    }

}