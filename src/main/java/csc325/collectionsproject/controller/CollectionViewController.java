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
        //addItem("", "Added item!");
    }

    //Test method linked to addItemInGridBtn, change this back to addNewItem() once that part works
    @FXML
    void addNewItemTest(ActionEvent event) {
        addItem("", "Added item!");
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

    //just pass a CollectionItem here when it works, or we can get the data from the database? whats easier
    //collection view grid logic uwu happy thanksgiving
    public void addItem(String imageUrl, String itemName) {
        try {
            // Load the FXML for the item component
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/csc325/collectionsproject/components/item-component.fxml"));
            Node itemNode = loader.load();

            // Get the controller of the item component
            ItemComponentController newItemController = loader.getController();
            newItemController.setImage(imageUrl != null ? imageUrl : "/csc325/collectionsproject/imgs/pipermelonart.png");
            newItemController.setLabel(itemName);

            // Add the item to the grid at the next available position
            itemGrid.add(itemNode, column, row);
            //if column > 5, reset, increment row, add button to next

            // Update the grid indices, replace addItemInGridBtn
            column++;
            if (column >= 5) { // 5 items per row
                column = 0;
                row++;
            }
            itemGrid.getChildren().remove(addItemInGridBtn);
            itemGrid.add(addItemInGridBtn, column, row);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
