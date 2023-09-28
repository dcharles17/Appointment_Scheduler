package Controller;

import DAO.DBUsers;
import Model.Appointments;
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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static DAO.DBAppointments.getAllAppointments;

/**
 * Controller for the Login screen.
 */
public class LoginController implements Initializable {


        public static ZoneId userZone = ZoneId.systemDefault();
        public static Locale defaultLocale = Locale.getDefault();
        public static String userCountry = defaultLocale.getCountry();
        public static String userLanguage = defaultLocale.getDisplayLanguage();
        public boolean englishLanguage = true;
        public static String frUserName = "nom d'utilisateur";
        public static String frPassword = "mot de passe";
        public static String frLogin = "connexion";
        public static String frTitle = "Demande de planification";

        @FXML
        public Label titleLabel;
        @FXML
        public TextField userNameTextField;
        @FXML
        public TextField passwordTextField;
        @FXML
        public Label userZoneLabel;
        @FXML
        public Button loginButton;

        Stage stage;

        /**
         * Checks for upcoming appointments within the next 15 minutes.
         * If found, displays an alert with the appointment details.
         */
        private void checkUpcomingAppointments() {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime fifteenMinutesLater = now.plusMinutes(15);
                Locale defaultLocale = Locale.getDefault();
                boolean isFrenchLocale = defaultLocale.equals(Locale.FRANCE);

                List<Appointments> existingAppointments = getAllAppointments("", "");

                StringBuilder appointmentDetails = new StringBuilder();

                boolean foundUpcomingAppointment = existingAppointments.stream()
                        .filter(appointment -> {
                                LocalDateTime start = appointment.getApptStartTime();
                                return start.isAfter(now) && start.isBefore(fifteenMinutesLater);
                        })
                        .peek(appointment -> {
                                String appointmentId = String.valueOf(appointment.getApptId());
                                LocalDate date = appointment.getApptStartTime().toLocalDate();
                                LocalTime time = appointment.getApptStartTime().toLocalTime();

                                appointmentDetails.append("Appointment ID: ").append(appointmentId)
                                        .append("\nDate: ").append(date)
                                        .append("\nTime: ").append(time)
                                        .append("\n\n");
                        })
                        .findFirst()
                        .isPresent();


                String alertTitle;
                String alertHeaderText;
                ButtonType buttonTypeOk;
                if (isFrenchLocale) {
                        alertTitle = foundUpcomingAppointment ? "Rendez-vous à venir" : "Aucun rendez-vous à venir";
                        alertHeaderText = foundUpcomingAppointment ? "Il y a des rendez-vous dans les 15 minutes !" : "Il n'y a pas de rendez-vous avant au moins 15 minutes !";
                        buttonTypeOk = new ButtonType(getLocalizedButtonText(defaultLocale));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(alertTitle);
                        alert.setHeaderText(alertHeaderText);
                        alert.setContentText(appointmentDetails.toString());
                        alert.getButtonTypes().setAll(buttonTypeOk);
                        alert.showAndWait();
                } else {
                        alertTitle = foundUpcomingAppointment ? "Upcoming Appointments" : "No Upcoming Appointments";
                        alertHeaderText = foundUpcomingAppointment ? "There are appointments within 15 minutes!" : "There are no appointments for at least 15 minutes!";


                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(alertTitle);
                        alert.setHeaderText(alertHeaderText);
                        alert.setContentText(appointmentDetails.toString());
                        alert.showAndWait();
                }
        }

        /**
         * Method to get French string for the OK button.
         * @param locale
         * @return
         */
        private String getLocalizedButtonText(Locale locale){
                if (locale.getLanguage().equals("fr")){
                        return "d'accord";
                }else{
                        return "OK";
                }
        }


        /**
         * Method that checks if the user input matches the database. Either the user is taken to the Appointments
         * screen, or an error message is displayed if the input was invalid.
         *
         * @param event
         * @throws IOException
         * @throws SQLException
         */
        @FXML
        public void loginButtonAction(ActionEvent event) throws IOException, SQLException {
                String userNameInput = userNameTextField.getText();
                String passwordInput = passwordTextField.getText();

                boolean validUser = DBUsers.validateUserNamePassword(userNameInput, passwordInput);
                if (validUser) {
                        logLoginActivity("Login successful", userNameInput);
                        checkUpcomingAppointments();
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/View/AppointmentsScreen.fxml"));
                        Parent root = fxmlLoader.load();
                        stage = (Stage) loginButton.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                } else {
                        logLoginActivity("Login unsuccessful", userNameInput);
                        if (defaultLocale == Locale.FRANCE) {
                                ButtonType buttonTypeOk = new ButtonType(getLocalizedButtonText(defaultLocale));
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Identifiant invalide");
                                alert.setContentText("Authentification invalide.");
                                alert.getButtonTypes().setAll(buttonTypeOk);
                                alert.showAndWait();

                        } else {

                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Invalid Login");
                                alert.setContentText("Invalid Login Credentials.");
                                alert.showAndWait();
                        }
                }
        }

        /**
         * This method is logging the login attempts to a txt file. It will log the sucess, DateTime and timestamp for
         * each login attempt.
         *
         * @param logMessage
         * @param userName
         */
        private void logLoginActivity(String logMessage, String userName) {
                Logger logger = Logger.getLogger("LoginActivity");
                FileHandler fileHandler;
                try {
                        fileHandler = new FileHandler("login_activity.txt", true);
                        fileHandler.setFormatter(new SimpleFormatter());
                        logger.addHandler(fileHandler);
                        logger.setUseParentHandlers(false);

                        String logEntry = "[" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "] " +
                                logMessage + " - User: " + userName + "\n";
                        logger.log(Level.INFO, logEntry);

                        fileHandler.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }


        /**
         * Initializes the Login Screen
         * @param url
         * @param resourceBundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                userZoneLabel.setText(String.valueOf(userZone));
                if (defaultLocale == Locale.FRANCE) {
                        titleLabel.setText(frTitle);
                        loginButton.setText(frLogin);
                        userNameTextField.setText(frUserName);
                        passwordTextField.setText(frPassword);
                }
        }
}
