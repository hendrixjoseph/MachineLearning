package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.machine.StockMachine;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Map;

/**
 * Created by Joe on 12/1/2016.
 */
public class MachineController {
    @FXML
    public TextField numWeeksField;
    @FXML
    public TextField learningRateField;
    @FXML
    public CheckBox normalizeInputBox;
    @FXML
    public CheckBox normalizeOutputBox;
    @FXML
    private Label linearMachineText;

    @FXML
    private TableView resultsTable;

    @FXML
    private TableColumn<Map.Entry<Stock, Integer>, String> symbolColumn;

    private DowData data;

    @FXML
    private void initialize() {
        ObservableList<TableColumn<Map.Entry<Stock, Integer>, Number>> columns = resultsTable.getColumns();

        symbolColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().getSymbol()));

        int column = 1;
        columns.get(column++).setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getKey().getYearOpen() / 100.0));
        columns.get(column++).setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getKey().getYearClose() / 100.0));
        columns.get(column++).setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getValue() / 100.0));
        columns.get(column++).setCellValueFactory(param -> {
            int start = param.getValue().getKey().getYearOpen();
            int end = param.getValue().getValue();
            return new SimpleDoubleProperty(100 * (end - start) / start);
        });
        columns.get(column++).setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getKey().getPercentIncrease() * 100));
    }

    public void setData(DowData data) {
        this.data = data;
    }

    public void clickLearnButton(ActionEvent event) {
        try {
            int numWeeks = Integer.parseInt(numWeeksField.getText());
            double learningRate = Double.parseDouble(learningRateField.getText());

            StockMachine machine = new StockMachine(data.getStocks(), numWeeks);

            if (normalizeInputBox.isSelected()) {
                machine.normalizeInputs();
            }

            if (normalizeOutputBox.isSelected()) {
                machine.normalizeOutputs();
            }

            machine.runMachines(learningRate);

            resultsTable.getItems().clear();
            resultsTable.getItems().addAll(machine.getResults().entrySet());

            linearMachineText.setText("Completed in " + machine.maxGeneration() + " generations.");
        } catch (Exception e) {
            linearMachineText.setText("Error: " + e.getClass().getName());
        }
    }
}
