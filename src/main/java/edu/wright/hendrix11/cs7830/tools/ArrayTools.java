package edu.wright.hendrix11.cs7830.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 11/7/2016.
 */
public class ArrayTools {
    public static List<Double> createList(int size, double values) {
        List<Double> newList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            newList.add(values);
        }

        return newList;
    }

    public static double[][] convertDouble2DListToArray(List<double[]> list) {
        double[][] array = new double[list.size()][];

        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static double[] convertDoubleListToArray(List<Double> list) {
        double[] array = new double[list.size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static int findMaxIndex(double[] array) {
        int index = 0;

        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[index]) {
                index = i;
            }
        }

        return index;
    }

    public static double[][] normalize(double[][] array) {
        double[][] normalized = new double[array.length][];

        for (int i = 0; i < normalized.length; i++) {
            normalized[i] = normalize(array[i]);
        }

        return normalized;
    }

    public static double[] normalize(double[] array) {
        double min = array[0];
        double max = array[0];
        double sum = 0.0;

        for (double d : array) {
            min = Math.min(d, min);
            max = Math.max(d, max);
            sum += d;
        }

        double average = sum / array.length;
        double range = max - min;

        double[] normalized = new double[array.length];

        for (int i = 0; i < normalized.length; i++) {
            normalized[i] = (array[i] - average) / range;
        }

        return normalized;
    }
}
