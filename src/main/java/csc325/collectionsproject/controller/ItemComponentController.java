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
    void viewCollection(MouseEvent event) throws IOException {
        //Start CollectionSession
        CollectionSession session = CollectionSession.getInstance();

        //Calling Profile Controller
        ProfileController pcontroller = new ProfileController();

        //Get item name from view label
        String selectedCollectionName = itemNameLbl.getText();
                //Displays Clicked Collections Name
                System.out.println(selectedCollectionName);

        //Set Selected Collection Name
        session.setSelectedCollectionName(selectedCollectionName);

        //Switching to Collection View
        pcontroller.switchToCollectionView();
    }

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
