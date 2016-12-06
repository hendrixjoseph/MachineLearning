package edu.wright.hendrix11.cs7830;

import java.util.Optional;

/**
 * Created by Joe on 11/18/2016.
 */
public class StockData implements Comparable<StockData> {
    private String symbol;
    private Integer quarter;
    private Integer dayOfYear;
    private Integer open;
    private Integer high;
    private Integer low;
    private Integer close;
    private Integer volume;
    private Double percentChangePrice;
    private Optional<Double> percentChangeVolume;
    private Optional<Integer> previousWeekVolume;
    private Integer nextWeekOpen;
    private Integer nextWeekClose;
    private Double percentChangeNextWeekPrice;
    private Integer daysToNextDividend;
    private Double percentReturnNextDividend;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public Integer getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public Integer getClose() {
        return close;
    }

    public void setClose(Integer close) {
        this.close = close;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getPercentChangePrice() {
        return percentChangePrice;
    }

    public void setPercentChangePrice(Double percentChangePrice) {
        this.percentChangePrice = percentChangePrice;
    }

    public Double getPercentChangeVolume() {
        return percentChangeVolume.orElse(0.0);
    }

    public void setPercentChangeVolume(Double percentChangeVolume) {
        this.percentChangeVolume = Optional.ofNullable(percentChangeVolume);
    }

    public Integer getPreviousWeekVolume() {
        return previousWeekVolume.orElse(0);
    }

    public void setPreviousWeekVolume(Integer previousWeekVolume) {
        this.previousWeekVolume = Optional.ofNullable(previousWeekVolume);
    }

    public Integer getNextWeekOpen() {
        return nextWeekOpen;
    }

    public void setNextWeekOpen(Integer nextWeekOpen) {
        this.nextWeekOpen = nextWeekOpen;
    }

    public Integer getNextWeekClose() {
        return nextWeekClose;
    }

    public void setNextWeekClose(Integer nextWeekClose) {
        this.nextWeekClose = nextWeekClose;
    }

    public Double getPercentChangeNextWeekPrice() {
        return percentChangeNextWeekPrice;
    }

    public void setPercentChangeNextWeekPrice(Double percentChangeNextWeekPrice) {
        this.percentChangeNextWeekPrice = percentChangeNextWeekPrice;
    }

    public Integer getDaysToNextDividend() {
        return daysToNextDividend;
    }

    public void setDaysToNextDividend(Integer daysToNextDividend) {
        this.daysToNextDividend = daysToNextDividend;
    }

    public Double getPercentReturnNextDividend() {
        return percentReturnNextDividend;
    }

    public void setPercentReturnNextDividend(Double percentReturnNextDividend) {
        this.percentReturnNextDividend = percentReturnNextDividend;
    }

    @Override
    public int compareTo(StockData o) {
        return dayOfYear - o.dayOfYear;
    }
}
