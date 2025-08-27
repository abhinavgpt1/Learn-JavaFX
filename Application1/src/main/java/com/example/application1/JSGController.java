/**
 * Sample Skeleton for 'jsg-view.fxml' Controller Class
 */

package com.example.application1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class JSGController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="helloWorldText"
    private Label helloWorldText; // Value injected by FXMLLoader

    @FXML
    void doClose(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    void doHelloWorld(ActionEvent event) {
        System.out.println("Hello world printed on console");
        helloWorldText.setText("Hello World");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert helloWorldText != null : "fx:id=\"helloWorldText\" was not injected: check your FXML file 'jsg-view.fxml'.";
    }

}