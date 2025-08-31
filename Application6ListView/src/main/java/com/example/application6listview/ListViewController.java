/**
 * Sample Skeleton for 'ListView.fxml' Controller Class
 */

package com.example.application6listview;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ListViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lblSum"
    private Label lblSum; // Value injected by FXMLLoader

    @FXML // fx:id="lstItems"
    private ListView<Integer> lstItems; // Value injected by FXMLLoader

    @FXML // fx:id="lstSelected"
    private ListView<Integer> lstSelected; // Value injected by FXMLLoader

    @FXML // fx:id="txtItemsMoved"
    private TextArea txtItemsMoved; // Value injected by FXMLLoader

    @FXML
    void doAddLstItems(ActionEvent event) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Add value");
        inputDialog.setContentText("Enter integer value to add to list");
        Optional<String> valueOpt = inputDialog.showAndWait();
        valueOpt.ifPresent(v -> {
            try {
                lstItems.getItems().add(Integer.parseInt(v));
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setContentText("Enter a valid integer eg. 58");
                alert.show();
            }
        });
    }

    @FXML
    void doDeleteLstItems(ActionEvent event) {
        ObservableList<Integer> selectedValues = lstItems.getSelectionModel().getSelectedItems();
        lstItems.getItems().removeAll(selectedValues);
        // Recall: list has remove(int index) and remove(Object value) functions which behave differently for int i and Integer i.
    }

    @FXML
    void doDeleteLstSelected(ActionEvent event) {
        ObservableList<Integer> selectedValues = lstSelected.getSelectionModel().getSelectedItems();
        lstSelected.getItems().removeAll(selectedValues);
        // Recall: list has remove(int index) and remove(Object value) functions which behave differently for int i and Integer i.
    }

    @FXML
    void doMoveItems(ActionEvent event) {
        ObservableList<Integer> selectedItems = lstItems.getSelectionModel().getSelectedItems();
        lstSelected.getItems().addAll(selectedItems); //not removing from lstItems since we want multiple instances of same number.

        // track what all items moved during a single operation
        String itemsMoved = String.join(",", selectedItems.stream().map(String::valueOf).toArray(String[]::new));
        txtItemsMoved.setText("You moved: " + itemsMoved);
    }

    @FXML
    void doSumLstSelected(ActionEvent event) {
        Optional<Integer> sumOpt = lstSelected.getItems().stream().reduce(Integer::sum); // = reduce((i1, i2) -> i1 + i2);
        lblSum.setText(sumOpt.map(String::valueOf).orElse("")); // or simply use if-else with sumOpt.isPresent()
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert lblSum != null : "fx:id=\"lblSum\" was not injected: check your FXML file 'ListView.fxml'.";
        assert lstItems != null : "fx:id=\"lstItems\" was not injected: check your FXML file 'ListView.fxml'.";
        assert lstSelected != null : "fx:id=\"lstSelected\" was not injected: check your FXML file 'ListView.fxml'.";
        assert txtItemsMoved != null : "fx:id=\"txtItemsMoved\" was not injected: check your FXML file 'ListView.fxml'.";

        // app init
        Integer [] list = new Integer[] {1, 5, 10, 50, 100};
        lstItems.getItems().addAll(list);
        lstItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstSelected.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}