package Model;

/**
 * The Contacts class represents a contact entity with its ID, name, and email.
 */
public class Contacts {

    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Constructs a Contacts object with the specified contact ID, name, and email.
     *
     * @param contactId    the ID of the contact
     * @param contactName  the name of the contact
     * @param contactEmail the email of the contact
     */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Retrieves the ID of the contact.
     *
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets the ID of the contact.
     *
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Retrieves the name of the contact.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the name of the contact.
     *
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Retrieves the email of the contact.
     *
     * @return the contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the email of the contact.
     *
     * @param contactEmail the contact email to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
