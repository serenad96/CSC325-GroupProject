package csc325.collectionsproject.controller;


import csc325.collectionsproject.CollectionsApplication;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class ProfileController {


private ImageView profilePicture;

    @FXML
    private Button viewCollectionBtn;

    @FXML
    void showPrimaryCollection(ActionEvent event) throws IOException {
        switchToCollectionView();
    }

    @FXML
    public void switchToCollectionView() throws IOException {
        CollectionsApplication.setRoot("collection-view");
    }
}
