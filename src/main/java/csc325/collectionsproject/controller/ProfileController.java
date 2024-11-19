package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ProfileController {

    @FXML
    private ImageView profilePicture;

    @FXML
    private Button viewPrimaryCollectionBtn;

    @FXML
    private Button createCollectionButton;

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
