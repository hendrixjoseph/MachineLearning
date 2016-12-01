package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Joe on 11/29/2016.
 */
public class Controller {
    @FXML
    LineChart stockChart;

    @FXML
    PieChart pieChart;

    private DowData data;

    @FXML
    private void initialize() throws IOException, ParseException {
        data = new DowData("dow_jones_index.csv");

        XYChart.Series index = new XYChart.Series();
        index.setName("index");
        index.getData().add(new XYChart.Data(data.getFirstDayOfYear(), data.getAverageYearOpen() / 100.0));
        index.getData().add(new XYChart.Data(data.getLastDayOfYear(), data.getAverageYearClose() / 100.0));
        stockChart.getData().add(index);

        for(Stock stock : data.getStocks()) {
            stockChart.getData().add(getSeries(stock));
            pieChart.getData().add(getPieData(stock));
        }
    }

    public PieChart.Data getPieData(Stock stock) {
        return new PieChart.Data(stock.getSymbol(), stock.getYearOpen() / 100.0);
    }

    public XYChart.Series getSeries(Stock stock) {
        XYChart.Series series = new XYChart.Series();
        series.setName(stock.getSymbol());

        for(StockData data : stock.getData()) {
            series.getData().add(new XYChart.Data(data.getDayOfYear(), data.getOpen() / 100.0));
        }

        return series;
    }
}
