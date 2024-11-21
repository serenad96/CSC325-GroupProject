package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.io.IOException;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;

public class AddItemController {

        @FXML
        private ImageView addItemImg;

        @FXML
        private Label privateToggleLbl, publicToggleLbl, starRatingLabel;

        @FXML
        private HBox privacyToggleBox, starBox;

        @FXML
        private Button addImgBtn, addNewItemBtn, backBtn, newCollectionBtn, profileBtn;

        @FXML
        private ToggleButton itemRating1, itemRating2, itemRating3, itemRating4, itemRating5, itemPrivacyToggle;

        private ToggleGroup ratingToggleGwoup;

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
        }

        @FXML
        void addNewItem(ActionEvent event) throws IOException {
                switchToCollectionView();
        }

        @FXML
        public void switchToCollectionView() throws IOException {
                CollectionsApplication.setRoot("collection-view");
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
                                        System.out.println("Selected Rating 1");
                                        starRatingLabel.setText("1 STAR THIS THING SUCKS");
                                        // Do stuff for Rating 1
                                        break;

                                case "itemRating2":
                                        System.out.println("Selected Rating 2");
                                        starRatingLabel.setText("2 STAR THIS THING IS FINE IG");
                                        // Do stuff for Rating 2
                                        break;
                                case "itemRating3":
                                        System.out.println("Selected Rating 3");
                                        starRatingLabel.setText("3 STARS yeah aight");
                                        // Do stuff for Rating 3
                                        break;
                                case "itemRating4":
                                        System.out.println("Selected Rating 4");
                                        starRatingLabel.setText("4 STARs oooo KINDA Spicy");
                                        // Do stuff for Rating 4
                                        break;
                                case "itemRating5":
                                        System.out.println("Selected Rating 5");
                                        starRatingLabel.setText("5 STARS BABY!!!!!!!!!");
                                        // Do stuff for Rating 5
                                        break;
                                default:
                                        System.out.println("default case");
                        }
                }
                else {
                System.out.println("No button is selected");
                // starRatingLabel.setText();
                }
        }

}
