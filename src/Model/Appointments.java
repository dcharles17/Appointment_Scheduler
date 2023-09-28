package Model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The Appointments class represents an appointment in the application.
 */
public class Appointments {

    private Integer apptId;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private LocalDateTime apptStartTime;
    private LocalDateTime apptEndTime;
    private int apptCustomerId;
    private int apptUserId;
    private int apptContactId;

    /**
     * Constructs an instance of the Appointments class with specified values for all fields.
     *
     * @param apptId          the appointment ID
     * @param apptTitle       the appointment title
     * @param apptDescription the appointment description
     * @param apptLocation    the appointment location
     * @param apptType        the appointment type
     * @param apptStartTime   the appointment start time
     * @param apptEndTime     the appointment end time
     * @param apptCustomerId the customer ID associated with the appointment
     * @param apptUserId      the user ID associated with the appointment
     * @param apptContactId   the contact ID associated with the appointment
     */
    public Appointments(Integer apptId, String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDateTime apptStartTime, LocalDateTime apptEndTime, int apptCustomerId, int apptUserId, int apptContactId) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStartTime = apptStartTime;
        this.apptEndTime = apptEndTime;
        this.apptCustomerId = apptCustomerId;
        this.apptUserId = apptUserId;
        this.apptContactId = apptContactId;
    }

    /**
     * Constructs an instance of the Appointments class with specified values for most fields.
     * The apptId field is set to null.
     *
     * @param apptTitle       the appointment title
     * @param apptDescription the appointment description
     * @param apptLocation    the appointment location
     * @param apptType        the appointment type
     * @param apptStartTime   the appointment start time
     * @param apptEndTime     the appointment end time
     * @param apptCustomerId the customer ID associated with the appointment
     * @param apptUserId      the user ID associated with the appointment
     * @param apptContactId   the contact ID associated with the appointment
     */
    public Appointments(String apptTitle, String apptDescription, String apptLocation, String apptType, LocalDateTime apptStartTime, LocalDateTime apptEndTime, int apptCustomerId, int apptUserId, int apptContactId) {
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStartTime = apptStartTime;
        this.apptEndTime = apptEndTime;
        this.apptCustomerId = apptCustomerId;
        this.apptUserId = apptUserId;
        this.apptContactId = apptContactId;
    }

    /**
     * Retrieves the appointment ID.
     *
     * @return the appointment ID
     */
    public Integer getApptId() {
        return apptId;
    }

    /**
     * Retrieves the appointment title.
     *
     * @return the appointment title
     */
    public String getApptTitle() {
        return apptTitle;
    }

    /**
     * Retrieves the appointment description.
     *
     * @return the appointment description
     */
    public String getApptDescription() {
        return apptDescription;
    }

    /**
     * Retrieves the appointment location.
     *
     * @return the appointment location
     */
    public String getApptLocation() {
        return apptLocation;
    }

    /**
     * Retrieves the appointment type.
     *
     * @return the appointment type
     */
    public String getApptType() {
        return apptType;
    }

    /**
     * Retrieves the appointment start time.
     *
     * @return the appointment start time
     */
    public LocalDateTime getApptStartTime() {
        return apptStartTime;
    }

    /**
     * Retrieves the appointment end time.
     *
     * @return the appointment end time
     */
    public LocalDateTime getApptEndTime() {
        return apptEndTime;
    }

    /**
     * Retrieves the customer ID associated with the appointment.
     *
     * @return the customer ID
     */
    public int getApptCustomerId() {
        return apptCustomerId;
    }

    /**
     * Retrieves the user ID associated with the appointment.
     *
     * @return the user ID
     */
    public int getApptUserId() {
        return apptUserId;
    }

    /**
     * Retrieves the contact ID associated with the appointment.
     *
     * @return the contact ID
     */
    public int getApptContactId() {
        return apptContactId;
    }

    /**
     * Sets the appointment ID.
     *
     * @param apptId the appointment ID to set
     */
    public void setApptId(Integer apptId) {
        this.apptId = apptId;
    }

    /**
     * Sets the appointment title.
     *
     * @param apptTitle the appointment title to set
     */
    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /**
     * Sets the appointment description.
     *
     * @param apptDescription the appointment description to set
     */
    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    /**
     * Sets the appointment location.
     *
     * @param apptLocation the appointment location to set
     */
    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    /**
     * Sets the appointment type.
     *
     * @param apptType the appointment type to set
     */
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    /**
     * Sets the appointment start time.
     *
     * @param apptStartTime the appointment start time to set
     */
    public void setApptStartTime(LocalDateTime apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    /**
     * Sets the appointment end time.
     *
     * @param apptEndTime the appointment end time to set
     */
    public void setApptEndTime(LocalDateTime apptEndTime) {
        this.apptEndTime = apptEndTime;
    }

    /**
     * Sets the customer ID associated with the appointment.
     *
     * @param apptCustomerId the customer ID to set
     */
    public void setApptCustomerId(int apptCustomerId) {
        this.apptCustomerId = apptCustomerId;
    }

    /**
     * Sets the user ID associated with the appointment.
     *
     * @param apptUserId the user ID to set
     */
    public void setApptUserId(int apptUserId) {
        this.apptUserId = apptUserId;
    }

    /**
     * Sets the contact ID associated with the appointment.
     *
     * @param apptContactId the contact ID to set
     */
    public void setApptContactId(int apptContactId) {
        this.apptContactId = apptContactId;
    }
}
