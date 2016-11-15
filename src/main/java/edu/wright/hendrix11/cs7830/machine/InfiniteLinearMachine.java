package edu.wright.hendrix11.cs7830.machine;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public class InfiniteLinearMachine {

    private List<Double> thetas;
    private List<Double> outputs;
    private List<Double>[] inputs;
    private double[] outputNormalization;
    private List<double[]> inputNormalization = new ArrayList<>();
    private int generation = 0;
    private List<Double> costOverTime = new ArrayList<>();

    public InfiniteLinearMachine(List<Double> outputs, List<Double>... inputs) {
        this.outputs = outputs;
        this.inputs = inputs;
        thetas = new ArrayList<>();//createList(inputs.length + 1, 1.0);

        for(int i = 0; i < inputs.length + 1; i++) {
            if(thetas.isEmpty()) {
                thetas.add(1.1);
            } else {
                thetas.add(thetas.get(i - 1) - 0.2);
            }
        }

        resetNormalization();
    }

    private List<Double> createList(int size, double values) {
        List<Double> newList = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            newList.add(values);
        }

        return newList;
    }

    public List<Double> getCostOverTime() {
        return costOverTime;
    }

    public int getGeneration() {
        return generation;
    }

    public double hypothesis(List<Double> data) {
        double result = thetas.get(thetas.size() - 1);

        for(int i = 0; i < data.size(); i++) {
            result += thetas.get(i) * data.get(i);
        }

        assert Double.isFinite(result) : "result: " + result + " data: " + data;

        return result;
    }

    public void generateNormalization() {
        outputNormalization = generateNormalization(outputs);

        inputNormalization = new ArrayList<>();

        for(List<Double> input : inputs) {
            inputNormalization.add(generateNormalization(input));
        }
    }

    public void resetNormalization() {
        outputNormalization = new double[]{0, 1};

        for(int i = 0; i < inputs.length; i++) {
            inputNormalization.add(new double[]{0, 1});
        }
    }

    private double[] generateNormalization(List<Double> vector) {
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

    private double normalize(double value, double[] normal) {
        double normalized = (value - normal[0]) / normal[1];

        assert Double.isFinite(normalized);

        return normalized;
    }

    public void learn(double learningRate) {
        if(learningRate <= 0 || learningRate > 1) {
            throw new InvalidParameterException("Learning rate must be from 0 exclusive to 1 inclusive!");
        }

        double moddedLearningRate = learningRate / outputs.size();
        assert Double.isFinite(moddedLearningRate);

        costOverTime = new ArrayList<>();

        for(generation = 0; generation < 1_000_000; generation++) {
            List<Double> deltaThetas = createList(thetas.size(), 0.0);

            double costSum = 0;

            for(int i = 0; i < outputs.size(); i++) {
                List<Double> data = new ArrayList<>();

                for(int j = 0; j < inputs.length; j++) {
                    List<Double> input = inputs[j];
                    double[] iNormal = inputNormalization.get(j);

                    data.add(normalize(input.get(i), iNormal));
                }

                double desired = normalize(outputs.get(i), outputNormalization);
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

            costOverTime.add(costSum);

            for(int j = 0; j < thetas.size(); j++) {
                double theta = thetas.get(j) - moddedLearningRate * deltaThetas.get(j);
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

    private double cost(List<Double> data, double desired) {
        double cost = hypothesis(data) - desired;
        assert Double.isFinite(cost) : "Data: " + data + " Desired: " + desired;
        return cost;
    }

    public double answerDenormalized(List<Double> data) {
        List<Double> normalizedData = new ArrayList<>();

        for(int j = 0; j < data.size(); j++) {
            double[] iNormal = inputNormalization.get(j);
            normalizedData.add(normalize(data.get(j), iNormal));
        }

        double answer = hypothesis(normalizedData);

        assert Double.isFinite(answer);

        answer = answer * outputNormalization[1] + outputNormalization[0];

        assert Double.isFinite(answer);

        return answer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of generations: ").append(generation).append("\n");

        sb.append("Input\tDesired\tGuess\n");

        for(int i = 0; i < Math.min(outputs.size(), 20); i++) {
            List<Double> input = new ArrayList<>();

            for(List<Double> inputlist : inputs) {
                input.add(inputlist.get(i));
            }

            double output = outputs.get(i);
            double guess = answerDenormalized(input);

            sb.append(input).append("\t").append(output).append("\t").append(guess).append("\n");
        }

        return sb.toString();
    }

}
