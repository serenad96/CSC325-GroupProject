package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.User;
import csc325.collectionsproject.model.UserSession;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class RegistrationController {

    @FXML
    private TextField usernameTF, passwordTF;
    @FXML
   private Label loginStatusLabel;

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

        if(snapshot.isEmpty()) { loginStatusLabel.setText("Incorrect Username or Password. Try again."); }

        // Iterate through document and validates login
        List<QueryDocumentSnapshot> documents = snapshot.getDocuments();
        for(QueryDocumentSnapshot document : documents ){
            Map<String, Object> data = document.getData();
            String storedUsername = (String) data.get("Username");
            String storedPassword = (String) data.get("Password");

            // Validate password
            if (storedPassword.equals(password)) {
                // If credentials are valid, switch to another view
                //user session code start
                User user = new User(storedUsername, storedPassword);
                UserSession session = UserSession.getInstance();
                UserSession.getInstance().setLoggedInUser(user);
                System.out.println("User logged in to session: " + session.getLoggedInUser());
                //user session code end
                switchToProfileView();
                System.out.println("Log-in Successful!");
            } else {
                System.out.println("Incorrect password.");
                loginStatusLabel.setText("Incorrect username or password. Try again.");
            }
        }

    }

    @FXML
    void writeBtnClicked(ActionEvent event) {
        System.out.println("Write clicked");
    }
    @FXML
    void registerButtonClicked(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
        registerUser();
    }

    public void registerUser() throws IOException, ExecutionException, InterruptedException {
        //this needs some love uwu
         String username = usernameTF.getText().trim();
         String password = passwordTF.getText().trim();

        Firestore database = FirestoreClient.getFirestore();
        CollectionReference usersCollection = database.collection("Users");
        ApiFuture<QuerySnapshot> querySnapshot = usersCollection.whereEqualTo("Username", username).get();
        QuerySnapshot snapshot = querySnapshot.get();

      //  if(snapshot.isEmpty()) { loginStatusLabel.setText("Incorrect Username or Password. Try again."); }

        // Iterate through document and validates login
        List<QueryDocumentSnapshot> documents = snapshot.getDocuments();

        for(QueryDocumentSnapshot document : documents ){
            Map<String, Object> data = document.getData();
            String storedUsername = (String) data.get("Username");
            if (username.equals(storedUsername)) {
                loginStatusLabel.setText("Username already exists.");
                //user session remove active user
                return;
            }
            else loginStatusLabel.setText("Successfully registered.");
        }

            //Registration validation passed, create username
            DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username);

            Map<String, Object> data = new HashMap<>();
            data.put("Username", username);
            data.put("Password", password);

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
