package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.machine.StockMachine;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Map;
import java.util.Set;

/**
 * @author Joe Hendrix
 */
public class SuperMachineController {
    private TableView resultsTable;

    protected void initialize(TableView resultsTable, TableColumn<Map.Entry<Stock, Integer>, String> symbolColumn) {
        this.resultsTable = resultsTable;

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

    protected String runMachine(StockMachine machine, boolean nInput, boolean nOutput, TextField learningRateField) {
        try {
            double learningRate = Double.parseDouble(learningRateField.getText());

            if (nInput) {
                machine.normalizeInputs();
            }

            if (nOutput) {
                machine.normalizeOutputs();
            }

            machine.runMachines(learningRate);

            Set<Map.Entry<Stock, Integer>> results = machine.getResults().entrySet();

            resultsTable.getItems().clear();
            resultsTable.getItems().addAll(results);

            return "Completed in " + machine.getGenerations() + " generations.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getClass().getName();
        }
    }
}
