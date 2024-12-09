package csc325.collectionsproject.model;

import java.util.ArrayList;

public class Collection {

    public String collectionTitle;
    public String collectionDescription;
    public ArrayList<CollectionItem> collectionItems = new ArrayList<>();
    public ArrayList<String> collectionTags;

    /**
     * Used in setting the active collection in CollectionSession to locally keep the actively focused Collection.
     * @param collectionTitle Title of the Collection
     * @param collectionDescription Description of the Collection
     */
    public Collection(String collectionTitle, String collectionDescription) {
        this.collectionTitle = collectionTitle;
        this.collectionDescription = collectionDescription;
        this.collectionTags = new ArrayList<String>();
        this.collectionItems = new ArrayList<>(); //coll Items needed?
    }

    public Collection(String collectionTitle, String collectionDescription, ArrayList<String> collectionTags) {
        this.collectionTitle = collectionTitle;
        this.collectionDescription = collectionDescription;
        this.collectionTags = new ArrayList<String>();
        this.collectionItems = new ArrayList<>(); //coll Items needed?
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

    public ArrayList<CollectionItem> getCollectionItems() {
        return collectionItems;
    }

    public void setCollectionItems(ArrayList<CollectionItem> collectionItems) {
        this.collectionItems = collectionItems;
    }

    /*
    //get tags

    public void setTags(ArrayList<String> tags) {
        this.collectionTags = tags;
    }

   */
}
