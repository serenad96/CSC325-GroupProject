package csc325.collectionsproject.model;

import java.util.ArrayList;
import java.util.List;
//Could possibly be a interface which implements standard collection & showcase collection?
public class Collection {

    public String collectionTitle;
    public String collectionDescription;
    //public ArrayList<CollectionItem> collectionItems = new ArrayList<>();
   // public ArrayList<String> collectionTags;
    //public int rating;
    //make all ratings an eNum

    public Collection(String collectionTitle, String collectionDescription) {
        this.collectionTitle = collectionTitle;
        //this.collectionTags = new ArrayList<String>();
        this.collectionDescription = collectionDescription;
    }

    public Collection() {
        this.collectionTitle = "Test Collection";
        this.collectionDescription = "This is a test collection";
    }

    public String getCollectionTitle() {
        return collectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    // public List<String> getCollectionTags() { return collectionTags; }

 //   public void setCollectionTags(List<String> collectionTags) { this.collectionTags = collectionTags; }

    public String getCollectionDescription() {
        return collectionDescription;
    }

    public void setCollectionDescription(String collectionDescription) {
        this.collectionDescription = collectionDescription;
    }

  /*  public ArrayList<CollectionItem> getCollectionItems() {
        return collectionItems;
    }

    public void setCollectionItems(ArrayList<CollectionItem> collectionItems) {
        this.collectionItems = collectionItems;
    }

    public void setTags(ArrayList<String> tags) {
        this.collectionTags = tags;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    } */
/*

Tags
Other important fields
Rating, etc.
 */

}
