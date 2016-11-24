package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import edu.wright.hendrix11.cs7830.tools.ArrayTools;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Joe Hendrix
 */
public class LinearStockMachine {

    private List<Double>[] inputs;
    private double output;

    private List<Double> thetas;

    public LinearStockMachine(Stock stock, double outData, Function<StockData, ? extends Number>... inData) {
        output = outData;

        inputs = new List[inData.length];

        for(int i = 0; i < inData.length; i++) {
            inputs[i] = new ArrayList<>();

            for(StockData sd : stock.getData()) {
                inputs[i].add(inData[i].apply(sd).doubleValue());
            }
        }

        thetas = new ArrayList<>();

        for(int i = 0; i < inData.length + 1; i++) {
            if(thetas.isEmpty()) {
                thetas.add(1.1);
            } else {
                thetas.add(thetas.get(i - 1) - 0.2);
            }
        }
    }

    public double hypothesis(List<Double> data) {
        double result = thetas.get(thetas.size() - 1);

        for(int i = 0; i < data.size(); i++) {
            result += thetas.get(i) * data.get(i);
        }

        assert Double.isFinite(result) : "result: " + result + " data: " + data;

        return result;
    }

    private double cost(List<Double> data, double desired) {
        double cost = hypothesis(data) - desired;
        assert Double.isFinite(cost) : "Data: " + data + " Desired: " + desired;
        return cost;
    }

    public void learn(double learningRate) {
        if(learningRate <= 0 || learningRate > 1) {
            throw new InvalidParameterException("Learning rate must be from 0 exclusive to 1 inclusive!");
        }

        for(int generation = 0; generation < 1_000_000; generation++) {
            List<Double> deltaThetas = ArrayTools.createList(thetas.size(), 0.0);

            double costSum = 0;

            for(int i = 0; i < inputs.length; i++) {
                List<Double> data = new ArrayList<>();

                double desired = output;
                double cost = cost(data, desired);
                costSum += cost;

                for(int j = 0; j < deltaThetas.size(); j++) {
                    double deltaTheta = deltaThetas.get(j);

                    if(j < data.size()) {
                        deltaTheta += cost * data.get(j);
                    } else {
                        deltaTheta += cost;
                    }

                    deltaThetas.set(j, deltaTheta);

                    assert Double.isFinite(deltaTheta) : deltaTheta;
                }
            }

            for(int j = 0; j < thetas.size(); j++) {
                double theta = thetas.get(j) - learningRate * deltaThetas.get(j);
                thetas.set(j, theta);
                assert Double.isFinite(theta) : "theta: " + theta + " deltaTheta: " + deltaThetas.get(j);
            }

            if(costSum < 0.0001 || isDeltaSmall(deltaThetas)) {
                break;
            }
        }
    }

    private boolean isDeltaSmall(List<Double> deltaThetas) {
        for(double deltaTheta : deltaThetas) {
            if(Math.abs(deltaTheta) > 0.0001) {
                return false;
            }
        }

        return true;
    }
}
