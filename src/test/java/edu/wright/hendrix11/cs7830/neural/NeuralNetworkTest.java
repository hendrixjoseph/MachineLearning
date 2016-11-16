package edu.wright.hendrix11.cs7830.neural;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Joe on 11/15/2016.
 */
public class NeuralNetworkTest {
    @Test
    public void testXor() throws Exception {
        Network network = new Network(2,4,1);

        double[][] inputs = {{0.0, 0.0},
                {0.0, 1.0},
                {1.0, 0.0},
                {1.0, 1.0}};

        double[][] outputs = {{0.0},{1.0},{1.0},{0.0}};

        network.setInputs(inputs);
        network.setTargets(outputs);

        network.learn(0.1);

        for(int i = 0; i < inputs.length; i++) {
            System.out.print("input: ");

            for(double input : inputs[i]) {
                System.out.print(input + " ");
            }

            System.out.print("output: " + network.getOutputs(inputs[i])[0]);


            System.out.println(" target: " + outputs[i][0]);
        }

    }
}