package csc325.collectionsproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class LoginController {

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


    //All of this is on hold until Firebase is sorted

    @FXML
    void onLoginButtonClick(ActionEvent event) throws IOException {
        String username =  usernameTextField.getText();
        String password = pwTextField.getText();
        switchToCollectionView();
        //Match user login info from info retrieved from database


    }

    @FXML
    public void switchToCollectionView() throws IOException {
        CollectionsApplication.setRoot("collectionview");
    }

}
