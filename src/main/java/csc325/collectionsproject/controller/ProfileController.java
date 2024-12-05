package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.api.core.ApiFuture;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileController {


    @FXML
    private Button viewPrimaryCollectionBtn, newCollectionBtn, settingsBtn, collectionViewBtn, viewAllButton, addItemInGridBtn;
    @FXML
    private GridPane itemGrid;
    @FXML
    private Label profileNameLabel;
    @FXML
    private ImageView profilePicture, primaryCollectionImage, showcaseItem1, showcaseItem2, showcaseItem3;


    //GridPane indices
    private int row = 0;
    private int column = 0;


    public void initialize() {
        UserSession session = UserSession.getInstance();
        profileNameLabel.setText("Welcome " + session.getLoggedInUser().getUsername() + "!");

        //Populate all of a users collections
        List<String> collectionNames = getCollectionNames(); // Fetch names of user's collection
        for (String collectionName : collectionNames) {
            addItem("", collectionName); // Call addItem for each item
        }
    }
    public List<String> getCollectionNames() {
        List<String> collectionNames = new ArrayList<>(); // Store item names
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
                // Retrieve collection name from the document
                String collectionName = document.getString("Collection Title");
                System.out.println("Collection Title: " + collectionName);

                // if collectionName is not null, add it to the list of titles to return
                if (collectionName != null) {
                    collectionNames.add(collectionName);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return collectionNames;
    }

    @FXML
    void addCollectionToGrid(ActionEvent event) {
        addItem("", "Collection uwu!!!"); // Call addItem for each item
    }

    public void addItem(String imageUrl, String itemName) {
        try {
            // Load the FXML for the item component
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/csc325/collectionsproject/components/collection-component.fxml"));
            Node itemNode = loader.load();

            // Get the controller of the item component
            CollectionComponentController newCollectionController = loader.getController();
            newCollectionController.setImage(imageUrl != null ? imageUrl : "/csc325/collectionsproject/imgs/trilo.jpg");
            newCollectionController.setLabel(itemName);
            //newItemController.setLabel(labelText != null ? labelText : "Collection Item Here");

            // Add the item to the grid at the next available position
            itemGrid.add(itemNode, column, row);
            //if column > 5, reset, increment row, add button to next
            // Update the grid indices
            column++;
            if (column >= 5) { // 5 items per row
                column = 0;
                row++;
            }
            itemGrid.getChildren().remove(addItemInGridBtn);
            itemGrid.add(addItemInGridBtn, column, row);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
/*
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
*/
}
