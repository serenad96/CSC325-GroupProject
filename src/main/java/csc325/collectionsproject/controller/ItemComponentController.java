package csc325.collectionsproject.controller;

import csc325.collectionsproject.model.CollectionSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * This Class is used to Display Items within a Collection
 */
public class ItemComponentController {

    @FXML
    private ImageView itemImage;

    @FXML
    private HBox itemLabelHBox;

    @FXML
    private VBox itemVBox;

    @FXML
    private Label itemNameLbl;

    @FXML
    private AnchorPane itemPane;

    private static final String DEFAULT_IMAGE_PATH =
            ItemComponentController.class.getResource("/csc325/collectionsproject/imgs/pipermelonart.png").toExternalForm();

    /**
     * Populates the Item Component with the Item information
     *
     * @param mouseEvent a user clicks on the Item in Collection View
     */
    @FXML
    public void viewItem(MouseEvent mouseEvent) throws IOException {
        //Start CollectionSession
        CollectionSession sessionI = CollectionSession.getInstance();

        //Get item name from view label
        String selectedCollectionItemName = itemNameLbl.getText();
                //Displays Clicked Collections Name
                System.out.println(selectedCollectionItemName);

        //Set Selected Collection Name
        sessionI.setSelectedCollectionItemName(selectedCollectionItemName);

        //Switch to Item View
        CollectionViewController cview = new CollectionViewController();
        cview.viewItemDetails();
    }

    /**
     * Sets the image for the Item Component
     *
     * @param imageUrl URL of the image of the image being represented
     */
    public void setImage(String imageUrl) {
        //should this be a new Image()? How to get the info from collection image?
        if (imageUrl == null || imageUrl.isEmpty()) {
            // Use the default image if no URL is provided
            itemImage.setImage(new Image(DEFAULT_IMAGE_PATH));
        } else {
            itemImage.setImage(new Image(imageUrl));
        }
    }

    /**
     * Sets the text in the Item Component to the items name
     *
     * @param text Item Name
     */
    public void setLabel(String text) {
        itemNameLbl.setText(text);
    }

}
