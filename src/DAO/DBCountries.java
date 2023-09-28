package DAO;

import Model.Countries;
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
 * This class provides data access methods for working with countries in the database.
 */
public class DBCountries {

    /**
     * Retrieves all countries from the database.
     *
     * @return an ObservableList of Countries containing all countries in the database
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
                LocalTime createTime = rs.getTime("Create_Date").toLocalTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");

                Countries C = new Countries(countryId, countryName, createDate, createTime, lastUpdate, createdBy);

                clist.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }

    public static int getCountryIdByName(String countryName) throws SQLException {

        int countryId = 0;
        try {
            String sql = "SELECT Country_ID FROM countries WHERE Country = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                countryId = rs.getInt("Country_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countryId;
    }

    public static String getCountryNameById(int countryId) throws SQLException{
        String sql = "SELECT Country FROM countries WHERE Country_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, countryId);

        ResultSet rs = ps.executeQuery();

        String countryName = null;
        while (rs.next()){
            countryName = rs.getString("Country");
        }

        return countryName;
    }
}
