package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.CollectionSession;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ImageView collectionImage, profilePicture;

    @FXML
    private Button addImgBtn, backBtn, createCollectionBtn, profileBtn;

    @FXML
    private TextField collectionDesc, collectionName;

    @FXML
    private ToggleButton privacyToggle;

    @FXML
    private HBox privacyToggleBox;

    @FXML
    private Label privateToggleLbl, publicToggleLbl;

    @FXML
    private void initialize() {
        // Privacy label is invisible cuz default public uwu data yummy
        privacyToggle.setSelected(true);
        adjustPrivacy();

        //Set profile picture if one has been set previously
        if(!UserSession.getInstance().getLoggedInUser().getProfilePicString().isEmpty()) {
            profilePicture.setImage(new Image(UserSession.getInstance().getLoggedInUser().getProfilePicString()));
            System.out.println("Set profile pic on create collection view!");
        }


    }


    @FXML
    void createNewCollection(ActionEvent event) throws IOException {
        FirebaseWriter fw = new FirebaseWriter();
        String newCollection = collectionName.getText().trim();
        fw.addCollectionToUser(newCollection, collectionDesc.getText());
        CollectionSession session = CollectionSession.getInstance();
        session.setSelectedCollectionName(newCollection);
        switchToCollectionView();
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        switchToProfileView();
    }

    @FXML
    void privacyToggleClicked(ActionEvent event){
        adjustPrivacy();
        System.out.println("Item Privacy Toggled");
    }

    private void adjustPrivacy() {
        if (privacyToggle.isSelected()) {
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
            System.out.println("Image Upload Error");
        }

    }
}
