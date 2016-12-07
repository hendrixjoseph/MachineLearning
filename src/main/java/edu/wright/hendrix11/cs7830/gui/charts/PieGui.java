package edu.wright.hendrix11.cs7830.gui.charts;

import edu.wright.hendrix11.cs7830.Stock;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Slider;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Joe on 12/1/2016.
 */
public class PieGui {
    @FXML
    public Slider weekSlider;
    @FXML
    private PieChart pieChart;

    private List<Stock> stocks;

    @FXML
    private void initialize() {
        weekSlider.valueProperty().addListener((observable, oldValue, newValue) -> setWeek(newValue.intValue()));
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;

        setWeek(0);
    }

    private void setWeek(int week) {
        pieChart.getData().clear();

        List<PieChart.Data> data = stocks.stream()
                .map(stock -> getPieData(stock, week))
                .collect(Collectors.toList());

        pieChart.getData().setAll(data);

        pieChart.setTitle("Week " + (week + 1));
    }

    private PieChart.Data getPieData(Stock stock, int week) {
        return new PieChart.Data(stock.getSymbol(), stock.getData().get(week).getOpen());
    }
}
