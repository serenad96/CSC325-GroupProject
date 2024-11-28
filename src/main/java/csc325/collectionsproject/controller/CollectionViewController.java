package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class CollectionViewController {
    @FXML
    private Button addItemBtn, newCollectionBtn,profileBtn, addItemInGridBtn;

    @FXML
    private GridPane itemGrid;

    private int row = 0;
    private int column = 0;




    @FXML
    void addNewItem(ActionEvent event) throws IOException {
        //addItem(String imageUrl, String labelText); commented out while adding component logic is incomplete
        //dont forget to remove gridlines from gridpane
        switchToAddItemView();
    }

    @FXML
    public void switchToAddItemView() throws IOException {
        CollectionsApplication.setRoot("add-item-view");
    }

    @FXML
    public void switchToProfileView() throws IOException {
        CollectionsApplication.setRoot("profile-view");
    }

    @FXML
    public void switchToCreateCollectionView() throws IOException {
        CollectionsApplication.setRoot("create-collection-view");
    }
    public void addItem(String imageUrl, String labelText) {
        try {
            // Load the FXML for the item component
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemComponent.fxml"));
            Node itemNode = loader.load();

            // Get the controller of the item component
            ItemComponentController newItem = loader.getController();
            newItem.setImage(imageUrl);
            newItem.setLabel(labelText);

            // Add the item to the grid at the next available position
            itemGrid.add(itemNode, column, row);
            //Remove and add addItemInGridBtn immediately to next position
            //needs validation to be added between different rows in the case that the item is added to the last column in the grid

            // Update the grid position
            column++;
            if (column >= 5) { // 5 items per row
                column = 0;
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
