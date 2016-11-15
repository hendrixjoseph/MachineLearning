package edu.wright.hendrix11.cs7830.machine;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public abstract class Machine {
    protected double theta0 = 0.9;
    protected double theta1 = 1.1;
    protected List<Double> outputs;
    protected List<Double> inputs;
    protected double[] outputNormalization = {0, 1};
    protected double[] inputNormalization = {0, 1};
    protected int generation = 0;
    protected List<Double> costOverTime = new ArrayList<>();

    public Machine(List<Double> inputs, List<Double> outputs) {
        if(inputs.isEmpty() || outputs.isEmpty() || inputs.size() != outputs.size()) {
            StringBuilder sb = new StringBuilder("inputs.isEmpty() == ");
            sb.append(inputs.isEmpty()).append("; outputs.isEmpty() == ");
            sb.append(outputs.isEmpty()).append("; inputs.size() == ");
            sb.append(inputs.size()).append("; outputs.size() == ");
            sb.append(outputs.size());

            throw new InvalidParameterException(sb.toString());
        }

        this.inputs = inputs;
        this.outputs = outputs;
    }

    public int getGeneration() {
        return generation;
    }

    public double getTheta0() {
        return theta0;
    }

    public double getTheta1() {
        return theta1;
    }

    public abstract double hypothesis(double data);

    public double answerDenormalized(double data) {
        double normalizedData = normalize(data, inputNormalization);
        double answer = hypothesis(normalizedData);

        assert Double.isFinite(normalizedData) && Double.isFinite(answer);

        answer = answer * outputNormalization[1] + outputNormalization[0];

        assert Double.isFinite(answer);

        return answer;
    }

    public List<Double> getCostOverTime() {
        return costOverTime;
    }

    public double getCurrentAverageCost() {
        double costSum = 0.0;

        for(int i = 0; i < inputs.size(); i++) {
            double input = inputs.get(0);
            double output = outputs.get(0);

            costSum += cost(input, output);
        }

        return costSum / inputs.size();
    }

    private double cost(double data, double desired) {
        double cost = hypothesis(data) - desired;
        assert Double.isFinite(cost) : "Data: " + data + " Desired: " + desired;
        return cost;
    }

    public void generateNormalization() {
        inputNormalization = generateNormalization(inputs);
        outputNormalization = generateNormalization(outputs);
    }

    public void resetNormalization() {
        inputNormalization = new double[]{0, 1};
        outputNormalization = new double[]{0, 1};
    }

    protected double[] generateNormalization(List<Double> vector) {
        double sum = 0;
        double min = vector.get(0);
        double max = vector.get(0);

        for(double v : vector) {
            sum += v;
            min = Math.min(v, min);
            max = Math.max(v, max);
        }

        double[] returnValue = {sum / vector.size(), max - min};

        assert Double.isFinite(returnValue[0]) && Double.isFinite(returnValue[1]) : returnValue[0] + " " + returnValue[1];

        return returnValue;
    }

    protected double normalize(double value, double[] normal) {
        double normalized = (value - normal[0]) / normal[1];

        assert Double.isFinite(normalized);

        return normalized;
    }

    public void learn(double learningRate) {
        learn(learningRate, 1.0);
    }

    public void learn(double learningRate, double ratio) {
        if(learningRate <= 0 || learningRate > 1) {
            throw new InvalidParameterException("Learning rate must be from 0 exclusive to 1 inclusive!");
        }

        if(ratio <= 0 || ratio > 1) {
            throw new InvalidParameterException("Learning rate must be from 0 exclusive to 1 inclusive!");
        }

        double moddedLearningRate = learningRate / (inputs.size() * ratio);
        assert Double.isFinite(moddedLearningRate);

        costOverTime = new ArrayList<>();

        for(generation = 0; generation < 10_000_000; generation++) {
            double deltaTheta0 = 0;
            double deltaTheta1 = 0;

            double costSum = 0;

            for(int j = 0; j < inputs.size() * ratio; j++) {
                double data = normalize(inputs.get(j), inputNormalization);
                double desired = normalize(outputs.get(j), outputNormalization);
                double cost = cost(data, desired);
                costSum += cost;

                deltaTheta0 += cost;
                deltaTheta1 += cost * data;

                assert Double.isFinite(deltaTheta0) && Double.isFinite(deltaTheta1) : "cost: " + cost + " data: " + data;
            }

            costOverTime.add(costSum);

            theta0 = theta0 - moddedLearningRate * deltaTheta0;
            theta1 = theta1 - moddedLearningRate * deltaTheta1;

            assert Double.isFinite(theta0) && Double.isFinite(theta1);

            if(Math.abs(deltaTheta0) < 0.0001 && Math.abs(deltaTheta1) < 0.0001) {
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of generations: ").append(generation).append("\n");
        sb.append("theta0: ").append(theta0).append("\ttheta1: ").append(theta1).append("\n");
        sb.append("input normalization: ").append(inputNormalization[0]).append(" ").append(inputNormalization[1]).append("\n");
        sb.append("output normalization: ").append(outputNormalization[0]).append(" ").append(outputNormalization[1]).append("\n");

        sb.append("Input\tDesired\tGuess\n");

        for(int i = 0; i < Math.min(inputs.size(), 20); i++) {
            double input = inputs.get(i);
            double output = outputs.get(i);
            double guess = answerDenormalized(input);

            sb.append(input).append("\t").append(output).append("\t").append(guess).append("\n");
        }

        return sb.toString();
    }
}
