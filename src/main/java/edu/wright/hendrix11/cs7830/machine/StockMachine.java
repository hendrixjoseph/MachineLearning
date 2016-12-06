package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.Stock;

import java.util.Map;

/**
 * @author Joe Hendrix
 */
public interface StockMachine {
    void runMachines(double learningRate);

    Map<Stock, Integer> getResults();

    int getGenerations();

    void normalizeInputs();

    void normalizeOutputs();
}
