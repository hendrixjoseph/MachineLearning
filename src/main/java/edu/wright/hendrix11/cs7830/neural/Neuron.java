package edu.wright.hendrix11.cs7830.neural;

/**
 * Created by Joe on 10/31/2016.
 */
public class Neuron {
    private int position;

    private double bias;
    private double weights[];

    private double output;
    private double deltaBias;
    private double deltaWeights[];

    private double errorOut;
    private double deltaOutDeltaNet;

    public Neuron(final int numWeights, final int position) {
        this.position = position;

      bias = 1.0;//(2.0 * Math.random() - 1.0) * 1.0;

        weights = new double[numWeights];

        for (int i = 0; i < numWeights; i++) {
            weights[i] = 1.0;//(2.0 * Math.random() - 1.0) * 1.0;
        }
    }

    public double getOutput() {
        return output;
    }

    public void computeDeltaWeights(final Layer previousLayer, final double target) {
        deltaWeights = new double[weights.length];

        errorOut = computeError(target);
        deltaOutDeltaNet = deltaOutDeltaNet();

        deltaBias = errorOut * deltaOutDeltaNet;

        for(int i = 0; i < weights.length; i++) {
            deltaWeights[i] = errorOut * deltaOutDeltaNet * previousLayer.getNeurons()[i].getOutput();
        }
    }

    public void applyDeltaWeights(double learningRate) {
        bias = bias - learningRate * deltaBias;

        for(int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] - learningRate * (deltaWeights[i] + 0.0 * weights[i] / weights.length);
        }
    }

    public void computeDeltaWeights(final Layer previousLayer, final Layer nextLayer) {
        double[] input = new double[previousLayer.getNeurons().length];

        for(int i = 0; i < input.length; i++) {
            input[i] = previousLayer.getNeurons()[i].getOutput();
        }

        computeDeltaWeights(input, nextLayer);
    }

    public void computeDeltaWeights(final double[] input, final Layer nextLayer) {
        deltaWeights = new double[weights.length];

        errorOut = 0.0;

        for(int i = 0; i < nextLayer.getNeurons().length; i++) {
            Neuron nextNeuron = nextLayer.getNeurons()[i];

            double eNextNetNext = nextNeuron.errorOut * nextNeuron.deltaOutDeltaNet;
            double eNextOutThis = eNextNetNext * nextNeuron.weights[position];

            errorOut += eNextOutThis;
        }

        deltaOutDeltaNet = deltaOutDeltaNet();

        deltaBias = errorOut * deltaOutDeltaNet;

        for(int i = 0; i < weights.length; i++) {
            deltaWeights[i] = errorOut * deltaOutDeltaNet * input[i];
        }
    }

    private double deltaOutDeltaNet() {
        return output * (1 - output);
    }

    private double computeError(final double target) {
        return output - target;
    }

    public double getOutput(final double inputs[]) {
        if(inputs.length != weights.length) {
            throw new IllegalArgumentException("Input array length (" + inputs.length +
                    ") needs to equal weight array length (" + weights.length +")!");
        }

        output = bias;

        for(int i = 0; i < inputs.length; i++) {
            output += weights[i] * inputs[i];
        }

        output = sigmoid(output);

        return output;
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }
}
