package edu.wright.hendrix11.cs7830.neural;

/**
 * Created by Joe on 10/31/2016.
 */
public abstract class Layer {
    protected final Neuron[] neurons;
    protected Layer previousLayer = null;

    public Layer(int size, int neuronInputSize) {
        neurons = new Neuron[size];

        for(int i = 0; i < size; i++) {
            neurons[i] = new Neuron(neuronInputSize, i);
        }
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public double[] getOutputs(double[] inputs) {
        double[] outputs = new double[neurons.length];

        for(int i = 0; i < outputs.length; i++) {
            outputs[i] = neurons[i].getOutput(inputs);
        }

        return outputs;
    }

    public void setPreviousLayer(HiddenLayer previousLayer) {
        this.previousLayer = previousLayer;
        previousLayer.setNextLayer(this);
    }

    public abstract void computeDeltaWeights(double[] value);

    public void applyDeltaWeights(double learningRate) {
        for(Neuron neuron : neurons) {
            neuron.applyDeltaWeights(learningRate);
        }
    }
}
