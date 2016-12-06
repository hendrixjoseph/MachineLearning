package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joe Hendrix
 */
public class StockMachine {

    private Map<Stock, Machine> machines = new HashMap<>();

    public StockMachine(List<Stock> stocks, int numWeeks) {

        for (Stock stock : stocks) {
            List<Double> outputs = new ArrayList<>();
            List<Double> inputs = new ArrayList<>();

            for (int i = 0; i < numWeeks; i++) {
                StockData data = stock.getData().get(i);
                inputs.add(data.getDayOfYear().doubleValue());
                outputs.add(data.getClose().doubleValue());
            }

            machines.put(stock, new LinearMachine(outputs, inputs));
        }
    }

    public void runMachines(double learningRate) {
        machines.values().forEach(machine -> machine.learn(learningRate));
    }

    public int maxGeneration() {
        return machines.values().stream().max(Comparator.comparingInt(Machine::getGeneration)).get().getGeneration();
    }

    public int minGeneration() {
        return machines.values().stream().min(Comparator.comparingInt(Machine::getGeneration)).get().getGeneration();
    }

    public void normalizeInputs() {
        machines.values().forEach(Machine::normalizeInputs);
    }

    public void normalizeOutputs() {
        machines.values().forEach(Machine::normalizeOutputs);
    }

    public Map<Stock, Integer> getResults() {
        Map<Stock, Integer> results = new HashMap<>();

        for (Map.Entry<Stock, Machine> entry : machines.entrySet()) {
            int yearCloseGuess = (int) entry.getValue().hypothesis(175.0);
            results.put(entry.getKey(), yearCloseGuess);
        }

        return results;
    }
}