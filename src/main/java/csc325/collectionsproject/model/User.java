package csc325.collectionsproject.model;

import java.util.Objects;

public class User {
    public String username;
    public String password;
    public String profilePicString;
    public String favCollectionString;


    /**
     * This is the class that stores a User's information, including their username, password, profilePicture, and favorite collection.
     * Profile Picture is set during run time and saved locally by saving the String.
     * Favorite collection is set during run time.
     * @param username The username that a User creates
     * @param password The password that a User creates
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profilePicString = "";
        this.favCollectionString = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicString() {
        return profilePicString;
    }

    public void setProfilePicString(String profilePicString) {
        this.profilePicString = profilePicString;
    }

    public String getFavCollectionString() {
        return favCollectionString;
    }

    public void setFavCollectionString(String favCollectionString) {
        this.favCollectionString = favCollectionString;
    }

    @Override
    public String toString() {
        return "Username: " + username + " Password: " + password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User u = (User) obj;
        return u.getUsername().equalsIgnoreCase(this.username) && u.getPassword().equals(this.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}