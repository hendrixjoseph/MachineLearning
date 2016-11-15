package edu.wright.hendrix11.cs7830.machine;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public class TriValueLinearMachine extends Machine {
    private double theta2 = 0.5;
    private List<Double> inputs2;
    private double[] input2Normalization = {0, 1};

    public TriValueLinearMachine(List<Double> inputs, List<Double> inputs2, List<Double> outputs) {
        super(inputs, outputs);
        this.inputs2 = inputs2;
    }

    @Override
    public double hypothesis(double data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void generateNormalization() {
        super.generateNormalization();
        input2Normalization = generateNormalization(inputs2);
    }

    @Override
    public void resetNormalization() {
        super.resetNormalization();
        input2Normalization = new double[]{0, 1};
    }

    public double hypothesis(double data1, double data2) {
        return theta0 + theta1 * data1 + theta2 * data2;
    }

    private double cost(double data1, double data2, double desired) {
        double cost = hypothesis(data1, data2) - desired;
        assert Double.isFinite(cost);
        return cost;
    }

    @Override
    public void learn(double learningRate) {
        if(learningRate <= 0 || learningRate > 1) {
            throw new InvalidParameterException("Learning rate must be from 0 exclusive to 1 inclusive!");
        }

        double moddedLearningRate = learningRate / inputs.size();
        assert Double.isFinite(moddedLearningRate);

        costOverTime = new ArrayList<>();

        for(generation = 0; generation < 10_000_000; generation++) {
            double deltaTheta0 = 0;
            double deltaTheta1 = 0;
            double deltaTheta2 = 0;

            double costSum = 0;

            for (int j = 0; j < inputs.size(); j++) {
                double data1 = normalize(inputs.get(j), inputNormalization);
                double data2 = normalize(inputs2.get(j), inputNormalization);
                double desired = normalize(outputs.get(j), outputNormalization);
                double cost = cost(data1, data2, desired);
                costSum += cost;

                deltaTheta0 += cost;
                deltaTheta1 += cost * data1;
                deltaTheta2 += cost * data2;

                assert Double.isFinite(deltaTheta0) && Double.isFinite(deltaTheta1) && Double.isFinite(deltaTheta2);
            }

            costOverTime.add(costSum);

            theta0 = theta0 - moddedLearningRate * deltaTheta0;
            theta1 = theta1 - moddedLearningRate * deltaTheta1;
            theta2 = theta2 - moddedLearningRate * deltaTheta2;

            assert Double.isFinite(theta0) && Double.isFinite(theta1) && Double.isFinite(theta2);

            if (deltaTheta0 < 0.0001 && deltaTheta1 < 0.0001 && deltaTheta2 < 0.0001) {
                break;
            }
        }
    }
}
