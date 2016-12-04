package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.tools.ArrayTools;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 11/30/2016.
 */
public abstract class Machine {
    private List<Double> thetas;

    private List<Double>[] inputs;
    private List<Double> outputs;

    public Machine(List<Double> outputs, List<Double>[] inputs) {
        this.outputs = outputs;
        this.inputs = inputs;

        thetas = new ArrayList<>();

        for (int i = 0; i < inputs.length + 1; i++) {
            if (thetas.isEmpty()) {
                thetas.add(1.1);
            } else {
                thetas.add(thetas.get(i - 1) - 0.2);
            }
        }
    }

    public void learn(double learningRate) {
        if (learningRate <= 0 || learningRate > 1) {
            throw new InvalidParameterException("Learning rate must be from 0 exclusive to 1 inclusive!");
        }

        for (int generation = 0; generation < 1_000_000; generation++) {
            List<Double> deltaThetas = ArrayTools.createList(thetas.size(), 0.0);

            double costSum = 0;

            for (int i = 0; i < inputs.length; i++) {
                List<Double> data = new ArrayList<>();

                double desired = outputs.get(i);
                double cost = cost(data, desired);
                costSum += cost;

                for (int j = 0; j < deltaThetas.size(); j++) {
                    double deltaTheta = deltaThetas.get(j);

                    if (j < data.size()) {
                        deltaTheta += cost * data.get(j);
                    } else {
                        deltaTheta += cost;
                    }

                    deltaThetas.set(j, deltaTheta);

                    assert Double.isFinite(deltaTheta) : deltaTheta;
                }
            }

            for (int j = 0; j < thetas.size(); j++) {
                double theta = thetas.get(j) - learningRate * deltaThetas.get(j);
                thetas.set(j, theta);
                assert Double.isFinite(theta) : "theta: " + theta + " deltaTheta: " + deltaThetas.get(j);
            }

            if (costSum < 0.0001 || isDeltaSmall(deltaThetas)) {
                break;
            }
        }
    }

    protected double cost(List<Double> data, double desired) {
        double cost = hypothesis(data) - desired;
        assert Double.isFinite(cost) : "Data: " + data + " Desired: " + desired;
        return cost;
    }

    protected abstract double hypothesis(List<Double> data, List<Double> thetas);

    public double hypothesis(List<Double> data) {
        return hypothesis(data, thetas);
    }

    protected boolean isDeltaSmall(List<Double> deltaThetas) {
        for (double deltaTheta : deltaThetas) {
            if (Math.abs(deltaTheta) > 0.0001) {
                return false;
            }
        }

        return true;
    }
}
