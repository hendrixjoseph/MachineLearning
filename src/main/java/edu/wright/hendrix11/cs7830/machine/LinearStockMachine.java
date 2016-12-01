package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Joe Hendrix
 */
public class LinearStockMachine extends LinearMachine {
    public LinearStockMachine(Stock stock, double output, Function<StockData, ? extends Number>... inData) {
        super(output, processInputs(stock, inData));
    }

    private static List<Double>[] processInputs(Stock stock, Function<StockData, ? extends Number>[] inData) {
        List<Double>[] inputs = new List[inData.length];

        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = new ArrayList<>();

            for (StockData sd : stock.getData()) {
                inputs[i].add(inData[i].apply(sd).doubleValue());
            }
        }

        return inputs;
    }
}
