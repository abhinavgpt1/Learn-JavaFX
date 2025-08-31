package com.example.application3markscalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * We learnt:
 * 1. how to get and set text.
 * 2. apply image in ui.
 * 3. perform validations and throw alert dialog box.
 * 4. take input from user.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MarksCalculatorView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Calculate Marks!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}