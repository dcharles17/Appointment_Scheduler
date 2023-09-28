package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main class serves as the entry point for the application.
 */
public class Main extends Application {

    /**
     * The start method is called when the application is launched.
     * It loads and displays the login screen.
     *
     * @param stage the primary stage for the application
     * @throws Exception if an error occurs during the start process
     */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Scheduling Application");
        stage.setScene(scene);
        stage.show();
        JDBC.openConnection();
        //JDBC.closeConnection();
    }

    /**
     * The main method is the entry point for the Java application.
     * It opens the database connection and launches the JavaFX application.
     *
     * @param stage the primary stage for the application
     * @throws IOException if an error occurs during the execution
     */
    public void main(Stage stage) throws IOException {
        JDBC.openConnection();
        //JDBC.closeConnection();
    }

    /**
     * The main method calls the launch method to start the JavaFX application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
