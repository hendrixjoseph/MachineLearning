package edu.wright.hendrix11.cs7830.gui.charts;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by Joe on 12/1/2016.
 */
public class LineChartGui {
    @FXML
    public VBox checkboxes1;
    @FXML
    public VBox checkboxes2;
    @FXML
    private LineChart stockChart;

    private Map<String, Stock> stocks = new HashMap<>();

    public void setStocks(List<Stock> stocks) {
        int i = 0;

        for (Stock stock : stocks) {
            this.stocks.put(stock.getSymbol(), stock);

            stockChart.getData().add(getSeries(stock));

            CheckBox box = new CheckBox(stock.getSymbol());
            box.setSelected(true);
            box.setOnAction(event -> setSelectedStocks());

            if(i < stocks.size() / 2) {
                checkboxes1.getChildren().add(box);
            } else {
                checkboxes2.getChildren().add(box);
            }
            i++;
        }
    }

    private void setSelectedStocks() {
        List<Stock> stocks = getSelectedStocks(checkboxes1.getChildren());
        stocks.addAll(getSelectedStocks(checkboxes2.getChildren()));

        stockChart.getData().clear();

        for (Stock stock : stocks) {
            stockChart.getData().add(getSeries(stock));
        }
    }

    private List<Stock> getSelectedStocks(List<Node> boxes) {
        return boxes.stream()
                .filter(box -> box instanceof CheckBox && ((CheckBox)box).isSelected())
                .map(box -> stocks.get(((CheckBox)box).getText()))
                .collect(Collectors.toList());
    }

    private XYChart.Series getSeries(Stock stock) {
        XYChart.Series series = new XYChart.Series();
        series.setName(stock.getSymbol());

        for (StockData data : stock.getData()) {
            series.getData().add(new XYChart.Data(data.getDayOfYear(), data.getOpen() / 100.0));
        }

        return series;
    }


    public void clickAllOn(ActionEvent actionEvent) {
        clickAll(true);
    }

    public void clickAllOff(ActionEvent actionEvent) {
        clickAll(false);
    }

    private void clickAll(boolean value) {
        clickAll(checkboxes1, value);
        clickAll(checkboxes2, value);
        setSelectedStocks();
    }

    private void clickAll(VBox boxes, boolean value) {
        boxes.getChildren().forEach(node -> {
            if(node instanceof CheckBox) {
                ((CheckBox)node).setSelected(value);
            }
        });
    }
}
