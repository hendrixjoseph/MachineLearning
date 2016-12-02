package edu.wright.hendrix11.cs7830.gui.charts;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;

/**
 * Created by Joe on 12/1/2016.
 */
public class LineChartGui {
    @FXML
    private LineChart stockChart;

    public void setStocks(List<Stock> stocks) {
        for (Stock stock : stocks) {
            stockChart.getData().add(getSeries(stock));
        }
    }

    private XYChart.Series getSeries(Stock stock) {
        XYChart.Series series = new XYChart.Series();
        series.setName(stock.getSymbol());

        for (StockData data : stock.getData()) {
            series.getData().add(new XYChart.Data(data.getDayOfYear(), data.getOpen() / 100.0));
        }

        return series;
    }

}
