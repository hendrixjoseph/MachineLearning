package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.gui.charts.*;
import edu.wright.hendrix11.cs7830.gui.charts.scatter.ScatterChart2Gui;
import edu.wright.hendrix11.cs7830.gui.charts.scatter.ScatterChartGui;
import edu.wright.hendrix11.cs7830.gui.linear.LinearTab2Controller;
import edu.wright.hendrix11.cs7830.gui.linear.LinearTabController;
import edu.wright.hendrix11.cs7830.gui.tables.StockDataTable;
import edu.wright.hendrix11.cs7830.gui.tables.StockSummaryTable;
import javafx.fxml.FXML;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Joe on 11/29/2016.
 */
public class Controller {

    @FXML
    private InvestTabController investTabController;


    @FXML
    private PieGui pieTabController;

    @FXML
    private ScatterChartGui scatterChartTabController;

    @FXML
    private ScatterChart2Gui scatterChart2TabController;

    @FXML
    private LineChartGui lineChartTabController;

    @FXML
    private StockSummaryTable stockSummaryTableTabController;

    @FXML
    private StockDataTable stockDataTableTabController;

    @FXML
    private LinearTabController linearTabController;

    @FXML
    private LinearTab2Controller linearTab2Controller;

    @FXML
    private void initialize() throws IOException, ParseException {
        DowData data = new DowData("dow_jones_index.csv");



        List<Stock> stocks = data.getStocks();
        linearTabController.setStocks(stocks);
        linearTab2Controller.setStocks(stocks);
        pieTabController.setStocks(stocks);
        scatterChartTabController.setStocks(stocks);
        scatterChart2TabController.setStocks(stocks);
        lineChartTabController.setStocks(stocks);
        stockSummaryTableTabController.setStocks(stocks);
        stockDataTableTabController.setStocks(stocks);
        investTabController.setStocks(stocks);
    }
}
