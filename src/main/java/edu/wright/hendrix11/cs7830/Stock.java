package edu.wright.hendrix11.cs7830;

import java.util.ArrayList;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.function.ToDoubleFunction;

/**
 * Created by Joe on 11/18/2016.
 */
public class Stock {
    private String symbol;
    private boolean sorted = false;

    private List<StockData> data = new ArrayList<>();

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public DoubleSummaryStatistics getStatsFor(ToDoubleFunction<StockData> d) {
        return data.stream().mapToDouble(d).summaryStatistics();
    }

    public DoubleSummaryStatistics getStatsFor(ToDoubleFunction<StockData> d, int weeks) {
        return data.stream().limit(weeks).mapToDouble(d).summaryStatistics();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<StockData> getData() {
        return data;
    }

    public void addData(StockData datum) {
        data.add(datum);
    }

    public void sortData() {
        Collections.sort(data);
        sorted = true;
    }

    public int getYearOpen() {
        assert sorted : "Please sort first.";

        return data.get(0).getOpen();
    }

    public int getYearClose() {
        assert sorted : "Please sort first.";

        return data.get(data.size() - 1).getClose();
    }

    public double getIncrease() {
        assert sorted : "Please sort first.";

        return getYearClose() - getYearOpen();
    }

    public double getPercentIncrease() {
        assert sorted : "Please sort first.";

        return getIncrease() / getYearOpen();
    }

    public int getDayOfYearFor(int index) {
        return data.get(index).getDayOfYear();
    }

    public int numDataPoints() {
        return data.size();
    }

    public int invest(double amount) {
        return invest(amount, 0);
    }

    public int invest(double amount, int week) {
        int costPerShare = data.get(week).getOpen();

        return (int) (getYearClose() * amount / costPerShare);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Stock && equals((Stock) o));
    }

    public boolean equals(Stock stock) {
        return stock != null && symbol.equals(stock.symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    @Override
    public String toString() {
        return symbol;
    }
}
