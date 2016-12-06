package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.tools.ArrayTools;
import edu.wright.hendrix11.cs7830.tools.Normalizer;
import edu.wright.hendrix11.cs7830.tools.RandomOrder;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Joe on 11/30/2016.
 */
public abstract class Machine {
    private List<Double> thetas;

    private List<Double>[] inputs;
    private List<Double> outputs;

    private Normalizer outNormalizer = new Normalizer();
    private Normalizer[] inNormalizer;

    private int generation;

    private List<Double> cost = new ArrayList<>();

    public Machine(List<Double> outputs, List<Double>[] inputs) {
        this.outputs = outputs;
        this.inputs = inputs;

        inNormalizer = new Normalizer[inputs.length];

        for (int i = 0; i < inputs.length; i++) {
            inNormalizer[i] = new Normalizer();
        }

        thetas = new ArrayList<>();

        for (int i = 0; i < inputs.length + 1; i++) {
            if (thetas.isEmpty()) {
                thetas.add(1.1);
            } else {
                thetas.add(thetas.get(i - 1) - 0.2);
            }
        }
    }

    public void normalizeOutputs() {
        outNormalizer = new Normalizer(outputs);
    }

    public void normalizeInputs() {
        inNormalizer = new Normalizer[inputs.length];

        for (int i = 0; i < inNormalizer.length; i++) {
            inNormalizer[i] = new Normalizer(inputs[i]);
        }
    }

    public int getGeneration() {
        return generation;
    }

    public void learn(double learningRate) {
        if (learningRate <= 0 || learningRate > 1) {
            throw new InvalidParameterException("Learning rate must be from 0 exclusive to 1 inclusive!");
        }

        for (generation = 0; generation < 100_000; generation++) {
            List<Double> deltaThetas = ArrayTools.createList(thetas.size(), 0.0);

            final double[] costSum = {0};

            RandomOrder order = new RandomOrder(inputs.length);

            order.indexStream().forEach(i -> {
                List<Double> data = new ArrayList<>();

                for (List<Double> input : inputs) {
                    data.add(input.get(i));
                }

                double desired = outputs.get(i);
                double cost = cost(data, desired);
                costSum[0] += cost;

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
            });

            for (int j = 0; j < thetas.size(); j++) {
                double theta = thetas.get(j) - learningRate * deltaThetas.get(j);
                thetas.set(j, theta);
                assert Double.isFinite(theta) : "theta: " + theta + " deltaTheta: " + deltaThetas.get(j);
            }

            cost.add(costSum[0]);

            if (costSum[0] < 0.0001 || isDeltaSmall(deltaThetas)) {
                break;
            }
        }
    }

    public List<Double> getCost() {
        return cost;
    }

    private double cost(List<Double> data, double desired) {
        desired = outNormalizer.normalize(desired);

        double cost = hypothesis(data) - desired;
        assert Double.isFinite(cost) : "Data: " + data + " Desired: " + desired;
        return cost;
    }

    protected abstract double hypothesis(List<Double> data, List<Double> thetas);

    public double hypothesis(List<Double> data) {

        List<Double> normalizedData = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            normalizedData.add(inNormalizer[i].normalize(data.get(i)));
        }

        double output = hypothesis(normalizedData, thetas);


        return outNormalizer.denormalize(output);
    }

    public double hypothesis(Double data) {
        return hypothesis(Arrays.asList(new Double[]{data}));
    }

    private boolean isDeltaSmall(List<Double> deltaThetas) {
        for (double deltaTheta : deltaThetas) {
            if (Math.abs(deltaTheta) > 0.0001) {
                return false;
            }
        }

        return true;
    }
}
