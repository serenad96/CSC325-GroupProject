package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.User;
import csc325.collectionsproject.model.UserSession;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class RegistrationController {

    @FXML
    private TextField usernameTF, passwordTF;
    @FXML
   private Label incorrectLoginLabel;

    public RegistrationController() {

    }

    @FXML
    void loginClicked(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
      System.out.println("Login clicked");
        validateLogIn();

    }

    void validateLogIn() throws ExecutionException, InterruptedException, IOException {

        String username = usernameTF.getText();
        String password = passwordTF.getText();
        //Setting up firestore
        Firestore database = FirestoreClient.getFirestore();
        CollectionReference usersCollection = database.collection("Users");
        ApiFuture<QuerySnapshot> querySnapshot = usersCollection.whereEqualTo("Username", username).get();
        QuerySnapshot snapshot = querySnapshot.get();

        if(snapshot.isEmpty()) { incorrectLoginLabel.setText("Incorrect Username or Password. Try again."); }

        // Iterate through document and validates login
        List<QueryDocumentSnapshot> documents = snapshot.getDocuments();
        for(QueryDocumentSnapshot document : documents ){
            Map<String, Object> data = document.getData();
            String storedPassword = (String) data.get("Password");

            // Validate password
            if (storedPassword.equals(password)) {
                // If credentials are valid, switch to another view
                switchToProfileView();
                System.out.println("Log-in Successful!");
            } else {
                System.out.println("Incorrect password.");
                incorrectLoginLabel.setText("Incorrect username or password. Try again.");
            }
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
        //collection("Users").doc(username).get(); (lines moved)

        // if (username != ) {
         String username = usernameTF.getText().trim();
         String password = passwordTF.getText().trim();

        //Validation needed for if user exists but do that later
            User user = new User(username, password);
            //Transfer this to be cleaner with login
            UserSession.getInstance().setLoggedInUser(user);
            System.out.println("User logged in: " + user.getUsername());

            DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username);

            Map<String, Object> data = new HashMap<>();
            data.put("Username", user.getUsername());
            data.put("Password", user.getPassword());

            ApiFuture<WriteResult> result = docRef.set(data);

        //}


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
