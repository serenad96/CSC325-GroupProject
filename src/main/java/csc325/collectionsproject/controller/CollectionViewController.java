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


    /**
     * Brings the user to the Add Item to Collection View
     * @param event triggered when the "Add Item" button is clicked
     * @throws IOException
     */
    @FXML
    void addNewItem(ActionEvent event) throws IOException {
        switchToAddItemView();
    }

    /**
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */

    /**
     * Initialzes collection view by loading up the selected collection's details
     * @throws ExecutionException
     * @throws InterruptedException
     */
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

    /**
     * Fetches all items in the currently selected collection from the Firestore database
     * @return The Items within a collection
     * @throws ExecutionException if database query execution fails
     * @throws InterruptedException
     */
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

    /**
     * Fetches the description of the currently selected collection
     * @throws ExecutionException if database query execution fails
     * @throws InterruptedException
     */
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


    /**
     * Deleted the currently selected collection after user confirmation
     * @throws IOException if navigation to another view fails
     * @throws InterruptedException if deletion process fails/is interrupted
     */
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

    /**
     * Navigates to the Item View to display the details of a selected item.
     * @throws IOException
     */
    @FXML
    public void viewItemDetails() throws IOException {
        switchToItemView();
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
    public void switchToItemView() throws IOException {
        CollectionsApplication.setRoot("item-view");
    }

    @FXML
    void createNewItem(ActionEvent event) throws IOException {
        switchToAddItemView();
    }

    /**
     * Adds an item to the grid display of the collection
     * @param imageUrl
     * @param itemName
     */
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

    /**
     * Marks the current collection as the user's favorite collection
     * @param actionEvent triggered by clicking the "Set As Favorite"
     */
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
