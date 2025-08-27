package com.example.application1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("jsg-view.fxml"));
        Scene scene = new Scene(root);
        // Scene scene = new Scene(root, w, h) // window opens with dimensions h * w and is adjustable.
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}