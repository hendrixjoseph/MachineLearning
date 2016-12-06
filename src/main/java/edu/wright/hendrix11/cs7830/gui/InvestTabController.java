package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.Stock;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Joe on 12/1/2016.
 */
public class InvestTabController {
    @FXML
    public TextField amountField;

    @FXML
    public TextField weekField;
    @FXML
    private Label outputLabel;
    @FXML
    private ListView<Stock> stockList;

    private Map<Stock, BooleanProperty> stockMap = new HashMap<>();

    @FXML
    public void initialize() {
        stockList.setCellFactory(CheckBoxListCell.forListView(stock -> stockMap.get(stock)));
    }


    public void setStocks(List<Stock> stocks) {
        stockList.getItems().addAll(stocks);

        for (Stock stock : stocks) {
            BooleanProperty bp = new SimpleBooleanProperty();
            bp.setValue(false);
            stockMap.put(stock, bp);
        }
    }

    public void invest() {
        try {
            int amount = Integer.parseInt(amountField.getText()) * 100;
            int week = Integer.parseInt(weekField.getText()) - 1;

            Supplier<Stream<Map.Entry<Stock, BooleanProperty>>> entryStream =
                    () -> stockMap.entrySet().stream()
                            .filter(entry -> entry.getValue().get());

            int count = (int) entryStream.get().count();

            if (count > 0) {
                double perEach = amount / count;
                final int[] total = {0};

                entryStream.get().forEach(entry -> total[0] += entry.getKey().invest(perEach, week));

                outputLabel.setText("You ended up with $" + (total[0]) / 100.00);
            } else {
                outputLabel.setText("Select something, will you?");
            }
        } catch (Exception e) {
            outputLabel.setText("Integers only, please!");
        }
    }
}
