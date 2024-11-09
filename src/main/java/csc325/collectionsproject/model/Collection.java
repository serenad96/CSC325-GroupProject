package csc325.collectionsproject.model;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    public String collectionTitle;
    public String collectionDescription;
    public List<String> tags;
    public int rating;
    //make all ratings an eNum

    public Collection(String collectionTitle, List<String> tags) {
        this.collectionTitle = collectionTitle;
        this.tags = new ArrayList<String>();
    }

    public Collection() {

    }

    public String getCollectionTitle() {
        return collectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    public List<String> getTags() { return tags; }

    public void setTags(List<String> tags) { this.tags = tags; }

/*

Tags
Other important fields
Rating, etc.

 */
}
