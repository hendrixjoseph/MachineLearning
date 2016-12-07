package edu.wright.hendrix11.cs7830.gui.charts.scatter;

import edu.wright.hendrix11.cs7830.StockData;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Created by Joe on 12/1/2016.
 */
public class ScatterChart2Gui extends SuperScatter {

    @FXML
    public Slider weekSlider;
    @FXML
    private VBox radioBox;
    @FXML
    private ScatterChart scatterChart;

    private String lastText = null;

    @FXML
    private void initialize() {
        initialize(radioBox);

        weekSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(lastText != null) {
                changeX(lastText);
            }
        });
    }

    @Override
    protected void changeX(String text) {
        lastText = text;

        int week = (int) weekSlider.getValue();

        ToDoubleFunction<StockData> f = buttons.get(text);

        List<XYChart.Data> chartData = stocks.stream()
                .map(stock -> new XYChart.Data(stock.getStatsFor(f, week).getAverage(), stock.getPercentIncrease()))
                .collect(Collectors.toList());

        XYChart.Series series = new XYChart.Series<>();
        series.getData().setAll(chartData);

        scatterChart.getData().clear();
        scatterChart.getData().add(series);

        scatterChart.getXAxis().setLabel(text);
    }


}
