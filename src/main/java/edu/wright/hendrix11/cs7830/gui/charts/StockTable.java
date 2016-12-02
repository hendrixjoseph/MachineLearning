package edu.wright.hendrix11.cs7830.gui.charts;

import edu.wright.hendrix11.cs7830.Stock;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created by Joe on 12/1/2016.
 */
public class StockTable {
    @FXML
    TableView stockTable;

    @FXML
    TableColumn<Stock, String> symbolColumn;
    @FXML
    TableColumn<Stock, Number> openColumn;
    @FXML
    TableColumn<Stock, Number> closeColumn;
    @FXML
    TableColumn<Stock, Number> increaseColumn;

    @FXML
    private void initialize() {
        symbolColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSymbol()));
        openColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getYearOpen() / 100.0));
        closeColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getYearClose() / 100.0));
        increaseColumn.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getPercentIncrease() * 100.0));
    }

    public void setStocks(List<Stock> stocks) {
        stockTable.getItems().setAll(stocks);
    }

}