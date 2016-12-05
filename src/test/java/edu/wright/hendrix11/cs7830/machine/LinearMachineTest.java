package edu.wright.hendrix11.cs7830.machine;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public class LinearMachineTest {
    private Double[] inputs = {-10.0,-8.0,-6.0,-4.0,-2.0,0.0,2.0,4.0,6.0,8.0,10.0};
    private Double[] outputs = {-11.2,-8.96,-6.72,-4.48,-2.24,0.0,2.24,4.48,6.72,8.96,11.2};

    @Test
    public void test() {
        List<Double> in = Arrays.asList(inputs);
        List<Double> out = Arrays.asList(outputs);

        Machine machine = new LinearMachine(out, in);
        machine.learn(0.001);

        System.out.println("generations: " + machine.getGeneration());

        System.out.println("input\toutput\tguess");

        for(int i = 0; i < inputs.length; i++) {
            System.out.println(inputs[i] + "\t" + outputs[i] + "\t" + machine.hypothesis(inputs[i]));
        }
    }
}