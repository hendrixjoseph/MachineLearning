package edu.wright.hendrix11.cs7830.machine;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public class LinearMachineTest {
    private static Double[] inputs = {-10.0,-8.0,-6.0,-4.0,-2.0,0.0,2.0,4.0,6.0,8.0,10.0};
    private static Double[] outputs = {-11.2,-8.96,-6.72,-4.48,-2.24,0.0,2.24,4.48,6.72,8.96,11.2};

    private List<Double> in = Arrays.asList(inputs);
    private List<Double> out = Arrays.asList(outputs);

    @Test
    public void test() {
        Machine machine = new LinearMachine(out, in);
        machine.learn(0.001);

        System.out.println("generations: " + machine.getGeneration());

        System.out.println("input\toutput\tguess");

        for(int i = 0; i < inputs.length; i++) {
            System.out.println(inputs[i] + "\t" + outputs[i] + "\t" + machine.hypothesis(inputs[i]));
        }
    }

    @Test
    public void testNormalized() throws FileNotFoundException {
        Machine machine = new LinearMachine(out, in);
        machine.normalizeInputs();
        machine.normalizeOutputs();
        machine.learn(0.001);

        System.out.println("generations: " + machine.getGeneration());

        System.out.println("input\toutput\tguess");

        for(int i = 0; i < inputs.length; i++) {
            System.out.println(inputs[i] + "\t" + outputs[i] + "\t" + machine.hypothesis(inputs[i]));
        }

        PrintWriter writer = new PrintWriter("output.csv");

        for(double cost : machine.getCost()) {
            writer.println(cost);
        }

        writer.close();
    }
}