package edu.wright.hendrix11.cs7830.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Joe on 11/29/2016.
 */
public class GuiMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GuiMain.fxml"));

        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("CS7830 Machine Learning Final Project");
        stage.setScene(scene);
        stage.show();
    }
}