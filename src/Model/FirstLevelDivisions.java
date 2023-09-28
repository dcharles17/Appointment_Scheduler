package Model;

/**
 * The FirstLevelDivisions class represents a first-level division entity with its ID, name, and country ID.
 */
public class FirstLevelDivisions {
    int divisionId;
    String divsionName;
    int countryId;

    /**
     * Constructs a FirstLevelDivisions object with the specified division ID, division name, and country ID.
     *
     * @param divisionId   the ID of the division
     * @param divisionName the name of the division
     * @param countryId    the ID of the country
     */
    public FirstLevelDivisions(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divsionName = divisionName;
        this.countryId = countryId;
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
        return divsionName;
    }

    /**
     * Retrieves the ID of the country associated with the division.
     *
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
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
     * @param divsionName the division name to set
     */
    public void setDivsionName(String divsionName) {
        this.divsionName = divsionName;
    }

    /**
     * Sets the ID of the country associated with the division.
     *
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
