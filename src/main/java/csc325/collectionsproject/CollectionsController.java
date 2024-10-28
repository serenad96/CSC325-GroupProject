package csc325.collectionsproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CollectionsController {

    @FXML
    private HBox LoginButtonBox;

    @FXML
    private HBox PwBox;

    @FXML
    private HBox UserBox;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField pwTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        String username =  usernameTextField.getText();
        String password = pwTextField.getText();

        //Match user login info from info retrieved from database


    }

}
