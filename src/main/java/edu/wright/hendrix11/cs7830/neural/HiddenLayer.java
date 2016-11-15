package edu.wright.hendrix11.cs7830.neural;

/**
 * Created by Joe on 11/3/2016.
 */
public class HiddenLayer extends Layer {
    private Layer nextLayer;

    public HiddenLayer(int size, int neuronInputSize) {
        super(size, neuronInputSize);
    }

    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public void computeDeltaWeights(double[] input) {
        for(int i = 0; i < neurons.length; i++) {
            if(previousLayer != null) {
                neurons[i].computeDeltaWeights(previousLayer, nextLayer);
            } else {
                neurons[i].computeDeltaWeights(input, nextLayer);
            }
        }
    }
}
