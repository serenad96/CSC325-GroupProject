package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;

public class AddItemController {

        @FXML
        private ImageView addItemImg;

        @FXML
        private TextField itemNameTF;

        @FXML
        private Label privateToggleLbl, publicToggleLbl, starRatingLabel, addItemLabel;

        @FXML
        private HBox privacyToggleBox, starBox;

        @FXML
        private Button addImgBtn, addNewItemBtn, backBtn, newCollectionBtn, profileBtn, collectionViewBtn;

        @FXML
        private ToggleButton itemRating1, itemRating2, itemRating3, itemRating4, itemRating5, itemPrivacyToggle;

        private ToggleGroup ratingToggleGwoup;
        int ratingValue;
        private CollectionViewController collectionController;

        @FXML
        private void initialize() {
                // Privacy label is invisible cuz default public uwu data yummy
                itemPrivacyToggle.setSelected(true);
                if (itemPrivacyToggle.isSelected()) {
                        publicToggleLbl.setVisible(true);
                        publicToggleLbl.setManaged(true);
                        privateToggleLbl.setVisible(false);
                        privateToggleLbl.setManaged(false);
                } else {
                        publicToggleLbl.setVisible(false);
                        publicToggleLbl.setManaged(false);
                        privateToggleLbl.setVisible(true);
                        privateToggleLbl.setManaged(true);
                }
                //i makea da rating pizza
                ratingToggleGwoup = new ToggleGroup();
                itemRating1.setToggleGroup(ratingToggleGwoup);
                itemRating2.setToggleGroup(ratingToggleGwoup);
                itemRating3.setToggleGroup(ratingToggleGwoup);
                itemRating4.setToggleGroup(ratingToggleGwoup);
                itemRating5.setToggleGroup(ratingToggleGwoup);


                /*DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document("Collection Title");
                addItemLabel.setText(docRef.toString());*/


        }



        //individual collection controller instance for a specific collection
        public void setCollectionController(CollectionViewController collectionController) {
                this.collectionController = collectionController;
        }

        @FXML
        void addNewItem(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
                //This is clicking the add new item button
                //This is where a item is finalized for adding to a user collection
                //CollectionItem collectionItem = new CollectionItem();
                // Gather item details
//                String imageUrl = ""; // test imageURL
//                String labelText = itemNameTF.getText();
                // Add the item to the collection
//                collectionController.addItem(imageUrl, labelText);

                //Write Item in to Firebase
                switchToCollectionView();
        }

        @FXML
        public void switchToCollectionView() throws IOException {
                CollectionsApplication.setRoot("collection-view");
        }

        public void switchToProfileView() throws IOException {
                CollectionsApplication.setRoot("profile-view");
        }

        public void switchToCreateCollectionView() throws IOException {
                CollectionsApplication.setRoot("create-collection-view");
        }

        @FXML
        void itemPrivacyToggleClicked(ActionEvent event) {
                if (itemPrivacyToggle.isSelected()) {
                        publicToggleLbl.setVisible(true);
                        publicToggleLbl.setManaged(true);
                        privateToggleLbl.setVisible(false);
                        privateToggleLbl.setManaged(false);
                } else {
                        publicToggleLbl.setVisible(false);
                        publicToggleLbl.setManaged(false);
                        privateToggleLbl.setVisible(true);
                        privateToggleLbl.setManaged(true);
                }
                System.out.println("Item Privacy Toggled");
        }

        @FXML
        void goBack(ActionEvent event) throws IOException {
                switchToCollectionView();
        }

        @FXML
        void ratingClicked(ActionEvent event) {
                // Get the selected button from the ToggleGroup
                Toggle selectedToggle = ratingToggleGwoup.getSelectedToggle();

                ToggleButton currentStarRATING;

                if (selectedToggle != null) {
                        currentStarRATING = (ToggleButton) selectedToggle;
                        System.out.println("Selected button ID: " + currentStarRATING.getId());
                        // Use a switch based on the fx:id of the selected button
                        switch (currentStarRATING.getId()) {
                                case "itemRating1":
                                        System.out.println("Star Rating ID Value: '" + currentStarRATING.getId() + "'");
                                        System.out.println("Selected Rating 1");
                                        starRatingLabel.setText("1 STAR THIS THING SUCKS");
                                        //set boolean value of item on create item, define value here
                                        ratingValue = 1;
                                        // Do stuff for Rating 1
                                        break;

                                case "itemRating2":
                                        System.out.println("Selected Rating 2");
                                        starRatingLabel.setText("2 STAR THIS THING IS FINE IG");
                                        // Do stuff for Rating 2
                                        ratingValue = 2;
                                        break;
                                case "itemRating3":
                                        System.out.println("Selected Rating 3");
                                        starRatingLabel.setText("3 STARS yeah aight");
                                        // Do stuff for Rating 3
                                        ratingValue = 3;
                                        break;
                                case "itemRating4":
                                        System.out.println("Selected Rating 4");
                                        starRatingLabel.setText("4 STARs oooo KINDA Spicy");
                                        // Do stuff for Rating 4
                                        ratingValue = 4;
                                        break;
                                case "itemRating5":
                                        System.out.println("Selected Rating 5");
                                        starRatingLabel.setText("5 STARS BABY!!!!!!!!!");
                                        // Do stuff for Rating 5
                                        ratingValue = 5;
                                        break;
                                default:
                                        System.out.println("default case");
                                        // set ratingValue to null?
                        }
                }
                else {
                System.out.println("No button is selected");
                // starRatingLabel.setText();
                }
        }



}
