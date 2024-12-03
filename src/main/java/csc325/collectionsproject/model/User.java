package csc325.collectionsproject.model;

import java.util.List;
import java.util.Objects;

public class User {
    public String username;
    public String password;
    public String usernameKey;
    public Profile profile;
    //profile picture
    public Collection collection; //should be showcase
 //   public List collections; //undecided on data type atm

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.usernameKey = username.toLowerCase();
        this.profile = new Profile();
        // this.collections = new <Collection>List();
        this.collection = new Collection("Test Collection","TestDescription");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameKey() {
        return usernameKey;
    }

    public void setUsernameKey(String usernameKey) {
        this.usernameKey = usernameKey;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
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