package Model;

/**
 * The Users class represents a user entity with its ID, username, and password.
 */
public class Users {

    private int userId;
    private String userName;
    private String password;

    /**
     * Constructs a Users object with the specified user ID, username, and password.
     *
     * @param userId   the ID of the user
     * @param userName the username of the user
     * @param password the password of the user
     */
    public Users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Retrieves the ID of the user.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the ID of the user.
     *
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the username of the user.
     *
     * @param userName the username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
