package Controller;

import DAO.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import static Controller.AppointmentsController.selectedAppointment;
import static DAO.DBContacts.getContactIdByName;
import static DAO.DBContacts.getContactNameById;
import static DAO.DBCustomers.getCustomerIdByName;
import static DAO.DBCustomers.getCustomerNameById;
import static DAO.DBUsers.getUserIdByName;
import static DAO.DBUsers.getUserNameById;

/**
 * Controller for New Appointment screen.
 */
public class NewAppointmentController implements Initializable {

    @FXML
    private Label customerLabel;
    @FXML
    private Label contactLabel;
    @FXML
    private Label userLabel;
    @FXML
    private Button exitButton;

    @FXML
    private Button saveButton;
    @FXML
    private TextField titleTF;
    @FXML
    private TextField descriptionTF;
    @FXML
    private TextField locationTF;
    @FXML
    private TextField typeTF;
    @FXML
    private ComboBox startTimeCB;
    @FXML
    private ComboBox endTimeCB;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> customerCB;
    @FXML
    private ComboBox<String> contactCB;
    @FXML
    private ComboBox<String> userIdCB;

    private DateTimeFormatter dTFTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private DateTimeFormatter dTFDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);


    private ObservableList<String> apptStartTimes = FXCollections.observableArrayList();
    private ObservableList<String> apptEndTimes = FXCollections.observableArrayList();


    /**
     * Changes the scene to the Appointments screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void changeSceneToHomeScreen(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/View/AppointmentsScreen.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    /**
     * Adds a new appointment to the appointments table. The appointment will only be added if all fields are populated.
     * This method also verifies the new appointment does not overlap existing appointments.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void saveNewAppt(ActionEvent event) throws IOException, SQLException {
        if (titleTF.getText().isEmpty() || descriptionTF.getText().isEmpty() || locationTF.getText().isEmpty() || typeTF.getText().isEmpty() || startTimeCB.getValue() == null || endTimeCB.getValue() == null || datePicker.getValue() == null || customerCB.getValue() == null || contactCB.getValue() == null || userIdCB.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error saving Appointment!");
            alert.setContentText("There are missing parameters for a new appointment.");
            alert.showAndWait();
        }
        String apptTitle = titleTF.getText();
        String apptDescription = descriptionTF.getText();
        String apptLocation = locationTF.getText();
        String apptType = typeTF.getText();
        LocalDate apptDate = datePicker.getValue();

        String startTimeString = (String) startTimeCB.getValue();
        LocalTime apptStartTime = LocalTime.parse(startTimeString, dTFTime);
        LocalDateTime apptStart = LocalDateTime.of(apptDate, apptStartTime);

        String endTimeString = (String) endTimeCB.getValue();
        LocalTime apptEndTime = LocalTime.parse(endTimeString, dTFTime);
        LocalDateTime apptEnd = LocalDateTime.of(apptDate, apptEndTime);
        String apptCustomer = customerCB.getSelectionModel().getSelectedItem();
        int apptCustomerId = getCustomerIdByName(apptCustomer);
        String apptUser = userIdCB.getSelectionModel().getSelectedItem();
        int apptUserId = getUserIdByName(apptUser);
        String apptContact = contactCB.getSelectionModel().getSelectedItem();
        int apptContactId = getContactIdByName(apptContact);

        Appointments newAppointment = new Appointments(apptTitle, apptDescription, apptLocation, apptType, apptStart, apptEnd, apptCustomerId, apptUserId, apptContactId);
        if (EditAppointmentController.isAppointmentValid(newAppointment)) {
            try {
                DBAppointments.insertNewAppointment(newAppointment);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Parent loginParent = FXMLLoader.load(getClass().getResource("/View/AppointmentsScreen.fxml"));
            Scene loginScene = new Scene(loginParent);

            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            window.setScene(loginScene);
            window.show();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error saving Appointment!");
            alert.setContentText("The new appointment overlaps with existing appointments or is outside business hours.");
            alert.showAndWait();
        }
    }


    /**
     * Initializes the New Appointment Screen.
     *
     * Lambda:
     * The expressions "customer ->...", "user ->...", and "contact ->..."  replaced the repetitive for loops. This made
     * the code more concise and readable. The 3 expressions do about the same thing... they each iterate over a list of
     * objects and grabs the name from each object, adding to a new list of names.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customers> customersList = DBCustomers.getAllCustomers();
        ObservableList<String> customerNamesList = FXCollections.observableArrayList();
        ObservableList<Users> usersList = DBUsers.getAllUsers();
        ObservableList<String> userNamesList = FXCollections.observableArrayList();
        ObservableList<Contacts> contactsList = DBContacts.getAllContacts();
        ObservableList<String> contactNamesList = FXCollections.observableArrayList();

        LocalTime lTime = LocalTime.of(8, 0);
        for (; !lTime.equals(LocalTime.of(22, 15)); lTime = lTime.plusMinutes(15)) {
            apptStartTimes.add(lTime.format(dTFTime));
            apptEndTimes.add(lTime.format(dTFTime));
        }
        apptStartTimes.remove(apptStartTimes.size() - 1);
        apptEndTimes.remove(0);

        startTimeCB.setItems(apptStartTimes);
        endTimeCB.setItems(apptEndTimes);

        // Lambda
        customersList.forEach(customer -> customerNamesList.add(customer.getCustomerName()));
        customerCB.setItems(customerNamesList);

        // Lambda
        usersList.forEach(user -> userNamesList.add(user.getUserName()));
        userIdCB.setItems(userNamesList);

        //Lambda
        contactsList.forEach(contact -> contactNamesList.add(contact.getContactName()));
        contactCB.setItems(contactNamesList);
    }

}
