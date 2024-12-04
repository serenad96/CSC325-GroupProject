package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ProfileController {


    @FXML
    private Button viewPrimaryCollectionBtn, newCollectionBtn, settingsBtn, collectionViewBtn, viewAllButton, addItemInGridBtn;
    @FXML
    private GridPane itemGrid;
    @FXML
    private Label profileNameLabel;
    @FXML
    private ImageView profilePicture, primaryCollectionImage, showcaseItem1, showcaseItem2, showcaseItem3;


    //GridPane indices
    private int row = 0;
    private int column = 0;


    public void initialize() {
        UserSession session = UserSession.getInstance();
        profileNameLabel.setText("Welcome " + session.getLoggedInUser().getUsername() + "!");
    }

    @FXML
    void addCollectionToGrid(ActionEvent event) {
        addItem("", "Collection uwu!!!"); // Call addItem for each item
    }

    public void addItem(String imageUrl, String itemName) {
        try {
            // Load the FXML for the item component
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/csc325/collectionsproject/components/item-component.fxml"));
            Node itemNode = loader.load();

            // Get the controller of the item component
            ItemComponentController newItemController = loader.getController();
            newItemController.setImage(imageUrl != null ? imageUrl : "/csc325/collectionsproject/imgs/pipermelonart.png");
            newItemController.setLabel(itemName);
            //newItemController.setLabel(labelText != null ? labelText : "Collection Item Here");

            // Add the item to the grid at the next available position
            itemGrid.add(itemNode, column, row);
            //if column > 5, reset, increment row, add button to next
            // Update the grid indices
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

    @FXML
    void createNewCollection(ActionEvent event) throws IOException {
        switchToCreateCollectionView();
    }

    @FXML
    void openAllCollections(ActionEvent event) throws IOException {
        switchToCollectionView();
    }

    @FXML
    void openPrimaryCollection(ActionEvent event) throws IOException {
        switchToCollectionView();
    }

    @FXML
    public void switchToCollectionView() throws IOException {
        CollectionsApplication.setRoot("collection-view");
    }

    @FXML
    public void switchToCreateCollectionView() throws IOException {
        CollectionsApplication.setRoot("create-collection-view");
    }

}
