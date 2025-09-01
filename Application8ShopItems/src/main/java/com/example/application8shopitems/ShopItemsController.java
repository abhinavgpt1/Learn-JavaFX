/**
 * Sample Skeleton for 'ShopItemsView.fxml' Controller Class
 */

package com.example.application8shopitems;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class ShopItemsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboType"
    private ComboBox<String> comboType; // Value injected by FXMLLoader

    @FXML // fx:id="lblBill"
    private Label lblBill; // Value injected by FXMLLoader

    @FXML // fx:id="lstItems"
    private ListView<String> lstItems; // Value injected by FXMLLoader

    @FXML // fx:id="lstItemsPrice"
    private ListView<Double> lstItemsPrice; // Value injected by FXMLLoader

    @FXML // fx:id="lstSelectedItems"
    private ListView<String> lstSelectedItems; // Value injected by FXMLLoader

    @FXML // fx:id="lstSelectedItemsPrice"
    private ListView<Double> lstSelectedItemsPrice; // Value injected by FXMLLoader

    @FXML // fx:id="rad10"
    private RadioButton rad10; // Value injected by FXMLLoader

    @FXML // fx:id="rad20"
    private RadioButton rad20; // Value injected by FXMLLoader

    @FXML // fx:id="radCustomDiscount"
    private RadioButton radCustomDiscount; // Value injected by FXMLLoader

    @FXML // fx:id="toggleDiscount"
    private ToggleGroup toggleDiscount; // Value injected by FXMLLoader

    @FXML // fx:id="txtCustomDiscount"
    private TextField txtCustomDiscount; // Value injected by FXMLLoader

    private final Map<String, List<Item>> DEVICE_DATA = getDeviceData(); //can use initializer block too for the initialization.
    private Map<String, List<Item>> getDeviceData() {
        Map<String, List<Item>> itemMap = new HashMap<>();
        itemMap.put("Phone", Phone.getPhoneList());
        itemMap.put("Laptop", Laptop.getLaptopList());
        return itemMap;
    }
    
    @FXML
    void doAdd(ActionEvent event) {
        // Rule for this app: single purchase of particular item

        // Objective of this function:
        // 1. Add all selected items in lstSelectedItems
        // 2. Do not repeat same item
        // 3. Alert user to not select already selected item
        ObservableList<Integer> selectedItemsIndices = lstItems.getSelectionModel().getSelectedIndices();

        // Addition to lstSelectedItems and lstSelectedItemsPrice can be done using DEVICE_DATA too, but here we're taking the long route.
        // Efficient DS for DEVICE_DATA would be map of map.
        List<String> allRepeatedItems = new ArrayList<>();
        for(Integer selectedItemIndex: selectedItemsIndices){
            String item = lstItems.getItems().get(selectedItemIndex);
            Double price = lstItemsPrice.getItems().get(selectedItemIndex);
            if (lstSelectedItems.getItems().contains(item))
                allRepeatedItems.add(item);
            else {
                lstSelectedItems.getItems().add(item);
                lstSelectedItemsPrice.getItems().add(price);
            }
        }
        if (!allRepeatedItems.isEmpty())
            showAlert("Single quantity allowed per item", "Following items are already selected: \n" + allRepeatedItems, Alert.AlertType.INFORMATION);
    }

    @FXML
    void doBill(ActionEvent event) {
        if (lstSelectedItems.getItems().size() != lstSelectedItemsPrice.getItems().size()) { // would rarely happen
            showAlert("Item-Price mismatch", "Item count doesn't match with Price count", Alert.AlertType.ERROR);
            return;
        }
        double total = lstSelectedItemsPrice.getItems().stream().mapToDouble(Double::doubleValue).sum();
        lblBill.setText("Rs. " + String.format("%.2f", total));
    }

    @FXML
    void doClearCart(ActionEvent event) {
        lstSelectedItems.getItems().clear();
        lstSelectedItemsPrice.getItems().clear();
        lblBill.setText("");
    }

    @FXML
    void doDeleteSelectedItem(ActionEvent event) {
        // Way 1 - using loop with indices. Don't simply loop and remove since the List changes dynamically meanwhile the indices being same (during multiple selection).
        List<Integer> selectedItemIndices = lstSelectedItems.getSelectionModel().getSelectedIndices().stream().collect(Collectors.toList());
        Collections.sort(selectedItemIndices);
        int elementsDeleted = 0;
        for (Integer selectedItemIndex: selectedItemIndices) {
            lstSelectedItems.getItems().remove(selectedItemIndex.intValue() - elementsDeleted); // remove by index and not by value
            lstSelectedItemsPrice.getItems().remove(selectedItemIndex.intValue() - elementsDeleted);
            elementsDeleted++;
        }
        // // Way 2 for lstSelectedItems - use removalAll()
        // lstSelectedItems.getItems().removeAll(lstSelectedItems.getSelectionModel().getSelectedItems());
        // // there's no direct way to pick the Double values in lstSelectedItemsPrice corresponding to indices selected => use same loop logic as above

        // PTR: all this logic is because lstSelectedItems is SelectionMode.MULTIPLE
    }

    @FXML
    void doPopulateTypeItems(ActionEvent event) {
        String type = comboType.getSelectionModel().getSelectedItem();
        if (!DEVICE_DATA.containsKey(type)) {
            showAlert("Limited Inventory", "Items for " + type + " not available", Alert.AlertType.INFORMATION);
            return;
        }
        lstItems.getItems().clear(); // IMP.
        lstItemsPrice.getItems().clear(); // IMP.
        for (Item i : DEVICE_DATA.get(type)){
            lstItems.getItems().add(i.getModel());
            lstItemsPrice.getItems().add(i.getPrice());
        }
    }

    void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert comboType != null : "fx:id=\"comboType\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert lblBill != null : "fx:id=\"lblBill\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert lstItems != null : "fx:id=\"lstItems\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert lstItemsPrice != null : "fx:id=\"lstItemsPrice\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert lstSelectedItems != null : "fx:id=\"lstSelectedItems\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert lstSelectedItemsPrice != null : "fx:id=\"lstSelectedItemsPrice\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert rad10 != null : "fx:id=\"rad10\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert rad20 != null : "fx:id=\"rad20\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert radCustomDiscount != null : "fx:id=\"radCustomDiscount\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert toggleDiscount != null : "fx:id=\"toggleDiscount\" was not injected: check your FXML file 'ShopItemsView.fxml'.";
        assert txtCustomDiscount != null : "fx:id=\"txtCustomDiscount\" was not injected: check your FXML file 'ShopItemsView.fxml'.";

        // app init
        lstItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // lstItemsPrice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // not needed
        lstSelectedItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // lstSelectedItemsPrice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        txtCustomDiscount.setDisable(true); // although defined in fxml, but this will turn active only when it's radio is selected
        List<String> deviceTypes = DEVICE_DATA.keySet().stream().collect(Collectors.toList());
        // Or use "var" - a reserved type name != keyword ~= auto (c++)
        // https://stackoverflow.com/questions/63073153/var-keyword-in-java
        comboType.setItems(FXCollections.observableList(deviceTypes));

    }
}