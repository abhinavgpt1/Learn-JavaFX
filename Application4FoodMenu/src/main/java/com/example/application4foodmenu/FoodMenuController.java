/**
 * Sample Skeleton for 'FoodMenuView.fxml' Controller Class
 */

package com.example.application4foodmenu;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FoodMenuController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="chkAll"
    private CheckBox chkAll; // Value injected by FXMLLoader

    @FXML // fx:id="chkBurger"
    private CheckBox chkBurger; // Value injected by FXMLLoader

    @FXML // fx:id="chkPizza"
    private CheckBox chkPizza; // Value injected by FXMLLoader

    @FXML // fx:id="rad10"
    private RadioButton rad10; // Value injected by FXMLLoader

    @FXML // fx:id="rad20"
    private RadioButton rad20; // Value injected by FXMLLoader

    @FXML // fx:id="toggleDiscount"
    private ToggleGroup toggleDiscount; // Value injected by FXMLLoader

    @FXML // fx:id="txtBill"
    private Label txtBill; // Value injected by FXMLLoader

    @FXML // fx:id="txtPostBillingMessage"
    private TextArea txtPostBillingMessage; // Value injected by FXMLLoader

    private final String CONGRATULATIONS_MSG = "Congratulations! You've received a discount of %s%%. You saved Rs. %s.";
    @FXML
    void doBill(ActionEvent event) {
        float bill = 0;
        if(chkBurger.isSelected()){
            bill += 50;
        }
        if(chkPizza.isSelected()) {
            bill += 100;
        }
        int discountPer = rad10.isSelected() ? 10 : rad20.isSelected() ? 20: 0;
        float discount = bill * discountPer / 100;
        bill = bill - discount;

        txtBill.setText("Rs. " + bill);
        txtPostBillingMessage.setText(String.format(CONGRATULATIONS_MSG, discountPer, discount));
    }

    @FXML
    void doChkAll(ActionEvent event) {
        chkBurger.setSelected(chkAll.isSelected()); // IMP: can't just set it to true
        chkPizza.setSelected(chkAll.isSelected());
    }

    @FXML
    void doClearDiscount(ActionEvent event) {
        // check if any radio button is selected.
        if(toggleDiscount.getToggles().stream().anyMatch(Toggle::isSelected)) { // = anyMatch(radio -> radio.isSelected())
            toggleDiscount.getSelectedToggle().setSelected(false);
        }
    }

    @FXML
    void doNew(ActionEvent event) {
        chkAll.setSelected(false);
        // PTR: passing event has no relevance. Setting chkAll as false doesn't call doChkAll
        doChkAll(event); // this function sees value set as false already, and performs clearance of all checkboxes as per chkAll().

        doClearDiscount(event); // passing null won't work. Passing event has no relevance either, but function works.

        txtBill.setText("");
        txtPostBillingMessage.setText("");
    }

    @FXML
    void doRad10(ActionEvent event) {
        // PTR:
        // This function comes in action when radio button is unselected, and we click on it.
        // Radio button calls nothing if clicked twice.
        // To make a decision on multiple clicks, use Event Listener.
        System.out.println("This function works for rad10 only when it is unselected. Don't use it for deselection.");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert chkAll != null : "fx:id=\"chkAll\" was not injected: check your FXML file 'FoodMenuView.fxml'.";
        assert chkBurger != null : "fx:id=\"chkBurger\" was not injected: check your FXML file 'FoodMenuView.fxml'.";
        assert chkPizza != null : "fx:id=\"chkPizza\" was not injected: check your FXML file 'FoodMenuView.fxml'.";
        assert rad10 != null : "fx:id=\"rad10\" was not injected: check your FXML file 'FoodMenuView.fxml'.";
        assert rad20 != null : "fx:id=\"rad20\" was not injected: check your FXML file 'FoodMenuView.fxml'.";
        assert toggleDiscount != null : "fx:id=\"toggleDiscount\" was not injected: check your FXML file 'FoodMenuView.fxml'.";
        assert txtBill != null : "fx:id=\"txtBill\" was not injected: check your FXML file 'FoodMenuView.fxml'.";
        assert txtPostBillingMessage != null : "fx:id=\"txtPostBillingMessage\" was not injected: check your FXML file 'FoodMenuView.fxml'.";

        // app init
        txtPostBillingMessage.setEditable(false); // redundant here since it's set in fxml. But it's imp to know Disable != Editable.
    }
}
