package csc325.collectionsproject.model;

public class UserSession {
    // Singleton instance
    private static UserSession instance;
    // Variable to store the logged-in user's information
    private User loggedInUser;

    // Private constructor (to prevent instantiation before registration/login events occur)
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
        return loggedInUser;
    }

    //Might need validation to ensure no user is overwritten at any weird point, maybe during searching and filteringdebug
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }


}