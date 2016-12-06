package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import edu.wright.hendrix11.cs7830.tools.ArrayTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * @author Joe Hendrix
 */
public class StockChangeMachine implements StockMachine {

    private List<Stock> stocks;
    private Machine machine;

    public StockChangeMachine(List<Stock> stocks) {
        this.stocks = stocks;

        List<Double> percentIncreases = getList(StockData::getPercentChangePrice);
        List<Double> volumes = getList(StockData::getPercentChangeVolume);
        List<Double> closingValues = getList(StockData::getClose);
        List<Double> nextDiv = getList(StockData::getDaysToNextDividend);

        machine = new LinearMachine(percentIncreases, new List[]{volumes, closingValues, nextDiv});
    }

    private List<Double> getList(ToDoubleFunction<StockData> function) {
        List<Double> list = new ArrayList<>();

        for(Stock stock : stocks) {
            list.addAll(getList(stock.getData(), function));
        }

        return list;
    }

    @Override
    public Map<Stock, Integer> getResults() {
        Map<Stock, Integer> results = new HashMap<>();

        for(Stock stock : stocks) {
            Double value = stock.getData().get(0).getClose().doubleValue();


            for(StockData data : stock.getData()) {
                List<Double> input = Arrays.asList(new Double[]{data.getPercentChangeVolume(), value, data.getDaysToNextDividend().doubleValue()});
                double percentChange = machine.hypothesis(input);
                System.out.println(percentChange);
                value *= 1.0 + percentChange / 100.0;
            }

            results.put(stock, value.intValue());
        }

        return results;
    }

    @Override
    public int getGenerations() {
        return machine.getGeneration();
    }

    @Override
    public void normalizeInputs() {
        machine.normalizeInputs();
    }

    @Override
    public void normalizeOutputs() {
        machine.normalizeOutputs();
    }

    @Override
    public void runMachines(double learningRate) {
        machine.learn(learningRate);
    }

    private List<Double> getList(List<StockData> data, ToDoubleFunction<StockData> function) {
        return data.stream().mapToDouble(function).boxed().collect(Collectors.toList());
    }
}
