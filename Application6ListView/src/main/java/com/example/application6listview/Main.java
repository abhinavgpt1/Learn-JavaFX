package com.example.application6listview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * We learnt:
 * 1. How to make multi-selection, multi-instance list view cross movement from ListView to ListView.
 * 2. How to add, delete and iterate over the items as well as selected items.
 * 3. Use of streams, map-reduce and functional interface for validations and operations.
 * 4. revised - TextInputDialog, Alert and Optional
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ListView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ListView!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}