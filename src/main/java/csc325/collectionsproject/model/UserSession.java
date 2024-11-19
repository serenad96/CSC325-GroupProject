package csc325.collectionsproject.model;

public class UserSession {
    // Singleton instance
    private static UserSession instance;

    // Variable to store the logged-in user's information
    private User loggedInUser;

    // Private constructor (to prevent instantiation)
    private UserSession() {}

    // Method to get the singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Getter and Setter for user
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }


}