package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.machine.StockChangeMachine;
import edu.wright.hendrix11.cs7830.machine.StockMachine;
import edu.wright.hendrix11.cs7830.machine.StockTimeMachine;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.swing.Action;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Joe on 12/1/2016.
 */
public class MachineController extends SuperMachineController {
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
        initialize(resultsTable, symbolColumn);
    }

    public void setData(DowData data) {
        this.data = data;
    }

    public void clickLearnButton(ActionEvent event) {
        try {
            int numWeeks = Integer.parseInt(numWeeksField.getText());

            StockTimeMachine machine = new StockTimeMachine(data.getStocks(), numWeeks);

            String out = runMachine(machine, normalizeInputBox.isSelected(), normalizeOutputBox.isSelected(), learningRateField);
            linearMachineText.setText(out);
        } catch (Exception e) {
            linearMachineText.setText("Error: " + e.getClass().getName());
            e.printStackTrace();
        }
    }


}
