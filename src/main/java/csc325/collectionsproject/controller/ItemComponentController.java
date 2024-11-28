package csc325.collectionsproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    @FXML
    void displayItemInfo(MouseEvent event) {
        //on mouse click, display item code goes here

    }

    public void setImage(String imageUrl) {
        //should this be a new Image()? How to get the info from collection image?
        if (imageUrl == null || imageUrl.isEmpty()) {
            // Use the default image if no URL is provided
            itemImage.setImage(new Image(DEFAULT_IMAGE_PATH));
        } else {
            itemImage.setImage(new Image(imageUrl));
        }
    }

    public void setLabel(String text) {
        itemNameLbl.setText(text);
    }

}
