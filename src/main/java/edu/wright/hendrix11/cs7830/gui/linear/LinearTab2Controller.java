package edu.wright.hendrix11.cs7830.gui.linear;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import edu.wright.hendrix11.cs7830.machine.MultiStockMachine;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Created by Joe on 12/1/2016.
 */
public class LinearTab2Controller {

    @FXML
    private HBox parameterBoxes;
    @FXML
    private TableView resultsTable;

    @FXML
    private TableColumn<Map.Entry<Stock, Integer>, String> symbolColumn;

    private List<Stock> stocks;

    private HashMap<String, ToDoubleFunction<StockData>> boxes = new HashMap<>();

    private void createCheckBox(String text, ToDoubleFunction<StockData> data) {
        boxes.put(text, data);
        CheckBox box = new CheckBox(text);
        box.setOnAction(event -> setSelectedStocks());

        parameterBoxes.getChildren().add(box);
    }

    private void setSelectedStocks() {
        List<ToDoubleFunction<StockData>> parameters = parameterBoxes.getChildren().stream()
                .filter(box -> box instanceof CheckBox && ((CheckBox) box).isSelected())
                .map(box -> boxes.get(((CheckBox) box).getText()))
                .collect(Collectors.toList());

        MultiStockMachine machine = new MultiStockMachine(stocks, parameters);

        Set<Map.Entry<Stock, Integer>> entries = machine.getResults().entrySet();

        resultsTable.getItems().setAll(entries);
    }

    @FXML
    private void initialize() {

        createCheckBox("Open", StockData::getOpen);
        createCheckBox("Close", StockData::getClose);
        createCheckBox("High", StockData::getHigh);
        createCheckBox("Low", StockData::getLow);
        createCheckBox("Percent Change Price", StockData::getPercentChangePrice);
        createCheckBox("Days to Next Dividend", StockData::getDaysToNextDividend);
        createCheckBox("Trades", StockData::getVolume);
        createCheckBox("Percent change trades", StockData::getPercentChangeVolume);

        ObservableList<TableColumn<Map.Entry<Stock, Integer>, Number>> columns = resultsTable.getColumns();

        symbolColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getSymbol()));

        int column = 1;
        columns.get(column++).setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getKey().getYearOpen() / 100.0));
        columns.get(column++).setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getValue() / 100.0));
        columns.get(column++).setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getKey().getYearClose() / 100.0));
        columns.get(column++).setCellValueFactory(param -> {
            int start = param.getValue().getKey().getYearOpen();
            int end = param.getValue().getValue();
            return new SimpleDoubleProperty(100 * (end - start) / start);
        });
        columns.get(column++).setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getKey().getPercentIncrease() * 100));
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;

        MultiStockMachine machine = MultiStockMachine.createGenericMachine(stocks);

        Set<Map.Entry<Stock, Integer>> entries = machine.getResults().entrySet();

        resultsTable.getItems().setAll(entries);
    }
}
