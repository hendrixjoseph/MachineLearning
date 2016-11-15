package edu.wright.hendrix11.cs7830.machine;

import java.util.List;

/**
 * @author Joe Hendrix
 */
public class QuadraticMachine extends Machine {

    public QuadraticMachine(List<Double> inputs, List<Double> outputs) {
        super(inputs, outputs);
    }

    @Override
    public double hypothesis(double data) {
        double result = getTheta0() + getTheta1() * data * data;

        assert Double.isFinite(result) : "theta0: "+ getTheta0() + "theta1: "+ getTheta1() + "data: " + data;

        return result;
    }
}
