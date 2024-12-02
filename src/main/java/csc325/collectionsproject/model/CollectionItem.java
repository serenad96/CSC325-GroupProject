package csc325.collectionsproject.model;

import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class CollectionItem {

    public String itemName;
    public ImageView itemImage;
    public String itemDescription;
    public boolean privacySetting;
    public ArrayList<String> itemTags;
    //item rating once that is established


    public CollectionItem(String itemName, String description, boolean privacyToggle) {
        this.itemName = itemName;
        this.itemImage = new ImageView();
        this.itemDescription = description; //change later
        this.privacySetting = privacyToggle; //set this to button toggle in gui
        this.itemTags = new ArrayList<>();
    }

//Might need to change one of these constructors
    public CollectionItem(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ImageView getItemImage() { return itemImage; }

    public void setItemImage(ImageView itemImage) { this.itemImage = itemImage; }

    public String getItemDescription() { return itemDescription; }

    public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }

    public boolean isPrivacySetting() {
        return privacySetting;
    }

    public void setPrivacySetting(boolean privacySetting) {
        this.privacySetting = privacySetting;
    }

    public ArrayList<String> getItemTags() {
        return itemTags;
    }

    public void setItemTags(ArrayList<String> itemTags) {
        this.itemTags = itemTags;
    }

    @Override
    public String toString() {
        return "CollectionItem{" +
                "itemName='" + itemName + '\'' +
                '}';
    }
}
