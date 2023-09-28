package DAO;

import Model.Users;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides data access methods for working with users in the database.
 */
public class DBUsers {

    private String userName;
    private String password;

    /**
     * Retrieves all users from the database.
     *
     * @return an ObservableList of Users containing all users in the database
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> ulist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                Users users = new Users(userId, userName, password);

                ulist.add(users);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ulist;
    }

    /**
     * Validates the username and password combination.
     *
     * @param userName the username
     * @param password the password
     * @return true if the username and password are valid, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public static boolean validateUserNamePassword(String userName, String password) throws SQLException {
        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String dbUserName = rs.getString("User_Name");
                String dbPassword = rs.getString("Password");

                if (userName.equals(dbUserName) && password.equals(dbPassword)) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables; // Rethrow the SQLException to handle it at a higher level
        }

        System.out.println("User/Password Not Found in the Database");
        return false;
    }

    public static String getUserNameById(int userId) throws SQLException{
        String sql = "SELECT User_Name FROM users WHERE User_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        String userName = null;
        while(rs.next()){
            userName = rs.getString("User_Name");
        }

        return userName;
    }

    public static int getUserIdByName(String userName) throws SQLException{
        String sql = "SELECT User_ID FROM users WHERE User_Name = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, userName);

        ResultSet rs = ps.executeQuery();

        int userId = 0;
        while(rs.next()){
            userId = rs.getInt("User_ID");
        }

        return userId;
    }

}
