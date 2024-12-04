package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileController {


    @FXML
    private ImageView profilePicture;
    @FXML
    private Button viewPrimaryCollectionBtn, newCollectionBtn, settingsBtn, collectionViewBtn, viewAllButton;
    @FXML
    private AnchorPane ProfileView;
    @FXML
    private ImageView primaryCollectionImage;
    @FXML
    private Label profileNameLabel;
    @FXML
    private ImageView showcaseItem1;
    @FXML
    private ImageView showcaseItem2;
    @FXML
    private ImageView showcaseItem3;

    public void initialize() {
        UserSession session = UserSession.getInstance();
        profileNameLabel.setText("Welcome " + session.getLoggedInUser().getUsername() + "!");
    }


    @FXML
    void createNewCollection(ActionEvent event) throws IOException {
        switchToCreateCollectionView();
    }

    @FXML
    void openAllCollections(ActionEvent event) throws IOException {
        switchToCollectionView();
    }

    @FXML
    void openPrimaryCollection(ActionEvent event) throws IOException {
        switchToCollectionView();
    }

    @FXML
    public void switchToCollectionView() throws IOException {
        CollectionsApplication.setRoot("collection-view");
    }

    @FXML
    public void switchToCreateCollectionView() throws IOException {
        CollectionsApplication.setRoot("create-collection-view");
    }

    public void viewCollectionItems(ActionEvent event) throws IOException {
        switchToCollectionView();
        getCollectionName();


    }

    public String getCollectionName() {
        try {
            // Use the singleton instance to get the active username
            UserSession active = UserSession.getInstance();
            String username = active.getLoggedInUser().getUsername(); // Retrieve the username

            // Navigate to the user's "Collections" sub-collection
            CollectionReference collectionsRef = CollectionsApplication.fstoreDB.collection("Users")
                    .document(username)
                    .collection("Collections");

            // Get all documents in the "Collections" sub-collection
            ApiFuture<QuerySnapshot> future = collectionsRef.get();
            QuerySnapshot querySnapshot = future.get();

            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String collectionId = document.getId(); // Get the document ID
                System.out.println("Collection ID: " + collectionId);

                // Optionally, retrieve specific fields from the document
                String collectionTitle = document.getString("Collection Title");
                System.out.println("Collection Title: " + collectionTitle);
                return collectionTitle;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
