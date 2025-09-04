/**
 * Sample Skeleton for 'StudentRecordsView.fxml' Controller Class
 */

package com.example.application11studentrecordscrudwithcomboanddatepicker;

import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StudentRecordsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboRollNumber"
    private ComboBox<String> comboRollNumber; // Value injected by FXMLLoader

    @FXML // fx:id="dtpDateOfAdmission"
    private DatePicker dtpDateOfAdmission; // Value injected by FXMLLoader

    @FXML // fx:id="lblResult"
    private Label lblResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="txtPercentage"
    private TextField txtPercentage; // Value injected by FXMLLoader

    private Connection connection;

    private void populateComboBoxWithAvailableRollNumbers() {
        String getAllRollNumbers = "SELECT rollnumber from Students";
        try {
            PreparedStatement ps = connection.prepareStatement(getAllRollNumbers);
            ResultSet studentIdResultSet = ps.executeQuery();
            List<String> studentIdList = new ArrayList<>();
            while(studentIdResultSet.next()) {
                studentIdList.add(studentIdResultSet.getString("rollnumber"));
            }
            comboRollNumber.getItems().addAll(studentIdList);
            System.out.println("INFO: populated all rollnumbers in combo box");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean isNumber(String value, Class<? extends Number> numberImpl) { // simply Class numberImpl could have been written only to get raw parameterized warning. Better is to specify what types we're expecting
        try {
            if (numberImpl == Integer.class) {
                Integer.parseInt(value);
            } else if (numberImpl == Float.class) {
                Float.parseFloat(value);
            } else {
                throw new IllegalArgumentException("Unsupported number type: " + numberImpl);
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Value " + value + " isn't a valid " + numberImpl.getSimpleName() + ".");
            return false;
        }
    }
    private boolean isValidRollNumber(){
        // In case of editable ComboBox, the value typed can be captured using getSelectedItem() inspite of it being present or not in the ComboBox.
        // In this case the getSelectedIndex() will be -1
        if(comboRollNumber.getSelectionModel() == null ||
                !isNumber(comboRollNumber.getSelectionModel().getSelectedItem(), Integer.class)) {
            showAlert("Invalid rollnumber", "Please fill a valid number for rollnumber.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
    private boolean isValidName(){
        if(txtName.getText() == null || txtName.getText().isEmpty()) {
            showAlert("Invalid Name", "Please fill a name.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
    private boolean isValidPercentage(){
        // Default percentage = null as per table schema, but I'm still making it compulsory from UI since the query to fetch/update will need if-else check on txtPercentage.
        if(!isNumber(txtPercentage.getText(), Float.class)) {
            showAlert("Invalid percentage", "Please fill a valid percentage excluding %.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }
    private boolean isValidDate(){
        // Tip: It's best to make DatePicker non-editable, since validation like this needs to be done everytime.
        // It's better user picks from the calendar, and you can save code just by writing 1 line - Date.valueOf(dtpDateOfAdmission.getValue())
        try {
            LocalDate dateOfAdmissionLocalDate = dtpDateOfAdmission.getValue(); // LocalDate
            Date dateOfAdmission = Date.valueOf(dateOfAdmissionLocalDate);
            // For Date conversion, we could have used Date.valueOf(String),
            // but the format allowed is "yyyy-mm-dd" which dtp doesn't support dd/mm/yyyy.
            // Therefore, LocalDate conversion is the best option here.
            return true;
        } catch (NullPointerException e) {
            System.out.println("ERROR: Invalid date: " + dtpDateOfAdmission.getValue());
            showAlert("Invalid date", "Please pick a valid date or fill in format dd/mm/yyyy", Alert.AlertType.ERROR);
            return false;
        }
    }
    private void showAlert(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private
    @FXML
    void doConsoleAllStudents(ActionEvent event) {
        String getAllStudents = "SELECT * from Students";
        try {
            PreparedStatement ps = connection.prepareStatement(getAllStudents);
            ResultSet studentDetails = ps.executeQuery();
            int rowCount = 0;
            while(studentDetails.next()) {
                int rollNumber = studentDetails.getInt("rollnumber");
                String name = studentDetails.getString("name");
                float percentage = studentDetails.getFloat("percentage");
                String dateOfAdmission = studentDetails.getString("dateofadmission");
                rowCount++;
                System.out.println(String.format("%s. (%s,%s,%s,%s)", rowCount, rollNumber, name, percentage, dateOfAdmission));
            }
            if(rowCount == 0) {
                showAlert("No student found", "Database has no record of students", Alert.AlertType.INFORMATION);
                lblResult.setText("No student found");
            } else {
                lblResult.setText("Logged student details.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void doDelete(ActionEvent event) {
        if (!isValidRollNumber())
            return;
        int rollNumber = Integer.parseInt(comboRollNumber.getSelectionModel().getSelectedItem());
        Alert confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDeletion.setTitle("Confirmation");
        confirmDeletion.setContentText("Are you sure about deleting student record?");
        Optional<ButtonType> verdict = confirmDeletion.showAndWait();
        if(!verdict.isPresent() || verdict.get() != ButtonType.OK) {
            System.out.println("INFO: Backed off from deleting student record with roll number " + rollNumber);
            return;
        }
        String deleteStudent = "DELETE from Students where rollnumber = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(deleteStudent);
            ps.setInt(1, rollNumber);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected == 1) {
                System.out.println("INFO: Student record deleted with roll number " + rollNumber);
                lblResult.setText("Student record deleted");
                txtName.setText("");
                txtPercentage.setText("");
                dtpDateOfAdmission.getEditor().setText("");
            } else {
                showAlert("Delete Unsuccessful", "No student record found to delete", Alert.AlertType.INFORMATION);
                lblResult.setText("No student record found to delete");
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Couldn't delete student record " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void doFetch(ActionEvent event) {
        if(!isValidRollNumber())
            return;

        String getStudentDetails = "SELECT * from Students where rollnumber = ?";
        // Note: value typed in combobox even if not found is also treated as a selected item
        int rollNumber = Integer.parseInt(comboRollNumber.getSelectionModel().getSelectedItem());
        try {
            PreparedStatement ps = connection.prepareStatement(getStudentDetails);
            ps.setInt(1, rollNumber);
            ResultSet studentDetails = ps.executeQuery();
            int numRows = 0;
            while(studentDetails.next()) {
                String name = studentDetails.getString("name");
                float percentage = studentDetails.getFloat("percentage");
                Date dateOfAdmission = studentDetails.getDate("dateofadmission");
                numRows++;
                lblResult.setText("Student info fetched!");
                txtName.setText(name);
                txtPercentage.setText(String.valueOf(percentage));
                dtpDateOfAdmission.getEditor().setText(dateOfAdmission.toString()); // getEditor() returns TextField, so literally we can setText anything here
            }
            if (numRows == 0) {
                showAlert("Student not found", "No student found with roll number: " + rollNumber, Alert.AlertType.INFORMATION);
                lblResult.setText("Student not found");
                txtName.setText("");
                txtPercentage.setText("");
                dtpDateOfAdmission.getEditor().setText("");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void doSave(ActionEvent event) {
        // Note: value typed in ComboBox even if not found is also treated as a selected item
        if (!isValidRollNumber() || !isValidName() || !isValidPercentage())
            return;
        LocalDate dateOfAdmissionLocalDate = LocalDate.now(); // by default doa = CURRENT_DATE()
        if(!dtpDateOfAdmission.getEditor().getText().isEmpty()) {
            if(!isValidDate())
                return;
            dateOfAdmissionLocalDate = dtpDateOfAdmission.getValue();
        }

        String insertStudentDetails = "INSERT into Students (rollnumber, name, percentage, dateofadmission) values(?,?,?,?)";
        int rollNumber = Integer.parseInt(comboRollNumber.getSelectionModel().getSelectedItem());
        String name = txtName.getText();
        float percentage = Float.parseFloat(txtPercentage.getText());
        Date dateOfAdmission = Date.valueOf(dateOfAdmissionLocalDate);

        try {
            PreparedStatement ps = connection.prepareStatement(insertStudentDetails);
            ps.setInt(1, rollNumber);
            ps.setString(2, name);
            ps.setFloat(3, percentage);
            ps.setDate(4, dateOfAdmission);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println(String.format("INFO: Student record created: (%s,%s,%s,%s)", rollNumber, name, percentage, dateOfAdmission));
                lblResult.setText("Student record created!");
            } else {
                System.out.println("ERROR: Unknown: Couldn't create Student. Please check the issue.");
                lblResult.setText("Error creating record");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("WARN: Student with this roll number exists:" + rollNumber);
            lblResult.setText("Student with this roll number exists.");
        } catch (SQLException e) {
            System.out.println(String.format("ERROR: Couldn't create Student record. (%s,%s,%s,%s,%s).", rollNumber, name, percentage, dateOfAdmission, e.getMessage()));
            lblResult.setText("Error creating record");
            throw new RuntimeException(e);
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
        if (!isValidRollNumber() || !isValidName() || !isValidPercentage() || !isValidDate()) // here dateValidation is necessary as we don't want DOA=null (schema doesn't allow too)
            return;
        String updateStudentDetails = "UPDATE Students set name=?, percentage=?, dateofadmission=? where rollnumber=?";
        String name = txtName.getText();
        float percentage = Float.parseFloat(txtPercentage.getText());
        Date dateOfAdmissinon = Date.valueOf(dtpDateOfAdmission.getValue());
        int rollNumber = Integer.parseInt(comboRollNumber.getSelectionModel().getSelectedItem());
        try {
            PreparedStatement ps = connection.prepareStatement(updateStudentDetails);
            ps.setString(1, name);
            ps.setFloat(2, percentage);
            ps.setDate(3, dateOfAdmissinon);
            ps.setInt(4, rollNumber);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println(String.format("INFO: Student record updated: (%s,%s,%s,%s)", rollNumber, name, percentage, dateOfAdmissinon));
                lblResult.setText("Student record updated!");
            } else {
                System.out.println("INFO: No student with roll number " + rollNumber + " found.");
                lblResult.setText(rollNumber + " student not found to update");
            }
        } catch (SQLException e) { // no SQLIntegrityConstraintViolationException since update flow doesn't create duplicate rollNo student, but updates it.
            throw new RuntimeException(e);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert comboRollNumber != null : "fx:id=\"comboRollNumber\" was not injected: check your FXML file 'StudentRecordsView.fxml'.";
        assert dtpDateOfAdmission != null : "fx:id=\"dtpDateOfAdmission\" was not injected: check your FXML file 'StudentRecordsView.fxml'.";
        assert lblResult != null : "fx:id=\"lblResult\" was not injected: check your FXML file 'StudentRecordsView.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'StudentRecordsView.fxml'.";
        assert txtPercentage != null : "fx:id=\"txtPercentage\" was not injected: check your FXML file 'StudentRecordsView.fxml'.";

        // PTR: add driver using maven dependency
        // app and db init
        // connect to db and create table if not created
        connection = DBConnection.doConnect();
        DBConnection.createStudentsTableIfNotExists(connection);
        populateComboBoxWithAvailableRollNumbers();
    }
}

/**
 * QQ - Why index=-1 for any selection I make in the combobox containing integers, but not for strings?
 * Ans - This behavior occurs because JavaFX's ComboBox uses the equals() method to determine the selected index. 
 * When you use a ComboBox<String>, the string you type or select is compared to the items in the list using String.equals(), which works as expected.
 * However, with a ComboBox<Integer>, if you type a number into the editable ComboBox, the value is a String, not an Integer. 
 * When you call getSelectionMdel().getSelectedIndex(), JavaFX tries to find an Integer in the list that equals the typed String, which always fails, so it returns -1.
 * 
 * Summary:
 * For ComboBox<String>, the typed value matches the list items by value.
 * For ComboBox<Integer>, the typed value is a String, but the list contains Integer objects, so no match is found, and the selected index is -1.
 * 
 */