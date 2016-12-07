package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import org.apache.commons.math3.stat.regression.MultipleLinearRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.*;
import java.util.function.ToDoubleFunction;

/**
 * Created by Joe on 12/6/2016.
 */
public class MultiStockMachine {

    private List<Stock> stocks;

    private MultipleLinearRegression multi;

    private List<ToDoubleFunction<StockData>> data = new ArrayList<>();

    public MultiStockMachine(List<Stock> stocks, List<ToDoubleFunction<StockData>> data) {
        this.stocks = stocks;
        this.data = data;

        double[] y = stocks.stream()
                .map(stock -> stock.getData().stream()
                        .mapToDouble(StockData::getPercentChangePrice))
                .flatMapToDouble(d -> d).toArray();

        int size = stocks.size() * stocks.get(0).getData().size();

        double[][] x = new double[size][];

        int i = 0;

        for(Stock stock : stocks) {
            for(StockData d : stock.getData()) {
                x[i++] = data.stream().mapToDouble(td -> td.applyAsDouble(d)).toArray();
            }
        }

        OLSMultipleLinearRegression multi = new OLSMultipleLinearRegression();
        multi.newSampleData(y, x);
        this.multi = multi;
    }

    public static MultiStockMachine createGenericMachine(List<Stock> stocks) {
        List<ToDoubleFunction<StockData>> data = new ArrayList<>();
        data.add(StockData::getDayOfYear);
        data.add(StockData::getVolume);

        return new MultiStockMachine(stocks, data);
    }

    public Map<Stock, Integer> getResults() {
        Map<Stock, Integer> results = new HashMap<>();

        for(Stock stock : stocks) {
            Double value = stock.getData().get(0).getClose().doubleValue();

            for(StockData data : stock.getData()) {
                double[] input = this.data.stream().mapToDouble(td -> td.applyAsDouble(data)).toArray();
                double percentChange = guess(input);
                value *= 1.0 + percentChange / 100.0;
            }

            results.put(stock, value.intValue());
        }

        return results;
    }

    public double guess(double[] x) {
        double[] params = multi.estimateRegressionParameters();

        double sum = params[0];

        for(int i = 0; i < x.length; i++) {
            sum += x[i] * params[i + 1];
        }

        return sum;
    }
}
