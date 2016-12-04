package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Joe Hendrix
 */
public class StockMachineFactory {

    private StockMachineFactory() {

    }

    public static LinearMachine getLinearStockMachine(List<Stock> stocks, Function<StockData, ? extends Number>... inData) {
        List<Double>[] inputs = processInputs(stocks, inData).toArray(new List[0]);

        List<Double> outputs = processOutputs(stocks, inputs.length);

        return new LinearMachine(outputs, inputs);
    }

    private static List<Double> processOutputs(List<Stock> stocks, int inputLength) {
        List<Double> outputs = new ArrayList<>();

        for(int i = 0; i < stocks.size(); i++) {
            double increase = stocks.get(i).getPercentIncrease();

            for(int j = 0; j < inputLength; j++) {
                outputs.add(increase);
            }
        }

        return outputs;
    }

    private static List<List<Double>> processInputs(List<Stock> stocks, Function<StockData, ? extends Number>[] inData) {
        List<List<Double>> inputs = new ArrayList<>();

        for(Stock stock : stocks) {
            List<List<Double>> input = processInputs(stock, inData);

            inputs.addAll(input);
        }

        return inputs;
    }

    private static List<List<Double>> processInputs(Stock stock, Function<StockData, ? extends Number>[] inData) {
        List<List<Double>> inputs = new ArrayList<>();

        for (int i = 0; i < inData.length; i++) {
            List<Double> input = new ArrayList<>();

            for (StockData sd : stock.getData()) {
                input.add(inData[i].apply(sd).doubleValue());
            }

            inputs.add(input);
        }

        return inputs;
    }
}