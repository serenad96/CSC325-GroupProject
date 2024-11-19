package csc325.collectionsproject.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import csc325.collectionsproject.CollectionsApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

/*

    //Need a way to save a user's info once they log in
=======
    String user = "admin";
    String pw = "password";
    //All of this is on hold until Firebase is sorted
*/

    @FXML
    void onLoginButtonClick(ActionEvent event) throws IOException {
        String username =  usernameTextField.getText();
        String password = pwTextField.getText();
        switchToCollectionView();




        //Match user login info from info retrieved from database


    }

    @FXML
    void signInUser(ActionEvent event) throws IOException {
        //Signs user in via email matching, password does not presently work
        //Needs some more setup of user classs and what we want to store before this can
        //proceed
        String email = usernameTextField.getText();
        String password = pwTextField.getText();
        try {
            UserRecord user = CollectionsApplication.fauth.getUserByEmail(email);
            if (user != null) {
//                if (/*password check*/) {
                System.out.println("User is registered. User ID: " + user.getUid());
                //switch view to other GUI
                switchToCollectionView();
//                }
            } else {
                System.out.println("Incorrect password. Please try again.");
            }
        } catch (FirebaseAuthException ex) {
            System.out.println("User is not registered.");
            ex.printStackTrace();
        }
    }

    @FXML
    public void switchToCollectionView() throws IOException {
        CollectionsApplication.setRoot("collectionview");
    }



}
