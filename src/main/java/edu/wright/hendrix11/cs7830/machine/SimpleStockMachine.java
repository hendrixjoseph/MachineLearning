package edu.wright.hendrix11.cs7830.machine;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Joe on 12/6/2016.
 */
public class SimpleStockMachine {

    private Map<Stock, SimpleRegression> stocks = new HashMap<>();

    public SimpleStockMachine(List<Stock> stocks, int weeks) {
        for(Stock stock : stocks) {
            SimpleRegression simple = new SimpleRegression(true);

            for(StockData data : stock.getData().stream().limit(weeks).collect(Collectors.toList())) {
                simple.addData(data.getDayOfYear(), data.getClose());
            }

            this.stocks.put(stock, simple);
        }
    }

    public Map<Stock, Integer> getResults() {
        Map<Stock, Integer> results = new HashMap<>();

        for (Map.Entry<Stock, SimpleRegression> entry : stocks.entrySet()) {
            int yearCloseGuess = (int) entry.getValue().predict(175.0);
            results.put(entry.getKey(), yearCloseGuess);
        }

        return results;
    }
}
