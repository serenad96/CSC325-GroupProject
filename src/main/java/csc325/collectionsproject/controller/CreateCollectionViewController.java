package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.CollectionSession;
import csc325.collectionsproject.model.ResourceManager;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CreateCollectionViewController {

    @FXML
    private ImageView collectionImage, profilePicture, toggleIcon;

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

    private static final String ICONS_PATH = "/csc325/collectionsproject/icons/";

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
            toggleIcon.setImage(ResourceManager.getImage("toggle_on.png"));
        } else {
            publicToggleLbl.setVisible(false);
            publicToggleLbl.setManaged(false);
            privateToggleLbl.setVisible(true);
            privateToggleLbl.setManaged(true);
            toggleIcon.setImage(ResourceManager.getImage("toggle_off.png"));
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
