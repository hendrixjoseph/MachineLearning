package edu.wright.hendrix11.cs7830.neural;


import edu.wright.hendrix11.cs7830.tools.ArrayTools;
import edu.wright.hendrix11.cs7830.tools.Ordering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joe on 10/31/2016.
 */
public class Network {
    private Layer[] layers;

    private double[][] inputs;
    private double[][] targets;

    private List<Double> error = new ArrayList<>();

    private Ordering order;

    public Network(int... layerSize) {
        layers = new Layer[layerSize.length - 1];

        for (int i = 0; i < layers.length; i++) {
            if (i == layers.length - 1) {
                layers[i] = new OutputLayer(layerSize[i + 1], layerSize[i]);
            } else {
                layers[i] = new HiddenLayer(layerSize[i + 1], layerSize[i]);
            }

            if (i > 0) {
                layers[i].setPreviousLayer((HiddenLayer) layers[i - 1]);
            }
        }
    }

    public List<Double> getErrors() {
        return error;
    }

    public void setInputs(double[][] inputs) {
        this.inputs = inputs;
    }

    public void normalize() {
        inputs = ArrayTools.normalize(inputs);
        //targets = ArrayTools.normalize(targets);
    }

    public void setTargets(double[][] targets) {
        this.targets = targets;
    }

    public double[] getOutputs(double[] inputs) {
        double[] prevOutputs = inputs;

        for (Layer layer : layers) {
            prevOutputs = layer.getOutputs(prevOutputs);
        }

        return prevOutputs;
    }

    public void learn(double learningRate) {
        learn(learningRate, 1.0);
    }

    public void learn(double learningRate, double testPercent) {
        if (inputs.length != targets.length) {
            throw new IllegalArgumentException("inputs.length (" + inputs.length + ") != targets.length (" + targets.length + ")");
        }

        order = new Ordering(inputs.length);
        order.shuffle();

        for (int generation = 0; generation < 100_000; generation++) {
            error.add(getError());

            if (error.size() > 1) {
                int size = error.size();

                if (Math.abs(error.get(size - 1) - error.get(size - 2)) < 0.000000001) {
                    break;
                }
            }

            for (int x = 0; x < order.size() * testPercent; x++) {
                int i = order.getIndex(x);

                // forward propogation
                getOutputs(inputs[i]);

                backPropogation(inputs[i], targets[i]);

                for (Layer layer : layers) {
                    layer.applyDeltaWeights(learningRate);
                }
            }
        }
    }


    private void backPropogation(double[] input, double[] target) {
        int outputLayer = layers.length - 1;

        for (int layer = layers.length - 1; layer >= 0; layer--) {
            if (layer == outputLayer) {
                layers[layer].computeDeltaWeights(target);
            } else {
                layers[layer].computeDeltaWeights(input);
            }
        }
    }

    public double getError() {
        double error = 0.0;

        for (int i = 0; i < inputs.length; i++) {
            double[] outputs = getOutputs(inputs[i]);

            for (int j = 0; j < outputs.length; j++) {
                error += (targets[i][j] - outputs[j]) * (targets[i][j] - outputs[j]) / 2;
            }
        }

        return error / inputs.length;
    }

    public Map<Integer, Map<Integer, Integer>> getConfusionMatrix(double testPercent) {

        Map<Integer, Map<Integer, Integer>> matrix = new HashMap<>();

        for (int x = (int) (order.size() * testPercent); x < order.size(); x++) {
            int i = order.getIndex(x);

            double[] outputs = getOutputs(inputs[i]);

            int value = ArrayTools.findMaxIndex(outputs) + 1;
            int target = ArrayTools.findMaxIndex(targets[i]) + 1;


            if (matrix.get(value) == null) {
                matrix.put(value, new HashMap<>());
            }

            if (matrix.get(value).get(target) == null) {
                matrix.get(value).put(target, 0);
            }

            matrix.get(value).put(target, matrix.get(value).get(target) + 1);
        }

        return matrix;
    }

    public Map<Integer, Map<Integer, Integer>> getConfusionMatrix2(double testPercent) {

        Map<Integer, Map<Integer, Integer>> matrix = new HashMap<>();

        for (int x = (int) (order.size() * testPercent); x < order.size(); x++) {
            int i = order.getIndex(x);

            double[] outputs = getOutputs(inputs[i]);

            int value = (int) Math.round(outputs[0]);
            int target = (int) Math.round(targets[i][0]);

            if (matrix.get(value) == null) {
                matrix.put(value, new HashMap<>());
            }

            if (matrix.get(value).get(target) == null) {
                matrix.get(value).put(target, 0);
            }

            matrix.get(value).put(target, matrix.get(value).get(target) + 1);
        }

        return matrix;
    }
}
