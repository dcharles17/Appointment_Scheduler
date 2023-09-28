package DAO;

import Model.Countries;
import Model.Customers;
import Model.FirstLevelDivisions;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class provides data access methods for working with customers in the database.
 */
public class DBCustomers {

    /**
     * Retrieves all customers from the database.
     *
     * @return an ObservableList of Customers containing all customers in the database
     */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> customerlist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int customerDivisionId = rs.getInt("Division_ID");
                String division = DBFirstLevelDivisions.getDivisionNameById(customerDivisionId);

                Customers C = new Customers(customerId, customerName, customerAddress, customerPostalCode, customerPhone, customerDivisionId, division);

                customerlist.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerlist;
    }

    /**
     * Retrieves all customer names from the database.
     *
     * @return an ObservableList of Strings containing all customer names in the database
     */
    public static ObservableList<String> getAllCustomerNames() {
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Customer_Name FROM customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                customerNames.add(customerName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerNames;
    }

    /**
     * Deletes a selected customer from the database.
     *
     * @param customerId the ID of the customer to be deleted
     * @throws SQLException if a database access error occurs
     */
    public static void deleteSelectedCustomer(int customerId) throws SQLException {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Inserts a new customer into the database.
     *
     * @param newCustomer the new customer to be inserted
     * @throws SQLException if a database access error occurs
     */
    public static void insertNewCustomer(Customers newCustomer) throws SQLException {
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, newCustomer.getCustomerName());
            ps.setString(2, newCustomer.getCustomerAddress());
            ps.setString(3, newCustomer.getCustomerPostalCode());
            ps.setString(4, newCustomer.getCustomerPhone());
            ps.setInt(5, newCustomer.getDivisionId());

            int rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates an existing customer in the database.
     *
     * @param newCustomer the updated customer
     * @throws SQLException if a database access error occurs
     */
    public static void updateNewAppointment(Customers newCustomer) throws SQLException {
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, newCustomer.getCustomerName());
            ps.setString(2, newCustomer.getCustomerAddress());
            ps.setString(3, newCustomer.getCustomerPostalCode());
            ps.setString(4, newCustomer.getCustomerPhone());
            ps.setInt(5, newCustomer.getDivisionId());
            ps.setInt(6, newCustomer.getCustomerId());

            int rowsAffected = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getCustomerNameById(int customerId) throws SQLException{
        String sql = "SELECT Customer_Name FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerId);

        ResultSet rs = ps.executeQuery();

        String customerName = null;
        while(rs.next()){
            customerName = rs.getString("Customer_Name");
        }

        return  customerName;
    }

    public static int getCustomerIdByName(String customerName) throws SQLException{
        String sql = "SELECT Customer_ID FROM customers WHERE Customer_Name = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);

        ResultSet rs = ps.executeQuery();

        int customerId = 0;
        while(rs.next()){
            customerId = rs.getInt("Customer_ID");
        }

        return  customerId;
    }

}
