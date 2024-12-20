package csc325.collectionsproject.controller;

import csc325.collectionsproject.model.CollectionSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CollectionComponentController {

    @FXML
    private HBox collLabelHBox;

    @FXML
    private VBox collVbox;

    @FXML
    private ImageView collectImg;

    @FXML
    private Label collectionNameLbl;

    @FXML
    private AnchorPane collectionPane;

        private static final String DEFAULT_IMAGE_PATH =
                csc325.collectionsproject.controller.CollectionComponentController.class.getResource("/csc325/collectionsproject/imgs/cats-10.png").toExternalForm();


    /**
     * @param event Mouse event linked to clicking on an individual CollectionComponent displayed in the Collection Grid on the Profile View.
     *              This is how you access an individual collection.
     * @throws IOException
     */
    @FXML
        void viewCollection(MouseEvent event) throws IOException {
            //Start CollectionSession
            CollectionSession session = CollectionSession.getInstance();

            //Calling Profile Controller
            ProfileController pcontroller = new ProfileController();

            //Get item name from view label
            String selectedCollectionName = collectionNameLbl.getText();
            //Displays Clicked Collections Name
            System.out.println(selectedCollectionName);

            //Set Selected Collection Name
            session.setSelectedCollectionName(selectedCollectionName);

            //Switching to Collection View
            pcontroller.switchToCollectionView();

        }

    /**
     * @param imageUrl sets an image for a collection, or provides a default if left blank.
     */
    public void setImage(String imageUrl) {
            //should this be a new Image()? How to get the info from collection image?
            if (imageUrl == null || imageUrl.isEmpty()) {
                // Use the default image if no URL is provided
                collectImg.setImage(new Image(DEFAULT_IMAGE_PATH));
            } else {
                collectImg.setImage(new Image(imageUrl));
            }
        }

    /**
     *
     * @param text The name of the collection that is being accessed
     */
    public void setLabel(String text) {
            collectionNameLbl.setText(text);
        }

}
