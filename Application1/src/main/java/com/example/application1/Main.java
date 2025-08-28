package com.example.application1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Acceptable naming convention for view and controller are CamelCase and kebab-case.
 * For example:
 *  LoreumIpsumView.fxml and LoreumIpsumViewController.java
 *  loreum-ipsum-view.fxml and loreum-ipsum-view-controller.java
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("JSGView.fxml"));
        Scene scene = new Scene(root);

        // Alternative ways:
        // FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("JSGView.fxml"));
        // Parent root = fxmlLoader.load();
        // Scene scene = new Scene(root, w, h) // window opens with dimensions h * w and is adjustable.
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}