package Controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import Model.Countries;
import Model.Customers;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controller.CustomersController.selectedCustomer;
import static DAO.DBCountries.getCountryIdByName;
import static DAO.DBCountries.getCountryNameById;
import static DAO.DBFirstLevelDivisions.*;

/**
 * Controller class for the Edit Customer screen.
 */
public class EditCustomerController implements Initializable {

    @FXML
    private Button exitButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField addressTF;
    @FXML
    private TextField postalCodeTF;
    @FXML
    private TextField phoneNumberTF;
    @FXML
    private ComboBox<String> countryCB;
    @FXML
    private ComboBox<String> firstLevelDivisionCB;

    /**
     * Changes scene to the Customer screen.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void changeSceneToCustomersScreen(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/View/CustomersScreen.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    /**
     * Method that edits/updates the selected customer based on User input.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void editCustomer(ActionEvent event) throws IOException, SQLException {
        if (nameTF.getText().isEmpty() || addressTF.getText().isEmpty() || postalCodeTF.getText().isEmpty() || phoneNumberTF.getText().isEmpty() || countryCB.getValue() == null || firstLevelDivisionCB.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Saving Customer!");
            alert.setContentText("There are missing parameters for a new customer.");
            alert.showAndWait();
        }

        int customerId = selectedCustomer.getCustomerId();
        String customerName = nameTF.getText();
        String customerAddress = addressTF.getText();
        String customerPostalCode = postalCodeTF.getText();
        String customerPhoneNumber = phoneNumberTF.getText();
        String customerDivision = firstLevelDivisionCB.getSelectionModel().getSelectedItem();
        int customerDivisionId = getDivisionIdByName(customerDivision);

        Customers newCustomer = new Customers(customerId, customerName, customerAddress, customerPostalCode, customerPhoneNumber, customerDivisionId, customerDivision);
        try{
            DBCustomers.updateNewAppointment(newCustomer);
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        Parent loginParent = FXMLLoader.load(getClass().getResource("/View/CustomersScreen.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();

    }

    /**
     * This method repopulates the firstLevelDivisonCB if the user selects a different country.
     *
     * Lambda:
     * This expression replaces a for loop and makes the code more readable.
     * This expression iterates over a list of firstLevelDivisions, gets the names of each, and adds them to a new list
     * of names.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void changeFirstLevelDivisionList(ActionEvent event) throws IOException, SQLException {
        String newValue = countryCB.getValue();
        if (newValue != null) {
            int countryId = 0;
            try {
                countryId = getCountryIdByName(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ObservableList<FirstLevelDivisions> firstLevelDivisions = getAllDivisionsByCountryId(countryId);

            // Create a list to hold the names of the first level divisions
            ObservableList<String> firstLevelDivisionNames = FXCollections.observableArrayList();
            firstLevelDivisions.forEach(division -> firstLevelDivisionNames.add(division.getDivisionName()));

            firstLevelDivisionCB.setItems(firstLevelDivisionNames);

            // Set the value explicitly
            firstLevelDivisionCB.setValue(getDivisionNameById(selectedCustomer.getDivisionId()));
        }
    }


    /**
     * Initializes the Edit Customer screen.
     *
     * 2 Lambda expressions:
     * country -> countryNamesList.add(country.getCountry())
     * and division -> firstLevelDivisionNamesList.add(division.getDivisionName())
     * These expressions replaced for loops and made the code more concise and readable.
     * Both expressions iterate over a list of objects, get the name(country name and firstleveldivision name), and add
     * them to a new list.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Countries> countriesList = DBCountries.getAllCountries();
        ObservableList<String> countryNamesList = FXCollections.observableArrayList();
        ObservableList<String> firstLevelDivisionNamesList = FXCollections.observableArrayList();

        nameTF.setText(selectedCustomer.getCustomerName());
        addressTF.setText(selectedCustomer.getCustomerAddress());
        postalCodeTF.setText(selectedCustomer.getCustomerPostalCode());
        phoneNumberTF.setText(selectedCustomer.getCustomerPhone());

        try {
            int countryId = getCountryIdById(selectedCustomer.getDivisionId());
            String countryName = getCountryNameById(countryId);
            ObservableList<FirstLevelDivisions> firstLevelDivisions = getAllDivisionsByCountryId(countryId);

            // Lambda
            countriesList.forEach(country -> countryNamesList.add(country.getCountry()));
            // Lambda
            firstLevelDivisions.forEach(division -> firstLevelDivisionNamesList.add(division.getDivisionName()));

            countryCB.setItems(countryNamesList);
            countryCB.setValue(countryName);
            firstLevelDivisionCB.setItems(firstLevelDivisionNamesList);
            firstLevelDivisionCB.setValue(getDivisionNameById(selectedCustomer.getDivisionId()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}





