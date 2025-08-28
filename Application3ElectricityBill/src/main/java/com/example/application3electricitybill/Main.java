package com.example.application3electricitybill;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Objective:
 * 1. Make electricity bill calculation application.
 * 2. Text get and set.
 * 3. Use of icons.
 * 4. Text validation on-the-fly as user types and adding/removing icons
 *  - using textField.textProperty().addListener [use focusedProperty() if you want validation when focus changes]
 *  5. Mutable boolean. Primitive or wrapper boolean is immutable if passed in function.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ElectricityBillView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calculate Electricity Bill");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}