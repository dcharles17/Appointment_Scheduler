module com.example.c_195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;



    opens com.example.c_195 to javafx.fxml;
    opens Controller;
    exports com.example.c_195;
    exports Controller;
    exports main;
    opens main to javafx.fxml;
    opens Model to javafx.base;
}