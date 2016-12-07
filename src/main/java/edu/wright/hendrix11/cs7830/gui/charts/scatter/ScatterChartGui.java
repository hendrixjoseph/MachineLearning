package edu.wright.hendrix11.cs7830.gui.charts.scatter;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Created by Joe on 12/1/2016.
 */
public class ScatterChartGui extends SuperScatter {

    @FXML
    private VBox radioBox;
    @FXML
    private ScatterChart scatterChart;

    @FXML
    private void initialize() {
        initialize(radioBox);
    }

    @Override
    protected void changeX(String text) {
        ToDoubleFunction<StockData> f = buttons.get(text);

        List<XYChart.Data> chartData = stocks.stream()
                .map(Stock::getData)
                .flatMap(List::stream)
                .map(data -> new XYChart.Data(f.applyAsDouble(data), data.getPercentChangePrice()))
                .collect(Collectors.toList());

        XYChart.Series series = new XYChart.Series<>();
        series.getData().setAll(chartData);

        scatterChart.getData().clear();
        scatterChart.getData().add(series);

        scatterChart.getXAxis().setLabel(text);
    }
}
