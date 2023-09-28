package DAO;

import Model.Contacts;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides data access methods for working with contacts in the database.
 */
public class DBContacts {

    /**
     * Retrieves all contacts from the database.
     *
     * @return an ObservableList of Contacts containing all contacts in the database
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contacts C = new Contacts(contactId, contactName, contactEmail);

                clist.add(C);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }

    public static String getContactNameById(int contactId) throws SQLException{
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, contactId);

        ResultSet rs = ps.executeQuery();

        String contactName = null;
        while(rs.next()){
            contactName = rs.getString("Contact_Name");
        }

        return contactName;
    }

    public static int getContactIdByName(String contactName) throws SQLException{
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, contactName);

        ResultSet rs = ps.executeQuery();

        int contactId = 0;
        while(rs.next()){
            contactId = rs.getInt("Contact_ID");
        }

        return contactId;
    }
}
