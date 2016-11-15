package edu.wright.hendrix11.cs7830.neural;


import edu.wright.hendrix11.cs7830.tools.ArrayTools;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Joe on 10/31/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        breastCancer();
    }

    private static void breastCancer() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("BreastCancerData.csv"));

        List<double[]> inputs = new ArrayList<>();
        List<double[]> targets = new ArrayList<>();

        int recurrant = 2;
        int[] readInputs = {3,5,7,8,11}; // Time Texture	Area Smoothness	Concave

        for(String line : lines) {
            String[] tokens = line.split(",");

            targets.add(new double[]{Double.parseDouble(tokens[recurrant])});

            List<Double> input = new ArrayList<>();

            for(int i = 0; i < readInputs.length; i++) {
                input.add(Double.parseDouble(tokens[readInputs[i]]));
            }

            inputs.add(ArrayTools.convertDoubleListToArray(input));
        }

        double[][] in = ArrayTools.convertDouble2DListToArray(inputs);
        double[][] tar = ArrayTools.convertDouble2DListToArray(targets);

        breastCancer(5,in,tar);
        breastCancer(10,in,tar);
        breastCancer(30,in,tar);
    }

    private static void breastCancer(int numHidden, double[][] inputs, double[][] targets) throws IOException {

        Network network = new Network(5,numHidden,numHidden,1);

        network.setInputs(inputs);
        network.setTargets(targets);
        network.normalize();

        network.learn(0.5,0.8);

        Map<Integer, Map<Integer, Integer>> matrix = network.getConfusionMatrix2(0.0);

        for(int i = 0; i <= 1; i++) {
            System.out.print("\t" + i);
        }

        System.out.println();

        for(int t = 0; t <= 1; t++) {

            System.out.print(t);

            for(int g = 0; g <= 1; g++) {
                if(matrix.get(g) != null && matrix.get(g).get(t) != null) {
                    System.out.print("\t" + matrix.get(g).get(t));
                } else {
                    System.out.print("\t0");
                }
            }

            System.out.println();
        }

        System.out.println();

    }

    private static void breastTest2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("BreastCancerData.csv")); //bctrain

        List<double[]> inputs = new ArrayList<>();
        List<double[]> targets = new ArrayList<>();

        int recurrant = 2;
        int[] readInputs = {3,5,7,8,11}; // Time Texture	Area Smoothness	Concave

        for(String line : lines) {
            String[] tokens = line.split(",");

            targets.add(new double[]{Double.parseDouble(tokens[recurrant])});

            List<Double> input = new ArrayList<>();

            for(int i = 0; i < readInputs.length; i++) {
                input.add(Double.parseDouble(tokens[readInputs[i]]));
            }

            inputs.add(ArrayTools.convertDoubleListToArray(input));
        }

        double[][] in = ArrayTools.convertDouble2DListToArray(inputs);
        double[][] tar = ArrayTools.convertDouble2DListToArray(targets);

        Network network = new Network(5,5,10,1);

        network.setInputs(in);
        network.setTargets(tar);
        network.normalize();

        network.learn(0.1,0.8);

        Map<Integer, Map<Integer, Integer>> matrix = network.getConfusionMatrix2(0.0);

        for(int i = 0; i <= 1; i++) {
            System.out.print("\t" + i);
        }

        System.out.println();

        for(int t = 0; t <= 1; t++) {

            System.out.print(t);

            for(int g = 0; g <= 1; g++) {
                if(matrix.get(g) != null && matrix.get(g).get(t) != null) {
                    System.out.print("\t" + matrix.get(g).get(t));
                } else {
                    System.out.print("\t0");
                }
            }

            System.out.println();
        }

        System.out.println();
    }

    private static void wineTest() throws IOException {
        wine(3);
        wine(5);
        wine(10);
        wine(20);
        wine(100);
    }

    private static void wine(int numHidden) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("wine.csv"));

        List<double[]> inputs = new ArrayList<>();
        List<double[]> targets = new ArrayList<>();

        for(String line : lines) {
            String[] tokens = line.split(",");

            switch(tokens[0]) {
                case "1":
                    targets.add(new double[]{1.0,0.0,0.0});
                    break;
                case "2":
                    targets.add(new double[]{0.0,1.0,0.0});
                    break;
                case "3":
                    targets.add(new double[]{0.0,0.0,1.0});
            }

            List<Double> input = new ArrayList<>();

            for(int i = 1; i < tokens.length; i++) {
                input.add(Double.parseDouble(tokens[i]));
            }

            inputs.add(ArrayTools.convertDoubleListToArray(input));
        }

        Network network = new Network(13,numHidden,3);

        network.setInputs(ArrayTools.convertDouble2DListToArray(inputs));
        network.setTargets(ArrayTools.convertDouble2DListToArray(targets));
        network.normalize();

        network.learn(0.1, 0.8);

//        PrintWriter writer = new PrintWriter("output.csv");
//
//        for(int i = 0; i < Math.min(network.getErrors().size(), 20_000); i++) {
//            double cost = network.getErrors().get(i);
//            writer.println(cost);
//        }
//
//        writer.close();

        Map<Integer, Map<Integer, Integer>> matrix = network.getConfusionMatrix(0.8);

        for(int i = 1; i <= 3; i++) {
            System.out.print("\t" + i);
        }

        System.out.println();

        for(int t = 1; t <= 3; t++) {

            System.out.print(t);

            for(int g = 1; g <= 3; g++) {
                if(matrix.get(g) != null && matrix.get(g).get(t) != null) {
                    System.out.print("\t" + matrix.get(g).get(t));
                } else {
                    System.out.print("\t0");
                }
            }

            System.out.println();
        }

        System.out.println();
    }

    private static void test() {
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
