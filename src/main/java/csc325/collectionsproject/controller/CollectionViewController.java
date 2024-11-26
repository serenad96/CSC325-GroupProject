package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class CollectionViewController {
    @FXML
    private Button addItemBtn, newCollectionBtn,profileBtn;

    @FXML
    private Button itemBtn;

    @FXML
    void addNewItem(ActionEvent event) throws IOException {
        switchToAddItemView();
    }

    @FXML
    public void switchToAddItemView() throws IOException {
        CollectionsApplication.setRoot("add-item-view");
    }

    @FXML
    public void switchToProfileView() throws IOException {
        CollectionsApplication.setRoot("profile-view");
    }

    @FXML
    public void switchToCreateCollectionView() throws IOException {
        CollectionsApplication.setRoot("create-collection-view");
    }

}
