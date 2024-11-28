package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import csc325.collectionsproject.controller.FirebaseWriter;
import java.io.IOException;

public class CreateCollectionViewController {

    @FXML
    private ImageView addCollectionImage;

    @FXML
    private Button addImgBtn, backBtn, collectionViewBtn, createCollectionBtn, profileBtn;

    @FXML
    private TextField collectionDesc, collectionName;

    @FXML
    private ImageView logo;

    @FXML
    private ToggleButton privacyToggle;

    @FXML
    private HBox privacyToggleBox;

    @FXML
    private Label privateToggleLbl, publicToggleLbl;

    @FXML
    private ImageView profilePic;


    @FXML
    void createNewCollection(ActionEvent event) throws IOException {
        FirebaseWriter fw = new FirebaseWriter();
        fw.addCollectionToUser(collectionName.getText(), collectionDesc.getText());
        switchToCollectionView();
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        switchToProfileView();
    }

    @FXML
    void privacyToggleClicked(ActionEvent event){

    }

    @FXML
    void profilePicClicked(MouseEvent event) throws IOException {
        switchToProfileView();
    }

    @FXML
    public void switchToCollectionView() throws IOException {
        CollectionsApplication.setRoot("collection-view");
    }

    @FXML
    public void switchToProfileView() throws IOException {
        CollectionsApplication.setRoot("profile-view");
    }
}
