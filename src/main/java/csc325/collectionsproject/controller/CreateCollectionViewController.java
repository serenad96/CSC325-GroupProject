package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import csc325.collectionsproject.controller.FirebaseWriter;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class CreateCollectionViewController {

    @FXML
    private ImageView collectionImage, profilePic;

    @FXML
    private Button addImgBtn, backBtn, createCollectionBtn, profileBtn;

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

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser imgChooser = new FileChooser();
        imgChooser.setTitle("Choose an Item Image");
        imgChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"));
        File file = imgChooser.showOpenDialog(collectionImage.getScene().getWindow());
        if (file != null) {
            collectionImage.setImage(new Image(file.toURI().toString()));
            //String filePath = "src/main/resources/csc325/collectionsproject/imgs";
        } else {
            System.out.println("Gay baby error");
        }

    }
}
