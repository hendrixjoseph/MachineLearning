package edu.wright.hendrix11.cs7830;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public double getIncrease() {
        assert sorted : "Please sort first.";

        return data.get(data.size() -1 ).getClose() - data.get(0).getOpen();
    }

    public double getPercentIncrease() {
        assert sorted : "Please sort first.";

        return getIncrease() / data.get(0).getOpen();
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Stock && equals((Stock)o));
    }

    public boolean equals(Stock stock) {
        return stock != null && symbol == stock.symbol;
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
