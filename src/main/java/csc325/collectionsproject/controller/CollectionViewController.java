package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.CollectionSession;
import csc325.collectionsproject.model.User;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CollectionViewController {
    @FXML
    private Button addItemBtn, newCollectionBtn, profileBtn;

    @FXML
    private GridPane itemGrid;

    @FXML
    private Label collectionNameLbl;

    private int row = 0;
    private int column = 0;

    String selectedCollectionName;


    @FXML
    void addNewItem(ActionEvent event) throws IOException {
        switchToAddItemView();
    }

    public void initialize() throws ExecutionException, InterruptedException {

        //Get Selected Collection Name
        List<String> itemNames = getCollectionItems(); // Fetch item names in collection
        for (String itemName : itemNames) {
            addItem("", itemName); // Call addItem for each item
        }

        //Retrieve the collectionName from the Collection Session
        CollectionSession session = CollectionSession.getInstance();
        String selectedCollection = session.getSelectedCollectionName();

        collectionNameLbl.setText(selectedCollection);
        System.out.println("Active collection in writer " + selectedCollection);

    }

    public List<String> getCollectionItems() throws ExecutionException, InterruptedException {
        List<String> itemNames = new ArrayList<>(); // Store item names
        try {
            // Use the singleton instance to get the active username
            UserSession active = UserSession.getInstance();
            String username = active.getLoggedInUser().getUsername(); // Retrieve the username

            //Retrieve the collectionName from the Collection Session
            CollectionSession session = CollectionSession.getInstance();
            String selectedCollection = session.getSelectedCollectionName();
            System.out.println("Active collection in writer " + selectedCollection);

            // Navigate to the user's "Collections" sub-collection
            CollectionReference collectionRef = CollectionsApplication.fstoreDB.collection("Users")
                    .document(username) //Username stored in active UserSession
                    .collection("Collections")
                    .document(selectedCollection + "Collection") // Actual name of Collection
                    .collection("Collection Items");

            // Get all documents in the "Collections" sub-collection
            ApiFuture<QuerySnapshot> future = collectionRef.get();
            QuerySnapshot itemSnapshot = future.get();

            System.out.println("DisplayItems Test Print 1");

            List<QueryDocumentSnapshot> documents = itemSnapshot.getDocuments();
            System.out.println("Number of documents: " + documents.size());

            //For each document in the snapshot, iterate through this loop
            for (QueryDocumentSnapshot document : documents) {
                // Retrieve item name from the document
                String itemName = document.getString("Item Name");
                System.out.println("Item Name: " + itemName);

                // if itenName is not null, add it to the list of titles to return
                System.out.println("Item Name: " + itemName);
                if (itemName != null) {
                    itemNames.add(itemName);
                }
            }
            System.out.println("DisplayItems End of method print");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //return list of item names
        return itemNames;
    }

    //Test method linked to addItemInGridBtn, change this back to addNewItem() once that part works
    @FXML
    void addNewItemTest(ActionEvent event) throws ExecutionException, InterruptedException {
        // Build whole collection at once whatever
//        List<String> itemNames = getCollectionItems(); // Fetch item names in collection
//        for (String itemName : itemNames) {
//            addItem("", itemName); // Call addItem for each item
//        }
    }

    @FXML
    public void switchToAddItemView() throws IOException {
        CollectionsApplication.setRoot("add-item-view");
    }

    @FXML
    public void switchToProfileView() throws IOException {
        CollectionsApplication.setRoot("profile-view");
    }

    @FXML
    public void switchToCreateCollectionView() throws IOException {
        CollectionsApplication.setRoot("create-collection-view");
    }

    //just pass a CollectionItem here when it works, or we can get the data from the database? whats easier
    //collection view grid logic uwu happy thanksgiving
    public void addItem(String imageUrl, String itemName) {
        try {
            //switchToAddItemView();
            // Load the FXML for the item component
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/csc325/collectionsproject/components/item-component.fxml"));
            Node itemNode = loader.load();

            // Get the controller of the item component
            ItemComponentController newItemController = loader.getController();
            newItemController.setImage(imageUrl != null ? imageUrl : "/csc325/collectionsproject/imgs/pipermelonart.png");
            newItemController.setLabel(itemName);
            //newItemController.setLabel(labelText != null ? labelText : "Collection Item Here");

            // Add the item to the grid at the next available position
            itemGrid.add(itemNode, column, row);
            //if column > 5, reset, increment row, add button to next

            // Update the grid indices, replace addItemInGridBtn
            column++;
            if (column >= 5) { // 5 items per row
                column = 0;
                row++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
