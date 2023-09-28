package Controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import Model.Appointments;
import Model.Contacts;
import Model.Customers;
import Model.Users;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.ResourceBundle;

import static Controller.AppointmentsController.selectedAppointment;
import static DAO.DBAppointments.getAllAppointments;
import static DAO.DBContacts.getContactIdByName;
import static DAO.DBContacts.getContactNameById;
import static DAO.DBCustomers.getCustomerIdByName;
import static DAO.DBCustomers.getCustomerNameById;
import static DAO.DBUsers.getUserIdByName;
import static DAO.DBUsers.getUserNameById;

/**
 * Controller class for the Edit Appointment screen.
 */
public class EditAppointmentController implements Initializable {

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
    private  ComboBox<String> userIdCB;

    private DateTimeFormatter dTFTime = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private DateTimeFormatter dTFDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);


    private ObservableList<String> apptStartTimes = FXCollections.observableArrayList();
    private ObservableList<String> apptEndTimes = FXCollections.observableArrayList();




//    private List<Appointments> getAllAppointments() {
//        return DBAppointments.getAllAppointments("");
//    }

    /**
     * Method that checks if there is a time overlap between two appointments.
     * @param appointment1
     * @param appointment2
     * @return
     */
    public static boolean isOverlap(Appointments appointment1, Appointments appointment2) {
        LocalDateTime start1 = appointment1.getApptStartTime();
        LocalDateTime end1 = appointment1.getApptEndTime();
        LocalDateTime start2 = appointment2.getApptStartTime();
        LocalDateTime end2 = appointment2.getApptEndTime();

        return (start1.isBefore(end2) && start2.isBefore(end1));
    }

    /**
     * @param dateTime
     *  Method to check if a given time is within business hours (Monday to Friday, 8 am - 5 pm)
     */
    public static boolean isWithinBusinessHours(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime timeOfDay = dateTime.toLocalTime();

        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            LocalTime businessStart = LocalTime.of(8, 0); // 8 am
            LocalTime businessEnd = LocalTime.of(17, 0); // 5 pm

            return (timeOfDay.isAfter(businessStart) || timeOfDay.equals(businessStart)) &&
                    (timeOfDay.isBefore(businessEnd) || timeOfDay.equals(businessEnd));
        }

        return false;
    }


    /**
     * Method to validate the new appointment
     * @param newAppointment
     * @return
     */
    public static boolean isAppointmentValid(Appointments newAppointment) {
        // Get all existing appointments
        List<Appointments> existingAppointments = getAllAppointments("", "");

        // Check for time overlaps
        for (Appointments existingAppointment : existingAppointments) {
            if (existingAppointment.getApptId() != (newAppointment.getApptId()) &&
                    isOverlap(existingAppointment, newAppointment)) {
                return false;
            }
        }
        // Check business hours
        if (!isWithinBusinessHours(newAppointment.getApptStartTime()) || !isWithinBusinessHours(newAppointment.getApptEndTime())) {
            return false;
        }
        if (newAppointment.getApptStartTime().isAfter(newAppointment.getApptEndTime())){
            return false;
        }

        return true;
    }

    /**
     * Changes the scene to the appointments screen.
     *
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
     * Method to edit the selected apppointment. This will update the selected appointment.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void editAppt(ActionEvent event) throws IOException, SQLException {
        if(titleTF.getText().isEmpty() || descriptionTF.getText().isEmpty() || locationTF.getText().isEmpty() || typeTF.getText().isEmpty() || startTimeCB.getValue() == null || endTimeCB.getValue() == null || datePicker.getValue() == null || customerCB.getValue() == null || contactCB.getValue() == null || userIdCB.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error saving Appointment!");
            alert.setContentText("There are missing parameters for a new appiontment.");
            alert.showAndWait();
        }
        int apptId = selectedAppointment.getApptId();
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

        Appointments newAppointment = new Appointments(apptId, apptTitle,apptDescription, apptLocation, apptType, apptStart, apptEnd, apptCustomerId, apptUserId, apptContactId);

        if (isAppointmentValid(newAppointment)) {
            try {
                DBAppointments.updateNewAppointment(newAppointment);

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
     * Initializes Edit Appointment screen.
     *
     * 3 Lambda expressions:
     * customer -> customerNamesList.add(customer.getCustomerName()), user -> userNamesList.add(user.getUserName()),
     * and contact -> contactNamesList.add(contact.getContactName())
     * Each of these expressions replaced for loops and made the code more concise and readable.
     * These expressions iterate over a list of Objects and grab the name from each object and add them to a new list.
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
        while (!lTime.equals(LocalTime.of(22, 15))) {
            LocalTime currentTime = lTime;
            apptStartTimes.add(currentTime.format(dTFTime));
            apptEndTimes.add(currentTime.format(dTFTime));
            lTime = lTime.plusMinutes(15);
        }
        apptStartTimes.remove(apptStartTimes.size() - 1);
        apptEndTimes.remove(0);

        titleTF.setText(selectedAppointment.getApptTitle());
        descriptionTF.setText(selectedAppointment.getApptDescription());
        locationTF.setText(selectedAppointment.getApptLocation());
        typeTF.setText(selectedAppointment.getApptType());
        startTimeCB.setValue(selectedAppointment.getApptStartTime().format(dTFTime));
        startTimeCB.setItems(apptStartTimes);
        endTimeCB.setValue(selectedAppointment.getApptEndTime().format(dTFTime));
        endTimeCB.setItems(apptEndTimes);
        datePicker.setValue(selectedAppointment.getApptEndTime().toLocalDate());

        try {
            String customerName = getCustomerNameById(selectedAppointment.getApptCustomerId());
            // Lambda
            customersList.forEach(customer -> customerNamesList.add(customer.getCustomerName()));
            customerCB.setItems(customerNamesList);
            customerCB.setValue(customerName);

            String userName = getUserNameById(selectedAppointment.getApptUserId());
            // Lambda
            usersList.forEach(user -> userNamesList.add(user.getUserName()));
            userIdCB.setItems(userNamesList);
            userIdCB.setValue(userName);

            String contactName = getContactNameById(selectedAppointment.getApptContactId());
            // Lambda
            contactsList.forEach(contact -> contactNamesList.add(contact.getContactName()));
            contactCB.setItems(contactNamesList);
            contactCB.setValue(contactName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
