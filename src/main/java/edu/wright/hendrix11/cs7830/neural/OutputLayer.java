package edu.wright.hendrix11.cs7830.neural;

/**
 * Created by Joe on 11/3/2016.
 */
public class OutputLayer extends Layer {

    public OutputLayer(int size, int neuronInputSize) {
        super(size, neuronInputSize);
    }

    public void computeDeltaWeights(double target[]) {
        for(int i = 0; i < neurons.length; i++) {
            neurons[i].computeDeltaWeights(previousLayer, target[i]);
        }
    }
}
