package edu.wright.hendrix11.cs7830.gui.linear;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.machine.SimpleStockMachine;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Created by Joe on 12/1/2016.
 */
public class LinearTabController {

    @FXML
    public Slider weekSlider;
    @FXML
    public Label bottomLabel;
    @FXML
    private TableView resultsTable;

    @FXML
    private TableColumn<Map.Entry<Stock, Integer>, String> symbolColumn;

    private List<Stock> stocks;

    @FXML
    private void initialize() {
        weekSlider.valueProperty().addListener((observable, oldValue, newValue) -> calculateForWeek(newValue.intValue()));

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

        calculateForWeek(24);
    }

    public void calculateForWeek(int week) {
        SimpleStockMachine machine = new SimpleStockMachine(stocks, week);

        Set<Map.Entry<Stock, Integer>> entries = machine.getResults().entrySet();

        resultsTable.getItems().setAll(entries);

        setLabel(entries, bottomLabel);
    }

    public static void setLabel(Set<Map.Entry<Stock, Integer>> entries, Label bottomLabel) {
        Integer fp = 0;
        Integer tp = 0;
        Integer fn = 0;
        Integer tn = 0;

        for(Map.Entry<Stock, Integer> entry : entries) {
            boolean positiveReturn = entry.getKey().getIncrease() > 0;
            boolean positiveGuess = entry.getValue() - entry.getKey().getYearOpen() > 0;

            if(positiveReturn && positiveGuess) {
                tp++;
            } else if(positiveReturn && !positiveGuess) {
                fn++;
            } else if(!positiveReturn && positiveGuess) {
                fp++;
            } else if(!positiveGuess && !positiveGuess) {
                tn++;
            }
        }

        StringJoiner sj = new StringJoiner("\t");
        sj.add("TP:").add(tp.toString()).add("FP:").add(fp.toString());
        sj.add("TN:").add(tn.toString()).add("FN:").add(fn.toString());

        bottomLabel.setText(sj.toString());
    }
}
