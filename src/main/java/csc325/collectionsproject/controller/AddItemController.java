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

public class AddItemController {

        @FXML
        private Button addImgBtn;

        @FXML
        private ImageView addItemImg;

        @FXML
        private Label privateToggleLbl;

        @FXML
        private HBox privacyToggleBox;

        @FXML
        private Label publicToggleLbl;

        @FXML
        private Button addNewItemBtn;

        @FXML
        private Button backBtn;

        @FXML
        private ToggleButton itemPrivacyToggle;

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
                System.out.println("Item Privacy Toggled");
        }

        @FXML
        void goBack(ActionEvent event) throws IOException {
                switchToCollectionView();
        }

}
