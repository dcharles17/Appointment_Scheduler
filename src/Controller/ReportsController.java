package Controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import Model.Appointments;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static DAO.DBAppointments.getAllAppointments;
import static DAO.DBContacts.getContactIdByName;

/**
 * Controller for the Reports screen.
 */
public class ReportsController implements Initializable {

    @FXML private Button exitButton;
    @FXML private Button filterByMonth;
    @FXML private Button filterByContact;
    @FXML private Button filterChrono;
    @FXML private ComboBox<String> contactCB;

    @FXML
    private TableView reportsTB;
    @FXML private TableColumn<Appointments, Integer> appointmentsIdColumn;
    @FXML private TableColumn<Appointments, String> appointmentTitleColumn;
    @FXML private TableColumn<Appointments, String> appointmentDescriptionColumn ;
    @FXML private TableColumn<Appointments, String> appointmentLocationColumn;
    @FXML private TableColumn<Appointments, String> appointmentTypeColumn;
    @FXML private TableColumn<Appointments, LocalDateTime> appointmentStartTimeColumn;
    @FXML private TableColumn<Appointments, LocalDateTime> appointmentEndTimeColumn;
    @FXML private TableColumn<Appointments, Integer> appointmentCustomerIdColumn;
    @FXML private TableColumn<Appointments, Integer> appointmentUserIdColumn;
    @FXML private TableColumn<Appointments, Integer> appointmentContactIdColumn;


    /**
     * Filters the appointments table to show appointments for the current month and orders by Type.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void viewApptsMonthType(ActionEvent event) {
        int month = LocalDateTime.now().getMonthValue();
        String filter = "MONTH(start) = " + month;
        String orderBy = "Type";
        populateAppointmentsTable(filter, orderBy);
    }

    /**
     * Filters appointments table by Contact_ID
     *
     * @param event
     */
    @FXML
    void viewApptsByContact(ActionEvent event) throws SQLException {
        String apptContact = contactCB.getSelectionModel().getSelectedItem();
        if(apptContact != null){
            int apptContactId = getContactIdByName(apptContact);
            String filter = "Contact_ID = " + apptContactId;

            reportsTB.setItems(getAllAppointments(filter, ""));

        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Contact Selected.");
            alert.setContentText("Please Select a Contact.");
            alert.showAndWait();

        }
    }

    /**
     * Filters the appointments table to sort by Start.
     *
     * @param event
     */
    @FXML
    void viewApptsByStart(ActionEvent event){
        reportsTB.setItems(getAllAppointments("", "Start"));
    }

    /**
     * Populates the appointments table based on provided filter and sorts by provided orderBy.
     * @param filter
     * @param orderBy
     */
    private void populateAppointmentsTable(String filter,  String orderBy) {
        DBAppointments appointmentsDB = new DBAppointments();
        //String sqlFilter = "'" + filter + "'";
        reportsTB.setItems(getAllAppointments(filter, orderBy));
    }

    /**
     * Changes scene to the Appointments screen.
     * @param event
     * @throws IOException
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
     * Initilizes the Reports screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportsTB.setItems(getAllAppointments("", ""));
        appointmentsIdColumn.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        appointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("apptStartTime"));
        appointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("apptEndTime"));
        appointmentCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("apptCustomerId"));
        appointmentUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("apptUserId"));
        appointmentContactIdColumn.setCellValueFactory(new PropertyValueFactory<>("apptContactId"));

        ObservableList<Contacts> contactsList = DBContacts.getAllContacts();
        ObservableList<String> contactNamesList = FXCollections.observableArrayList();

            for(Contacts contact : contactsList){
                contactNamesList.add(contact.getContactName());
            }
            contactCB.setItems(contactNamesList);
        }


}
