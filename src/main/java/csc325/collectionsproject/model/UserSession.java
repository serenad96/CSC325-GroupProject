package csc325.collectionsproject.model;

public class UserSession {
    // Singleton instance
    private static UserSession instance;
    // Variable to store the logged-in user's information
    private User loggedInUser;

    // Private constructor (to prevent instantiation before registration/login events occur)
    private UserSession() {}

    /**
     * Initializes a singleton instance for locally storing an active User's information.
     * This is to circumvent querying Firebase whenever a User's information is needed.
     * @return The instance that holds a logged in User, which is set upon successful login from
     * RegistrationController.
     */
    // Method to get the singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Get logged in User
     * @return Returns the actively logged-in User
     */
    // Getter and Setter for user
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Sets the logged-in User after successful User Login in RegistrationController.
     * @param user This is the active User who has logged in to the program. The program will display this User's Collection
     *             and Item info, and allow them to create and delete Collections and Items.
     */
    //Might need validation to ensure no user is overwritten at any weird point,
    //maybe during searching and filtering debug
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    /**
     * Overridden toString() method for debugging purposes, to display the active User.
     * @return The string displayed the logged-in User's information.
     */
    @Override
    public String toString() {
        return "UserSession{" +
                "loggedInUser=" + loggedInUser +
                '}';
    }
}