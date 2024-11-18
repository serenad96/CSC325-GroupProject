package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class CollectionViewController {
    @FXML
    private Button addItemBtn;

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
}
