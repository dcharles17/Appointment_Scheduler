package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Countries class represents a country entity with its ID, name, create date, create time, last update timestamp,
 * and last update by information.
 */
public class Countries {
    private int countryId;
    private String country;
    private LocalDate createDate;
    private LocalTime createTime;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    /**
     * Constructs a Countries object with the specified country ID, name, create date, create time, last update timestamp,
     * and last update by information.
     *
     * @param countryId    the ID of the country
     * @param country      the name of the country
     * @param createDate   the create date of the country
     * @param createTime   the create time of the country
     * @param lastUpdate   the last update timestamp of the country
     * @param lastUpdateBy the last update by information of the country
     */
    public Countries(int countryId, String country, LocalDate createDate, LocalTime createTime, Timestamp lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Retrieves the ID of the country.
     *
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the ID of the country.
     *
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Retrieves the name of the country.
     *
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     *
     * @param country the country name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retrieves the create date of the country.
     *
     * @return the create date of the country
     */
    public LocalDate getCreateDate() {
        return createDate;
    }

    /**
     * Sets the create date of the country.
     *
     * @param createDate the create date to set
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves the create time of the country.
     *
     * @return the create time of the country
     */
    public LocalTime getCreateTime() {
        return createTime;
    }

    /**
     * Sets the create time of the country.
     *
     * @param createTime the create time to set
     */
    public void setCreateTime(LocalTime createTime) {
        this.createTime = createTime;
    }

    /**
     * Retrieves the last update timestamp of the country.
     *
     * @return the last update timestamp of the country
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the last update timestamp of the country.
     *
     * @param lastUpdate the last update timestamp to set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Retrieves the last update by information of the country.
     *
     * @return the last update by information of the country
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Sets the last update by information of the country.
     *
     * @param lastUpdateBy the last update by information to set
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
