package DAO;

import Model.Customers;
import Model.FirstLevelDivisions;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides data access methods for working with first level divisions in the database.
 */
public class DBFirstLevelDivisions {

    /**
     * Retrieves all divisions by country ID from the database.
     *
     * @param countryId the ID of the country
     * @return an ObservableList of FirstLevelDivisions containing all divisions for the specified country
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisionsByCountryId(int countryId) {
        ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions WHERE Country_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int cId = rs.getInt("Country_ID");

                FirstLevelDivisions D = new FirstLevelDivisions(divisionId, divisionName, cId);

                divisionsList.add(D);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionsList;
    }

    /**
     * Retrieves the division name by division ID from the database.
     *
     * @param divisionId the ID of the division
     * @return the name of the division
     * @throws SQLException if a database access error occurs
     */
    public static String getDivisionNameById(int divisionId) throws SQLException {

        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, divisionId);

        ResultSet rs = ps.executeQuery();

        String dName = null;
        while (rs.next()) {
            dName = rs.getString("Division");
        }
        return dName;
    }

    public static int getDivisionIdByName(String divisionName) throws  SQLException{
        String sql = "SELECT Division_ID from first_level_divisions WHERE Division = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, divisionName);

        ResultSet rs = ps.executeQuery();

        int divisionId = 0;
        while(rs.next()){
            divisionId = rs.getInt("Division_ID");
        }

        return divisionId;
    }

    public static int getCountryIdById(int divisionId) throws SQLException {
        String sql = "SELECT Country_ID from first_level_divisions WHERE Division_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, divisionId);

        ResultSet rs = ps.executeQuery();

        int countryId = 0;
        while(rs.next()) {
            countryId = rs.getInt("Country_ID");
        }

        return countryId;
    }
}
