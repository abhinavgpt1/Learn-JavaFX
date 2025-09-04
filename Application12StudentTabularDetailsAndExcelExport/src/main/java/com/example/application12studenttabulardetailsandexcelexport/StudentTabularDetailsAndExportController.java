/**
 * Sample Skeleton for 'StudentTabularDetailsAndExportView.fxml' Controller Class
 */

package com.example.application12studenttabulardetailsandexcelexport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class StudentTabularDetailsAndExportController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tblStudents"
    private TableView<Student> tblStudents; // Value injected by FXMLLoader

    private Connection connection;

    private void showAlert(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private String getDefaultExcelDumpLocation() {
        String osName = System.getProperty("os.name").toLowerCase();
        String initialDir;
        if (osName.contains("win")) {
            initialDir = System.getProperty("user.home") + "\\Desktop";
        } else {
            initialDir = System.getProperty("user.home");
        }
        return initialDir;
    }
    @FXML
    void doExportCSV(ActionEvent event) {
        FileChooser excelDumpLocation = new FileChooser();
        String desktopPath = getDefaultExcelDumpLocation();
        excelDumpLocation.setInitialDirectory(new File(desktopPath));
        excelDumpLocation.setTitle("Save As");
        excelDumpLocation.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel (.csv)", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*") // not necessary to provide to user. Instead, we'll have to do validation on extension.
        );


        File excelDumpFile = excelDumpLocation.showSaveDialog(null);
        if (excelDumpFile == null) { // if user closes the Save As dialog box using cross or Cancel button.
            System.out.println("INFO: Looks like user doesn't want to save the excel yet");
            return;
        }
        String excelDumpFilePath = excelDumpFile.getAbsolutePath();
        if(!excelDumpFilePath.toLowerCase().endsWith(".csv")) {
            excelDumpFilePath = excelDumpFilePath + ".csv";
        }
        File excelFile = new File(excelDumpFilePath);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(excelFile));
            writer.write("Roll Number, Name, Percentage, Date of Admission\n"); //\n is Super IMP.
            for(Student student: tblStudents.getItems()) {
                // PTR: newline \n is important to add
                writer.write(String.format("%s,%s,%s,%s\n", student.getRollNumber(), student.getName(), student.getPercentage(), student.getDateOfAdmission()));
            }
            writer.flush(); // flush anything which isn't written yet off buffer.

            // Alert location and success
            showAlert("Downloaded", "File exported at " + excelDumpFilePath, Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            showAlert("Error", "File couldn't be downloaded", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // PTR: file can be replaced if you select an existing file
        // PTR: if that file is opened, then it can't be written, and probably you'll face exception = Caused by: java.lang.NullPointerException: Cannot invoke "java.io.BufferedWriter.close()" because "writer" is null.
    }

    @FXML
    void doFetchAll(ActionEvent event) {
        String fetchAllStudents = "SELECT * from Students";
        try {
            PreparedStatement ps = connection.prepareStatement(fetchAllStudents);
            ResultSet studentDetails = ps.executeQuery();
            int rowCount = 0;
            ObservableList<Student> studentList = FXCollections.observableArrayList();
            while(studentDetails.next()) {
                int rollNumber = studentDetails.getInt("rollnumber");
                String name = studentDetails.getString("name");
                float percentage = studentDetails.getFloat("percentage");
                String dateOfAdmission = studentDetails.getString("dateofadmission"); // or use .getDate(), and then convert to String and store in Student object.
                studentList.add(new Student(rollNumber, name, percentage, dateOfAdmission));
                rowCount++;
            }

            if(rowCount == 0) {
                showAlert("No student found", "Database has no record of students", Alert.AlertType.INFORMATION);
            } else {
                tblStudents.setItems(studentList); // does auto-cleaning of table, therefore no append in table.
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tblStudents != null : "fx:id=\"tblStudents\" was not injected: check your FXML file 'StudentTabularDetailsAndExportView.fxml'.";

        // app and db init
        connection = DBConnection.doConnect();
        DBConnection.createStudentsTableIfNotExists(connection);

        // setup table view
        TableColumn<Student, Integer> rollNumberColumn = new TableColumn<>("Roll Number");
        rollNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rollNumber")); // maps to property in Student.class

        // Found a bug in developement - naming the getter as getRollnumber and not as getRollNumber threw exception = java.lang.IllegalStateException: Cannot read from unreadable property rollNumber

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, Float> percentageColumn = new TableColumn<>("Percentage");
        percentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        TableColumn<Student, String> dateOfAdmissionColumn = new TableColumn<>("Date of Admission");
        dateOfAdmissionColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfAdmission"));

        tblStudents.getColumns().clear(); // clearing C1, C2 which are created on scene builder by default. Although it is deleted in FXML for now.
        tblStudents.getColumns().addAll(rollNumberColumn, nameColumn, percentageColumn, dateOfAdmissionColumn); // same is the order in UI.
    }
}