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
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static DAO.DBCountries.getCountryIdByName;
import static DAO.DBFirstLevelDivisions.getAllDivisionsByCountryId;
import static DAO.DBFirstLevelDivisions.getDivisionIdByName;

/**
 * Controlled for the New Customer screen.
 */
public class NewCustomerController implements Initializable {

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
    private ComboBox<String> firstLevelDivisonCB;

    /**
     * Changes the scene to the Customers screen.
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
     * Method to add a new customer to the customer's table.
     * Error if all fields have not been populated.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void saveNewCustomer(ActionEvent event) throws IOException, SQLException {
        if (nameTF.getText().isEmpty() || addressTF.getText().isEmpty() || postalCodeTF.getText().isEmpty() || phoneNumberTF.getText().isEmpty() || countryCB.getValue() == null || firstLevelDivisonCB.getValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Saving Customer!");
            alert.setContentText("There are missing parameters for a new customer.");
            alert.showAndWait();
        }

        String customerName = nameTF.getText();
        String customerAddress = addressTF.getText();
        String customerPostalCode = postalCodeTF.getText();
        String customerPhoneNumber = phoneNumberTF.getText();
        String customerDivision = firstLevelDivisonCB.getSelectionModel().getSelectedItem();
        int customerDivisionId = getDivisionIdByName(customerDivision);

        Customers newCustomer = new Customers(customerName, customerAddress, customerPostalCode, customerPhoneNumber, customerDivisionId);
        try{
            DBCustomers.insertNewCustomer(newCustomer);
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
     * Initializes the New Customer Screen.
     *
     * Lambda:
     * The expression replaced a for each loop that iterated over the countriesList and added each iteration to the
     * countryNamesList. The lambda expression allows for more expressive and concise code. The new lambda expression
     * is used with the forEach method. It iterates over the countriesList and adds the country name from each country
     * object to the countryNamesList.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Countries> countriesList = DBCountries.getAllCountries();
        ObservableList<String> countryNamesList = FXCollections.observableArrayList();

        countriesList.forEach(country -> countryNamesList.add(country.getCountry()));
        countryCB.setItems(countryNamesList);

        // Lambda
        countryCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

                firstLevelDivisonCB.setItems(firstLevelDivisionNames);
            }
        });
    }

}
