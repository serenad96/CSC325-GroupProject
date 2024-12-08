package csc325.collectionsproject.controller;

import csc325.collectionsproject.CollectionsApplication;
import javafx.fxml.FXML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ItemViewController {

    @FXML
    private Button backBtn, profileBtn, setFavItemBtn;

    @FXML
    private Label itemNameLbl, itemDescLbl, itemRatingLabel, itemTagsLbl;

    @FXML
    void setFavItem(ActionEvent event) {

    }

    @FXML
    void switchToProfileView(ActionEvent event) throws IOException {
        CollectionsApplication.setRoot("create-collection-view");
    }

}
