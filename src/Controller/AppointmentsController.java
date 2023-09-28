package Controller;

import DAO.DBAppointments;
import DAO.DBCustomers;
import Model.Appointments;
import Model.Customers;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static DAO.DBAppointments.getAllAppointments;

/**
 * Controller class for the Appointments screen.
 */
public class AppointmentsController implements Initializable {

    public static Appointments selectedAppointment;
    @FXML
    private RadioButton weekRB;
    @FXML
    RadioButton monthRB;
    @FXML
    RadioButton viewAllRB;
    @FXML
    private Button exitButton;
    @FXML private Button customersButton;
    @FXML private Button reportsButton;
    @FXML private Button newAppointmentButton;
    @FXML private Button editAppointmentButton;
    @FXML private Button deleteButton;

    @FXML private TableView<Appointments> appointmentsTableView;
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


//    /**
//     * Checks for upcoming appointments within the next 15 minutes.
//     * If found, displays an alert with the appointment details.
//     * Lambda expression
//     */
//    private void checkUpcomingAppointments() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime fifteenMinutesLater = now.plusMinutes(15);
//
//        List<Appointments> existingAppointments = getAllAppointments("", "");
//
//        StringBuilder appointmentDetails = new StringBuilder();
//
//        boolean foundUpcomingAppointment = existingAppointments.stream()
//                .filter(appointment -> {
//                    LocalDateTime start = appointment.getApptStartTime();
//                    return start.isAfter(now) && start.isBefore(fifteenMinutesLater);
//                })
//                .peek(appointment -> {
//                    String appointmentId = String.valueOf(appointment.getApptId());
//                    LocalDate date = appointment.getApptStartTime().toLocalDate();
//                    LocalTime time = appointment.getApptStartTime().toLocalTime();
//
//                    appointmentDetails.append("Appointment ID: ").append(appointmentId)
//                            .append("\nDate: ").append(date)
//                            .append("\nTime: ").append(time)
//                            .append("\n\n");
//                })
//                .findFirst()
//                .isPresent();
//
//        String alertTitle = foundUpcomingAppointment ? "Upcoming Appointments" : "No Upcoming Appointments";
//        String alertHeaderText = foundUpcomingAppointment ? "There are appointments within 15 minutes!" : "There are no appointments for at least 15 minutes!";
//
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(alertTitle);
//        alert.setHeaderText(alertHeaderText);
//        alert.setContentText(appointmentDetails.toString());
//        alert.showAndWait();
//    }

    /**
     * Filters the appointments table to show appointments for the current month.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void viewApptsMonth(ActionEvent event) {
        int month = LocalDateTime.now().getMonthValue();
        String filter = "MONTH(start) = " + month;
        populateAppointmentsTable(filter);
    }

    /**
     * Filters the appointments table to show appointments for the current week.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    void viewApptsByWeek(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        String filter = "WEEK(start) = WEEK('" + formattedNow + "')";
        populateAppointmentsTable(filter);
    }

    /**
     * Populates the appointments table with appointments based on the provided filter.
     *
     * @param filter the filter condition to apply to the appointments query
     */
    private void populateAppointmentsTable(String filter) {
        DBAppointments appointmentsDB = new DBAppointments();
        //String sqlFilter = "'" + filter + "'";
        appointmentsTableView.setItems(getAllAppointments(filter, ""));
    }

    /**
     * Displays all appointments in the appointments table.
     *
     * @param event the action event triggered by the user
     */
    @FXML
    public void viewAllAppts(ActionEvent event){
        appointmentsTableView.setItems(getAllAppointments("", ""));
    }


    /**
     * Changes the scene to the login screen.
     *
     * @param event the action event triggered by the user
     * @throws IOException if an I/O error occurs while loading the login screen
     */
    @FXML
    public void changeSceneToLoginScreen(ActionEvent event) throws IOException
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    /**
     * Changes the scene to the customers screen.
     *
     * @param event the action event triggered by the user
     * @throws IOException if an I/O error occurs while loading the login screen
     */
    @FXML
    public void changeSceneToCustomers(ActionEvent event) throws IOException
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/View/CustomersScreen.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    /**
     * Changes the scene to the reports screen.
     *
     * @param event the action event triggered by the user
     * @throws IOException if an I/O error occurs while loading the login screen
     */
    @FXML void changeSceneToReports(ActionEvent event) throws IOException
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/View/ReportsScreen.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    /**
     * Changes the scene to the new appointment screen.
     *
     * @param event the action event triggered by the user
     * @throws IOException if an I/O error occurs while loading the login screen
     */
    @FXML void changeSceneToNewAppointment(ActionEvent event) throws IOException
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/View/NewAppointmentScreen.fxml"));
        Scene loginScene = new Scene(loginParent);

        Stage window = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

        window.setScene(loginScene);
        window.show();
    }

    /**
     * Changes the scene to the edit appointment screen.
     *
     * @param event the action event triggered by the user
     * @throws IOException if an I/O error occurs while loading the login screen
     */
    @FXML void changeSceneToEditAppointment(ActionEvent event) throws IOException {
        selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            Optional<ButtonType> confirmModification = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Edit the selected appointment?").showAndWait();

            if (confirmModification.isPresent() && confirmModification.get() == ButtonType.OK) {

                Parent loginParent = FXMLLoader.load(getClass().getResource("/View/EditAppointmentScreen.fxml"));
                Scene loginScene = new Scene(loginParent);

                Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                window.setScene(loginScene);
                window.show();
            } else {
                Optional<ButtonType> missingSelection = new Alert(Alert.AlertType.ERROR, "Please select an appointment, then try again.").showAndWait();
            }
        }
    }

    /**
     * Deletes the selected appointment from the appointments table.
     * Shows a confirmation pop-up to confirm the deletion before proceeding.
     *
     * @param event the action event triggered by the user
     * @throws IOException  if an I/O error occurs while showing an alert
     * @throws SQLException if an SQL error occurs while deleting the appointment
     */
    @FXML public void deleteSelectedAppointment(ActionEvent event) throws IOException, SQLException {
        selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null){
            Optional<ButtonType> confirmDeletion = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete the selected appointment?").showAndWait();

            if(confirmDeletion.isPresent() && confirmDeletion.get() == ButtonType.OK){
                DBAppointments.deleteSelectedAppointment(selectedAppointment.getApptId());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Cancelled");
                alert.setContentText("You cancelled appointment ID: " + selectedAppointment.getApptId() + " and Type: " + selectedAppointment.getApptType());
                alert.showAndWait();

                appointmentsTableView.setItems(getAllAppointments("", ""));
            }
        }else {
            Optional<ButtonType> missingSelection = new Alert(Alert.AlertType.ERROR, "Please select an appointment, then try again.").showAndWait();
        }



    }

    /**
     * Initializes the appointments screen.
     * Sets up the appointments table view and populates data from the appointments table.
     * Checks for upcoming appointments.
     *
     * @param url            the location used to resolve relative paths
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBAppointments aDAO = new DBAppointments();

        appointmentsTableView.setItems(getAllAppointments("", ""));
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

        //checkUpcomingAppointments();

    }
}
