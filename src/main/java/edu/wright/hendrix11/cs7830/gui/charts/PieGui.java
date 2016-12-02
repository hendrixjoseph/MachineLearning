package edu.wright.hendrix11.cs7830.gui.charts;

import edu.wright.hendrix11.cs7830.Stock;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.List;

/**
 * Created by Joe on 12/1/2016.
 */
public class PieGui {
    @FXML
    private PieChart pieChart;

    public void setStocks(List<Stock> stocks) {
        for (Stock stock : stocks) {
            pieChart.getData().add(getPieData(stock));
        }
    }

    private PieChart.Data getPieData(Stock stock) {
        return new PieChart.Data(stock.getSymbol(), stock.getYearOpen() / 100.0);
    }
}
