package csc325.collectionsproject.model;

import javafx.scene.image.ImageView;
import java.util.Objects;

public class User {
    public String username;
    public String password;
    public String profilePicString;

    //profile picture
    public Collection collection; //should be showcase

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profilePicString = "";
        this.collection = new Collection("Test Collection","TestDescription");
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

    public Collection getCollection() { return collection; }

    public void setCollection(Collection collection) { this.collection = collection; }

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