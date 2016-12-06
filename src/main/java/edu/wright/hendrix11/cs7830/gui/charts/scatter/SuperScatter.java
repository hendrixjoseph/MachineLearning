package edu.wright.hendrix11.cs7830.gui.charts.scatter;

import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;

/**
 * Created by J23707 on 12/6/2016.
 */
public abstract class SuperScatter {

    protected List<Stock> stocks;

    protected Map<String, ToDoubleFunction<StockData>> buttons = new HashMap<>();

    protected void initialize(VBox radioBox) {
        ToggleGroup group = new ToggleGroup();

        buttons.put("Open", StockData::getOpen);
        buttons.put("Close", StockData::getClose);
        buttons.put("High", StockData::getHigh);
        buttons.put("Low", StockData::getLow);
        buttons.put("Trades", StockData::getVolume);
        buttons.put("Percent Change Trades", StockData::getPercentChangeVolume);
        buttons.put("Days to Next Dividend", StockData::getDaysToNextDividend);
        buttons.put("Next Dividend return", StockData::getPercentReturnNextDividend);
        buttons.put("Day of Year", StockData::getDayOfYear);
        buttons.put("Previous week's trades", StockData::getPreviousWeekVolume);
        buttons.put("Percent change of previous week's trades", StockData::getPercentChangeVolume);
        buttons.put("Day of Year", StockData::getDayOfYear);

        for (String string : buttons.keySet()) {
            RadioButton button = new RadioButton(string);
            button.setToggleGroup(group);

            button.setOnAction(event -> {
                String text = ((RadioButton) event.getSource()).getText();
                changeX(text);
            });

            radioBox.getChildren().add(button);
        }
    }

    abstract protected void changeX(String text);

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
