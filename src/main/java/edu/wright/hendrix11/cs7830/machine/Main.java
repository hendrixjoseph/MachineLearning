package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.tools.Reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public class Main {
    public static void main(String[] args) throws IOException {
        linearCancerTest2();
    }

    private static void trainAndTestCancerTest() throws IOException {
        int[] bccolums = {6,9}; // parameter, compactness

        List<List<Double>> data = Reader.getData("BreastCancerData.csv", bccolums);

        List<Double> inputs = data.get(0);
        List<Double> outputs = data.get(1);

        Machine machine = new LinearMachine(inputs, outputs);
        machine.generateNormalization();
        machine.learn(0.001, 0.75);

        System.out.println("Generations: " + machine.getGeneration());
        System.out.println("Average cost: " + machine.getCurrentAverageCost());

        double[] test = {0, 50, 100, 150, 200};

        for(double t : test) {
            System.out.println(t + "\t" + machine.answerDenormalized(t));
        }

        writeCostOverTime(machine);
    }

    private static void threeValueCancerTest() throws IOException {
        int[] bccolums = {6,7,9}; // parameter, area, compactness

        Machine machine = runThree("BreastCancerData.csv", bccolums);

        System.out.println("Generations: " + machine.getGeneration());

        writeCostOverTime(machine);
    }

    private static void linearCancerTest() throws IOException {
        int[] bccolums = {6,9}; // parameter, compactness

        Machine machine = run("BreastCancerData.csv", bccolums);

        System.out.println("Generations: " + machine.getGeneration());

        writeCostOverTime(machine);
    }

    private static void linearCancerTest2() throws IOException {
        int[] bccolums = {4,6,7,9}; // texture, parameter, area, compactness

        List<List<Double>> data = Reader.getData("BreastCancerData.csv", bccolums);

        List<Double> texture = data.get(0);
        List<Double> parameter = data.get(1);
        List<Double> area = data.get(2);
        List<Double> compactness = data.get(3);

        InfiniteLinearMachine machine = new InfiniteLinearMachine(compactness, texture, parameter, area);
        machine.generateNormalization();
        machine.learn(0.0001);

        System.out.println("Generations: " + machine.getGeneration());

        PrintWriter writer = new PrintWriter("output.csv");

        for(int i = machine.getCostOverTime().size() - 20_000; i < machine.getCostOverTime().size(); i++) {
            double cost = machine.getCostOverTime().get(i);
            writer.println(cost);
        }

        writer.close();
    }

    private static void quadCancerTest() throws IOException {
        int[] bccolums = {6,9}; // parameter, compactness

        Machine machine = runQuad("BreastCancerData.csv", bccolums);

        System.out.println("Generations: " + machine.getGeneration());

        writeCostOverTime(machine);
    }

    private static void linearTest() throws IOException {
        int[] columns = {0,1};
        System.out.println(run("LinearTestData.csv", columns));
    }

    private static void quadTest() throws IOException {
        int[] columns = {0,1};
        System.out.println(runQuad("QuadraticTestData.csv", columns));
    }

    private static void linearTest2() throws IOException {
        int[] columns = {0,1};
        List<List<Double>> data = Reader.getData("LinearTestData.csv", columns);

        List<Double> inputs = data.get(0);
        List<Double> outputs = data.get(1);

        InfiniteLinearMachine machine = new InfiniteLinearMachine(outputs, inputs);
        machine.generateNormalization();
        machine.learn(0.001);

        System.out.println(machine);
    }

    private static Machine runQuad(String filename, int[] columns) throws IOException {
        List<List<Double>> data = Reader.getData(filename, columns);

        List<Double> inputs = data.get(0);
        List<Double> outputs = data.get(1);

        Machine machine = new QuadraticMachine(inputs, outputs);
        machine.generateNormalization();
        machine.learn(0.0001);

        return machine;
    }

    private static Machine run(String filename, int[] columns) throws IOException {
        List<List<Double>> data = Reader.getData(filename, columns);

        List<Double> inputs = data.get(0);
        List<Double> outputs = data.get(1);

        Machine machine = new LinearMachine(inputs, outputs);
        machine.generateNormalization();
        machine.learn(0.001);

        return machine;
    }

    private static Machine runThree(String filename, int[] columns) throws IOException {
        List<List<Double>> data = Reader.getData(filename, columns);

        List<Double> inputs1 = data.get(0);
        List<Double> inputs2 = data.get(1);
        List<Double> outputs = data.get(2);

        Machine machine = new TriValueLinearMachine(inputs1, inputs2, outputs);
        machine.generateNormalization();
        machine.learn(0.001);

        return machine;
    }

    private static void writeCostOverTime(Machine machine) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("output.csv");

        for(int i = 0; i < Math.min(machine.getCostOverTime().size(), 20_000); i++) {
            double cost = machine.getCostOverTime().get(i);
            writer.println(cost);
        }

        writer.close();
    }
}