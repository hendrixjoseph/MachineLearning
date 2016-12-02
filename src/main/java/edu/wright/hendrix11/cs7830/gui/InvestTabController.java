package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import edu.wright.hendrix11.cs7830.machine.LinearStockMachine;
import edu.wright.hendrix11.cs7830.machine.Machine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by Joe on 12/1/2016.
 */
public class InvestTabController {
  private Stock stock;
  
  public void setStock(Stock stock) {
    this.stock = stock;
  }
}
