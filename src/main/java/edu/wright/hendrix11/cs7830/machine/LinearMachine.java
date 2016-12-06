package edu.wright.hendrix11.cs7830.machine;

import java.util.List;

/**
 * Created by Joe on 11/30/2016.
 */
public class LinearMachine extends Machine {

    public LinearMachine(List<Double> outputs, List<Double>[] inputs) {
        super(outputs, inputs);
    }

    public LinearMachine(List<Double> outputs, List<Double> inputs) {
        this(outputs, new List[]{inputs});
    }

    @Override
    protected double hypothesis(List<Double> data, List<Double> thetas) {
        double result = thetas.get(thetas.size() - 1);

        for (int i = 0; i < data.size(); i++) {
            result += thetas.get(i) * data.get(i);
        }

        assert Double.isFinite(result) : "result: " + result + " data: " + data;

        return result;
    }
}
