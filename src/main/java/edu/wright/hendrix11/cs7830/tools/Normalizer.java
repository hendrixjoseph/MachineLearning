package edu.wright.hendrix11.cs7830.tools;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * @author Joe Hendrix
 */
public class Normalizer {

    DoubleSummaryStatistics stats;

    public Normalizer() {
        stats = null;
    }

    public Normalizer(List<Double> vector) {
        stats = vector.stream().mapToDouble(d -> d).summaryStatistics();
    }

    public List<Double> normalize(double[] values) {
        List<Double> normalized = new ArrayList<>();

        for (double value : values) {
            normalized.add(normalize(value));
        }

        return normalized;
    }

    public List<Double> normalize(List<Double> values) {
        List<Double> normalized = new ArrayList<>();

        for (double value : values) {
            normalized.add(normalize(value));
        }

        return normalized;
    }

    public List<Double> denormalize(List<Double> values) {
        List<Double> denormalized = new ArrayList<>();

        for (double value : values) {
            denormalized.add(denormalize(value));
        }

        return denormalized;
    }

    public double normalize(double value) {
        if (stats != null) {
            double normalized = (value - stats.getMin()) / (stats.getMax() - stats.getMin());

            assert Double.isFinite(normalized);

            return normalized;
        } else {
            return value;
        }
    }

    public double denormalize(double value) {
        if (stats != null) {
            double denormalized = value * (stats.getMax() - stats.getMin()) + stats.getMin();

            assert Double.isFinite(denormalized);

            return denormalized;
        } else {
            return value;
        }
    }
}
