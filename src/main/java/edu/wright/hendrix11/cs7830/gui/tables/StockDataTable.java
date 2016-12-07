package edu.wright.hendrix11.cs7830.gui.tables;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Created by Joe on 12/1/2016.
 */
public class StockDataTable {
    @FXML
    private TableView stockTable;

    @FXML
    private void initialize() {

        List<TableColumn> columns = new ArrayList<>();

        TableColumn<StockData, String> column = new TableColumn<>();
        column.setText("Symbol");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSymbol()));
        columns.add(column);

        columns.add(getNumberColumn("Day of Year",StockData::getDayOfYear));
        columns.add(getNumberColumn("Open",1/100.0,StockData::getOpen));
        columns.add(getNumberColumn("Close",1/100.0,StockData::getClose));
        columns.add(getNumberColumn("High",1/100.0,StockData::getHigh));
        columns.add(getNumberColumn("Low",1/100.0,StockData::getLow));
        columns.add(getNumberColumn("Percent Change in Price",1/100.0,StockData::getPercentChangePrice));
        columns.add(getNumberColumn("Trades",StockData::getVolume));
        columns.add(getNumberColumn("Percent Change in Trades",100,StockData::getPercentChangeVolume));

        columns.add(getNumberColumn("Days to Next Dividend",StockData::getDaysToNextDividend));

        stockTable.getColumns().addAll(columns);
    }

    private TableColumn<StockData, Number> getNumberColumn(String text, ToDoubleFunction<StockData> data) {
        return getNumberColumn(text, 1.0,  data);
    }

    private TableColumn<StockData, Number> getNumberColumn(String text, double mult, ToDoubleFunction<StockData> data) {
        TableColumn<StockData, Number> column = new TableColumn<>();
        column.setText(text);
        column.setCellValueFactory(param -> new SimpleDoubleProperty(data.applyAsDouble(param.getValue()) * mult));

        return column;
    }

    public void setStocks(List<Stock> stocks) {
        List<StockData> data = stocks.stream()
                .map(Stock::getData)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        stockTable.getItems().setAll(data);
    }

}