package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.CollectionSession;
import csc325.collectionsproject.model.ResourceManager;
import csc325.collectionsproject.model.User;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class CollectionViewController {
    @FXML
    private Button addItemBtn, newCollectionBtn, profileBtn, deleteCollectionBtn, addItemInGridBtn, setFavoriteCollectionBtn;

    @FXML
    private GridPane itemGrid;

    @FXML
    private Label collectionNameLbl, collectionDescLbl;

    @FXML
    private ImageView profilePicture, favCollectionImg;


    private int row = 0;
    private int column = 0;
    boolean isFavorite = false;

    String selectedCollectionName;


    @FXML
    void addNewItem(ActionEvent event) throws IOException {
        switchToAddItemView();
    }

    public void initialize() throws ExecutionException, InterruptedException {
        //Retrieve the collectionName from the Collection Session
        CollectionSession session = CollectionSession.getInstance();
        String selectedCollection = session.getSelectedCollectionName();
        getCollectionDesc();

        //Get Selected Collection Name
        List<String> itemNames = getCollectionItems(); // Fetch item names in collection
        for (String itemName : itemNames) {
            addItem("", itemName); // Call addItem for each item
        }
        //Set profile picture if one has been set previously
        if (!UserSession.getInstance().getLoggedInUser().getProfilePicString().isEmpty()) {
            profilePicture.setImage(new Image(UserSession.getInstance().getLoggedInUser().getProfilePicString()));
            System.out.println("Set profile pic on collection view!");
        }
        collectionNameLbl.setText(selectedCollection);

        System.out.println("Selected collection" + selectedCollection);
        System.out.println("User fav Collection on collection init: " + UserSession.getInstance().getLoggedInUser().getFavCollectionString());
        if (selectedCollection.equals(UserSession.getInstance().getLoggedInUser().getFavCollectionString())) {
            isFavorite = true;
            favCollectionImg.setImage(ResourceManager.getImage("fav_collection.png"));
            System.out.println("Fav = true");
        } else {
            System.out.println("Fav = false");
            favCollectionImg.setImage(ResourceManager.getImage("orange_heart.png"));
            isFavorite = false;
        }

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

                // if itemName is not null, add it to the list of titles to return
                System.out.println("Item Name: " + itemName);
                if (itemName != null) {
                    itemNames.add(itemName);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //return list of item names
        return itemNames;
    }

    public void getCollectionDesc() throws ExecutionException, InterruptedException {

        // Use the singleton instance to get the active username
        UserSession active = UserSession.getInstance();
        String username = active.getLoggedInUser().getUsername(); // Retrieve the username

        //Retrieve the collectionName from the Collection Session
        CollectionSession session = CollectionSession.getInstance();
        String selectedCollection = session.getSelectedCollectionName();

        // Navigate to the user's "Collections" sub-collection
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users")
                .document(username) //Username stored in active UserSession
                .collection("Collections")
                .document(selectedCollection + "Collection"); // Actual name of Collection


        // Get all documents in the "Collections" sub-collection
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot itemSnapshot = future.get();
      //  List<QueryDocumentSnapshot> documents = itemSnapshot.getData();

        //For each document in the snapshot, iterate through this loop
        // Retrieve item name from the document
            Map<String,Object> data=itemSnapshot.getData();
            String collectionDesc = (String) data.get("Collection Description");
            collectionDescLbl.setText(collectionDesc);
    }



    public void deleteCollection() throws IOException, InterruptedException {
    FirebaseWriter firebaseWriter = new FirebaseWriter();
    RegistrationController controller = new RegistrationController();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //Create popup when delete button is clicked
        alert.setTitle("Delete Warning");
        alert.setHeaderText("Are you sure?");
        String s ="This will be deleted forever! (a long time)";
        alert.setContentText(s);

        Optional<ButtonType> result=alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

            firebaseWriter.removeCollectionFromUser();
            controller.switchToProfileView();

        }


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

    @FXML
    void createNewItem(ActionEvent event) throws IOException {
        switchToAddItemView();
    }

    //just pass a CollectionItem here when it works, or we can get the data from the database? whats easier
    //collection view grid logic uwu happy thanksgiving
    public void addItem(String imageUrl, String itemName) {
        try {
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
            //add button back to grid in next slot
            itemGrid.getChildren().remove(addItemInGridBtn);
            itemGrid.add(addItemInGridBtn, column, row);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setFavoriteCollection(ActionEvent actionEvent) {
        FirebaseWriter fbwriter = new FirebaseWriter();
        if (!isFavorite) {
            favCollectionImg.setImage(ResourceManager.getImage("fav_collection.png"));
            UserSession.getInstance().getLoggedInUser().setFavCollectionString(selectedCollectionName);
            isFavorite = true;
            System.out.println("Fav collection set to " + UserSession.getInstance().getLoggedInUser().getFavCollectionString());
        }
        fbwriter.setFavoriteCollection();
    }
}
