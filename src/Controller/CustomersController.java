package Controller;

import DAO.DBAppointments;
import DAO.DBCustomers;
import Model.Customers;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static DAO.DBAppointments.getAllAppointments;
import static DAO.DBCustomers.getAllCustomers;

/**
 * Controller class for the Customers screen.
 */
public class CustomersController implements Initializable {

    @FXML
    private Button exitButton;
    @FXML
    private Button newCustomerButton;
    @FXML
    private Button editCustomerButton;
    @FXML
    private Button deleteCustomerButton;

    @FXML private TableView<Customers> customersTableView;
    @FXML private TableColumn<Customers, Integer> customerIdColumn;
    @FXML private TableColumn<Customers, Integer> customerNameColumn;
    @FXML private TableColumn<Customers, String> customerAddressColumn;
    @FXML private TableColumn<Customers, String> customerPostalColumn;
    @FXML private TableColumn<Customers, String> customerPhoneColumn;
    @FXML private TableColumn<Customers, String> customerDivisionColumn;
    //@FXML private TableColumn<Customers, String> customerCountryColumn;

    public static Customers selectedCustomer;

    /**
     * Changes the scene to the appointments screen.
     *
     * @param event the action event triggered by the user
     * @throws IOException if an I/O error occurs while loading the login screen
     */
    @FXML
    public void changeSceneToHomeScreen(ActionEvent event) throws IOException
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/View/AppointmentsScreen.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    /**
     * Changes the scene to the new customer screen.
     *
     * @param event the action event triggered by the user
     * @throws IOException if an I/O error occurs while loading the login screen
     */
    @FXML
    public void changeSceneToNewCustomer(ActionEvent event) throws IOException
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/View/NewCustomerScreen.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    /**
     * Changes the scene to the edit customer screen.
     *
     * @param event the action event triggered by the user
     * @throws IOException if an I/O error occurs while loading the login screen
     */
    @FXML
    public void changeSceneToEditCustomer(ActionEvent event) throws IOException {
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Optional<ButtonType> confirmModification = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Edit the selected Customer?").showAndWait();

            if (confirmModification.isPresent() && confirmModification.get() == ButtonType.OK) {
                Parent loginParent = FXMLLoader.load(getClass().getResource("/View/EditCustomerScreen.fxml"));
                Scene loginScene = new Scene(loginParent);

                Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                window.setScene(loginScene);
                window.show();
            } else{
                Optional<ButtonType> missingSelection = new Alert(Alert.AlertType.ERROR, "Please Select a Customers.").showAndWait();
            }
        }
    }

    /**
     * Deletes the selected customer from the customers table.
     * Displays confirmation pop-up before proceeding.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML public void deleteSelectedCustomer(ActionEvent event) throws IOException, SQLException {
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null){
            Optional<ButtonType> confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete the selected customer?").showAndWait();

            if(confirmDeletion.isPresent() && confirmDeletion.get() == ButtonType.OK){
                DBAppointments.deleteAppointmentByCustomer(selectedCustomer.getCustomerId());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointments Cancelled");
                alert.setContentText("You cancelled all appointments for  " + selectedCustomer.getCustomerName());
                alert.showAndWait();

                DBCustomers.deleteSelectedCustomer(selectedCustomer.getCustomerId());
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Customer Deleted");
                alert2.setContentText(selectedCustomer.getCustomerName() + " has been deleted.");
                alert2.showAndWait();

                 customersTableView.setItems(getAllCustomers());
            }
        }else {
            Optional<ButtonType> missingSelection = new Alert(Alert.AlertType.ERROR, "Please select an appointment, then try again.").showAndWait();
        }



    }

    /**
     * Initializes Customers Screen.
     * Populates customers table.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBCustomers cDAO = new DBCustomers();

        customersTableView.setItems(getAllCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        //customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));


    }
}
