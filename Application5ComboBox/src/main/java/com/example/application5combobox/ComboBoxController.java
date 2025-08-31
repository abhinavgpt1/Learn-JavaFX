/**
 * Sample Skeleton for 'ComboBoxView.fxml' Controller Class
 */

package com.example.application5combobox;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

public class ComboBoxController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboItems"
    private ComboBox<String> comboItems; // Value injected by FXMLLoader

    @FXML // fx:id="lblIndex"
    private Label lblIndex; // Value injected by FXMLLoader

    @FXML // fx:id="lblItem"
    private Label lblItem; // Value injected by FXMLLoader

    @FXML
    void doAdd(ActionEvent event) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Add Item");
        inputDialog.setContentText("Enter value to add to list");
        Optional<String> valueOpt = inputDialog.showAndWait();
        // add at index is available too.
        valueOpt.ifPresent(s -> comboItems.getItems().add(s));

        // above code is same as
        // if(valueOpt.isPresent()){
        //     comboItems.getItems().add(valueOpt.get()); // add at index is available too.
        // }
    }

    @FXML
    void doDeleteAll(ActionEvent event) {
        comboItems.getItems().clear();
        lblIndex.setText("");
        lblItem.setText("");
    }

    @FXML
    void doShow(ActionEvent event) {
        // (here) same agenda as doShowSelectedItem
        // PTR:
        // Easy validation but less verbose: 
        // comboItems.getSelectionModel().isEmpty() checks both, if list is empty && if selectedIndex = -1

        if (comboItems.getItems().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("List Empty");
            a.setContentText("Please add something. \ngetSelectedItem = empty string, getSelectedIndex = -1");
            a.showAndWait();
            System.out.println("ERROR: performed selection while list was empty.");
            return;
        }
        if (comboItems.getSelectionModel().getSelectedIndex() == -1) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("No Selection or value typed not found.");
            a.setContentText("Please select something. \ngetSelectedItem = empty string, getSelectedIndex = -1");
            a.showAndWait();
            System.out.println("WARN: no selection or the value searched not found.");
            return;
        }
        // PTR:
        // As soon as you type in the search bar, the getSelectionModel value changes dynamically,
        // thus fluctuating index from -1 to +ve (if found).
        String item = comboItems.getSelectionModel().getSelectedItem();
        int index = comboItems.getSelectionModel().getSelectedIndex();
        lblItem.setText(item + " - Show btn");
        lblIndex.setText(index + " - Show btn");
    }

    @FXML
    void doShowSelectedItem(ActionEvent event) {
        // Editable ComboBox executes this function @DeleteAll, and @Show when custom search value is not found - i.e. a lot of glitches. So fix it we can have same validations as doShow() method.
        // This function works very well without validation for non-editable ComboBox.

        if (comboItems.getSelectionModel().isEmpty()) {
            System.out.println("Value not selected or list is empty");
            return;
        }
        String item = comboItems.getSelectionModel().getSelectedItem();
        int index = comboItems.getSelectionModel().getSelectedIndex();
        lblItem.setText(item);
        lblIndex.setText(String.valueOf(index));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert comboItems != null : "fx:id=\"comboItems\" was not injected: check your FXML file 'ComboBoxView.fxml'.";
        assert lblIndex != null : "fx:id=\"lblIndex\" was not injected: check your FXML file 'ComboBoxView.fxml'.";
        assert lblItem != null : "fx:id=\"lblItem\" was not injected: check your FXML file 'ComboBoxView.fxml'.";

        // app init
        // Way 1: populate ComboBox
        List<String> items = Arrays.asList("Laptop", "Mobile", "Mouse");
        comboItems.setItems(FXCollections.observableArrayList(items));

        // Way 2: populate ComboBox
        // String [] items = {"Laptop", "Mobile", "Mouse"};
        // comboItems.getItems().addAll(items);

        // PTR:
        // Editable option in ComboBox is used only for searching with the help of getValue() instead of using conventional dropdown button.
    }
}