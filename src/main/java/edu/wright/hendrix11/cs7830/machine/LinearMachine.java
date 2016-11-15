package edu.wright.hendrix11.cs7830.machine;

import java.util.List;

/**
 * @author Joe Hendrix
 */
public class LinearMachine extends Machine {

    public LinearMachine(List<Double> inputs, List<Double> outputs) {
        super(inputs, outputs);
    }

    @Override
    public double hypothesis(double data) {
        double result = getTheta0() + getTheta1() * data;

        return result;
    }
}
