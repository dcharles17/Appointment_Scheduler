package Model;

import DAO.DBFirstLevelDivisions;

import java.sql.SQLException;

/**
 * The Customers class represents a customer entity with its ID, name, address, postal code, phone number, division ID,
 * and division name information.
 */
public class Customers {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private int divisionId;
    private String divisionName;

    FirstLevelDivisions division;

    /**
     * Constructs an empty Customers object.
     */
    public Customers() {
    }

    /**
     * Constructs a Customers object with the specified customer ID, name, address, postal code, phone number, division ID,
     * and division name.
     *
     * @param customerId        the ID of the customer
     * @param customerName      the name of the customer
     * @param customerAddress   the address of the customer
     * @param customerPostalCode the postal code of the customer
     * @param customerPhone     the phone number of the customer
     * @param divisionId        the ID of the division
     * @param division          the name of the division
     */
    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionId, String division) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.divisionId = divisionId;
    }

    /**
     * Constructs a Customers object with the specified customer ID, name, address, postal code, phone number, division ID,
     * and division object.
     *
     * @param customerId        the ID of the customer
     * @param customerName      the name of the customer
     * @param customerAddress   the address of the customer
     * @param customerPostalCode the postal code of the customer
     * @param customerPhone     the phone number of the customer
     * @param divisionId        the ID of the division
     * @param division          the division object
     */
    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionId, FirstLevelDivisions division) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.divisionId = divisionId;
    }

    /**
     * Constructs a Customers object with the specified name, address, postal code, phone number, division ID,
     * and division object.
     *
     * @param customerName      the name of the customer
     * @param customerAddress   the address of the customer
     * @param customerPostalCode the postal code of the customer
     * @param customerPhone     the phone number of the customer
     * @param divisionId        the ID of the division
     * @param division          the division object
     */
    public Customers(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionId, FirstLevelDivisions division) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.divisionId = divisionId;
    }

    /**
     * Constructs a Customers object with the specified name, address, postal code, phone number, and division ID.
     *
     * @param customerName      the name of the customer
     * @param customerAddress   the address of the customer
     * @param customerPostalCode the postal code of the customer
     * @param customerPhone     the phone number of the customer
     * @param divisionId        the ID of the division

     */
    public Customers(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int divisionId) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.divisionId = divisionId;
    }

    /**
     * Retrieves the ID of the customer.
     *
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Retrieves the name of the customer.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Retrieves the address of the customer.
     *
     * @return the customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Retrieves the postal code of the customer.
     *
     * @return the customer postal code
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Retrieves the phone number of the customer.
     *
     * @return the customer phone number
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Retrieves the ID of the division.
     *
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Retrieves the name of the division.
     *
     * @return the division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets the ID of the customer.
     *
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Sets the name of the customer.
     *
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Sets the address of the customer.
     *
     * @param customerAddress the customer address to set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Sets the postal code of the customer.
     *
     * @param customerPostalCode the customer postal code to set
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param customerPhone the customer phone number to set
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * Sets the ID of the division.
     *
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Sets the name of the division.
     *
     * @param divisionName the division name to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Retrieves the division name associated with the division ID.
     *
     * @return the division name
     * @throws SQLException if an SQL exception occurs
     */
    public String getDivision() throws SQLException {
        return DBFirstLevelDivisions.getDivisionNameById(this.divisionId);
    }

    /**
     * Sets the division object.
     *
     * @param division the division object to set
     */
    public void setDivision(FirstLevelDivisions division) {
        this.division = division;
    }
}
