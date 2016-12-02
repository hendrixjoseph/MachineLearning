package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import edu.wright.hendrix11.cs7830.gui.charts.LineChartGui;
import edu.wright.hendrix11.cs7830.gui.charts.PieGui;
import edu.wright.hendrix11.cs7830.gui.charts.StockTable;
import edu.wright.hendrix11.cs7830.machine.LinearStockMachine;
import edu.wright.hendrix11.cs7830.machine.Machine;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Joe on 11/29/2016.
 */
public class Controller {


    @FXML
    PieGui pieTabController;

    @FXML
    LineChartGui lineChartTabController;

    @FXML
    StockTable stockTableTabController;

    @FXML
    Text linearMachineText;

    private DowData data;

    @FXML
    private void initialize() throws IOException, ParseException {
        linearMachineText.setText("this is the text");

        data = new DowData("dow_jones_index.csv");

        List<Stock> stocks = data.getStocks();

        pieTabController.setStocks(stocks);
        lineChartTabController.setStocks(stocks);
        stockTableTabController.setStocks(stocks);
    }



    public void clickButton(ActionEvent event) {
        Stock stock = data.getStocks().get(0);

        Machine machine = new LinearStockMachine(stock, stock.getYearClose(), StockData::getOpen, StockData::getVolume);
    }
}
