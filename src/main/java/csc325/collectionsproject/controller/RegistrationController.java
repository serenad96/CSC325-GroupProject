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

    public RegistrationController() {

    }

    @FXML
    void loginClicked(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
      System.out.println("Login clicked");

      //how do we get a specific user's password? how to do login validation?
        //user record stuff?
         //FirebaseDatabase database = FirebaseDatabase.getInstance();
        Firestore database = FirestoreClient.getFirestore();

        //DatabaseReference reference = database.getReference("server/Users/aubs");
        CollectionReference collection = database.collection("Users");
        DocumentReference docRef = database.collection("Users").document("Username");
        ApiFuture<QuerySnapshot> querySnapshot = collection.get();

        // Prints all fields in Users Collection
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        for(QueryDocumentSnapshot document : documents ){
            System.out.println("Document ID: " + document.getId());
            System.out.println("Data: " + document.getData());
        }


        if(usernameTF.getText().equals(docRef) && passwordTF.getText().equals(docRef)) {
            System.out.println("acbdefg");
            System.out.println("Login Successful");
        }
        switchToProfileView();

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
