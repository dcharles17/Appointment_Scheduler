package DAO;

import Model.Appointments;
import Model.Customers;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * This is the Data Access Object class for appointments.
 * All methods that interact with the appointments table are stored in this class.
 */
public class DBAppointments {

    /**
     * This method gets all the rows and columns from the apppointments table.
     * Returns them as an observable list.
     * Filter can be passed into the method to change results.
     * orderBy can be passed into the method to change the sorting of the results.
     * @param filter
     * @param orderBy
     * @return
     */
    public static ObservableList<Appointments> getAllAppointments(String filter, String orderBy){
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from appointments";
            if(!filter.isEmpty() && filter != null){
                sql += " WHERE " + filter;
            }

            if(!orderBy.isEmpty() && orderBy != null){
                sql += " ORDER BY " + orderBy;
            }

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int apptId = rs.getInt("Appointment_ID");
                String apptTitle = rs.getString( "Title");
                String apptDescription = rs.getString( "Description");
                String apptLocation = rs.getString("Location");
                String apptType = rs.getString("Type");
                LocalDateTime apptStartTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime apptEndTime = rs.getTimestamp("End").toLocalDateTime();
                int apptCustomerId = rs.getInt("Customer_ID");
                int apptUserId = rs.getInt("User_ID");
                int apptContactId = rs.getInt("Contact_ID");


                Appointments A = new Appointments(apptId, apptTitle, apptDescription, apptLocation, apptType, apptStartTime, apptEndTime, apptCustomerId, apptUserId, apptContactId);

                appointmentsList.add(A);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return appointmentsList;
    }

    /**
     * This method will delete a specific row from the appointment table based on the apptId passed into the method.
     * @param apptId
     * @throws SQLException
     */
    public static void deleteSelectedAppointment(int apptId) throws SQLException {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, apptId);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This method deletes a row from the appointments table based on the customerId passed into the method.
     * @param customerId
     * @throws SQLException
     */
    public static void deleteAppointmentByCustomer(int customerId) throws SQLException{
        try{
            String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This method adds a new row to the appointments table and populates all relevant columns.
     * @param newAppointment
     * @throws SQLException
     */
    public static void insertNewAppointment(Appointments newAppointment) throws SQLException {
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, newAppointment.getApptTitle());
            ps.setString(2, newAppointment.getApptDescription());
            ps.setString(3, newAppointment.getApptLocation());
            ps.setString(4, newAppointment.getApptType());
            ps.setTimestamp(5, Timestamp.valueOf(newAppointment.getApptStartTime()));
            ps.setTimestamp(6, Timestamp.valueOf(newAppointment.getApptEndTime()));
            ps.setInt(7, newAppointment.getApptCustomerId());
            ps.setInt(8, newAppointment.getApptUserId());
            ps.setInt(9, newAppointment.getApptContactId());

            int rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This method updates an existing row in the appointments table.
     * It only edits the row based on the appointment Id.
     * @param newAppointment
     * @throws SQLException
     */
    public static void updateNewAppointment(Appointments newAppointment) throws SQLException {
        try{
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, newAppointment.getApptTitle());
            ps.setString(2, newAppointment.getApptDescription());
            ps.setString(3, newAppointment.getApptLocation());
            ps.setString(4, newAppointment.getApptType());
            ps.setTimestamp(5, Timestamp.valueOf(newAppointment.getApptStartTime()));
            ps.setTimestamp(6, Timestamp.valueOf(newAppointment.getApptEndTime()));
            ps.setInt(7, newAppointment.getApptCustomerId());
            ps.setInt(8, newAppointment.getApptUserId());
            ps.setInt(9, newAppointment.getApptContactId());
            ps.setInt(10, newAppointment.getApptId());

            int rowsAffected = ps.executeUpdate();

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

}

