package com.example.application5combobox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * We learnt:
 * 1. ComboBox, it's Editable option (purpose of which is search without using dropdown button).
 * 2. (Observation) doAction on ComboBox when it's in editable state causes fluctuations whenever any button attempts accessing comboItems.getSelectionModel(). So use selectionModel.isEmpty().
 * 3. Add, show and delete from ComboBox.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ComboBoxView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ComboBox!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}