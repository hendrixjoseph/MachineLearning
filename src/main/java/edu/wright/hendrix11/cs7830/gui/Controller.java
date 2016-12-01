package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import edu.wright.hendrix11.cs7830.machine.LinearStockMachine;
import edu.wright.hendrix11.cs7830.machine.Machine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

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

    @FXML
    Text linearMachineText;

    private DowData data;

    @FXML
    private void initialize() throws IOException, ParseException {
        linearMachineText.setText("this is the text");

        data = new DowData("dow_jones_index.csv");
        
        for (Stock stock : data.getStocks()) {
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

        for (StockData data : stock.getData()) {
            series.getData().add(new XYChart.Data(data.getDayOfYear(), data.getOpen() / 100.0));
        }

        return series;
    }

    public void clickButton(ActionEvent event) {
        Stock stock = data.getStocks().get(0);

        Machine machine = new LinearStockMachine(stock, stock.getYearClose(), StockData::getOpen, StockData::getVolume);
    }
}
