package csc325.collectionsproject.model;

import javafx.scene.image.ImageView;

public class CollectionItem {

    String itemName;
    ImageView itemImage;
    String itemDescription;


    public CollectionItem(String itemName) {
        this.itemName = itemName;
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

}
