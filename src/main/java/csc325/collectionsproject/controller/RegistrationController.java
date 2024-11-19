package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegistrationController {

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField passwordTF;

  //  FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference reference = database.getReference("server/Users/aubs");


    @FXML
    void loginClicked(ActionEvent event) throws IOException {
      System.out.println("Login clicked");

      if(usernameTF.getText().equals("1") && passwordTF.getText().equals("1")) {
          switchToProfileView();
          System.out.println("Login Successful");
      }

    }

    @FXML
    void writeBtnClicked(ActionEvent event) {
        System.out.println("Write clicked");
    }
    @FXML
    void registerButtonClicked(ActionEvent event) {
        registerUser();
    }

    public void registerUser() {
        //This code adds a user for authentication
        /* UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("selin@firebase.com")
                .setEmailVerified(false)
                .setPassword("secret")
                .setPhoneNumber("+11234567890")
                .setDisplayName("John Doe")
                .setDisabled(false);
        */

         String username = usernameTF.getText().trim();
         String password = passwordTF.getText().trim();

        //collection("Users").doc(username).get();

       // if (username != ) {
            User user = new User(username, password);

            DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username);

            Map<String, Object> data = new HashMap<>();
            data.put("Username", user.getUsername());
            data.put("Password", user.getPassword());

            ApiFuture<WriteResult> result = docRef.set(data);

        //}

        //random UUID generate code
        // UUID.randomUUID();
        // IGNORE unless u r aubrey

        /* UserRecord userRecord;
        try {
            userRecord = CollectionsApplication.fauth.createUser(request);
            System.out.println("Successfully created new user with Firebase Uid: " + userRecord.getUid()
                    + " check Firebase > Authentication > Users tab");
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating a new user in the firebase");
            return false;
        } */
    }

    @FXML
    public void switchToProfileView() throws IOException {
        CollectionsApplication.setRoot("profile-view");
    }

}
