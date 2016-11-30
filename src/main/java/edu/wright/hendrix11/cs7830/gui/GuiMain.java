package edu.wright.hendrix11.cs7830.gui;

import edu.wright.hendrix11.cs7830.DowData;
import edu.wright.hendrix11.cs7830.Stock;
import edu.wright.hendrix11.cs7830.StockData;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Joe on 11/29/2016.
 */
public class GuiMain extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("CS7830 Machine Learning Final Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}