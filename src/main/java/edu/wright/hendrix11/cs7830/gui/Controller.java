package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.gui.charts.LineChartGui;
import edu.wright.hendrix11.cs7830.gui.charts.PieGui;
import edu.wright.hendrix11.cs7830.gui.charts.StockTable;
import javafx.fxml.FXML;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Joe on 11/29/2016.
 */
public class Controller {


    @FXML
    private PieGui pieTabController;

    @FXML
    private LineChartGui lineChartTabController;

    @FXML
    private StockTable stockTableTabController;

    @FXML
    private MachineController machineTabController;

    private DowData data;

    @FXML
    private void initialize() throws IOException, ParseException {


        data = new DowData("dow_jones_index.csv");

        machineTabController.setData(data);

        List<Stock> stocks = data.getStocks();

        pieTabController.setStocks(stocks);
        lineChartTabController.setStocks(stocks);
        stockTableTabController.setStocks(stocks);
    }
}
