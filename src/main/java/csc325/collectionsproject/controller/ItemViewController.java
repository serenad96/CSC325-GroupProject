package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.CollectionSession;
import csc325.collectionsproject.model.UserSession;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * Deletes a Collection within Firebase-------------
 */
public class ItemViewController {

    public Button deleteItemBtn;
    @FXML
    private Button backBtn, profileBtn, setFavItemBtn;

    @FXML
    private Label itemNameLbl, itemDescLbl, itemRatingLabel, itemTagsLbl;

    /**
     * Deletes a Collection within Firebase
     */
    @FXML
    private void initialize() throws ExecutionException, InterruptedException {
        getItemInfo();
    }

    /**
     * Gets the Item Name, Item Rating, and Item Description of the selected Item
     *
     * @throws ExecutionException Execution Exception
     * @throws InterruptedException Interrupted Exception
     */
    public void getItemInfo() throws ExecutionException, InterruptedException {
        // Use the singleton instance to get the active username
        UserSession active = UserSession.getInstance();
        String username = active.getLoggedInUser().getUsername(); // Retrieve the username

        //Retrieve the collectionName from the Collection Session
        CollectionSession session = CollectionSession.getInstance();
        String selectedCollection = session.getSelectedCollectionName();
        String selectedItem = session.getSelectedCollectionItemName();

        // Navigate to the user's "Collections" sub-collection
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users")
                .document(username) //Username stored in active UserSession
                .collection("Collections")
                .document(selectedCollection + "Collection") // Actual name of Collection
                .collection("Collection Items")
                .document(selectedItem);

        // Get all documents in the "Collections" sub-collection
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot itemSnapshot = future.get();


        //For each document in the snapshot, iterate through this loop
        // Retrieve item name from the document
        Map<String,Object> data=itemSnapshot.getData();
        String itemName = (String) data.get("Item Name");
        Number rating = (Number) data.get("Item Rating");
        String itemDesc = (String) data.get("Item Description");

        itemNameLbl.setText(itemName);
        itemDescLbl.setText(itemDesc);
        itemRatingLabel.setText(String.valueOf(rating));
    }

    @FXML
    void setFavItem(ActionEvent event) {

    }

    /**
     * Switches the program to the Collection View
     *
     * @throws IOException IO Exception
     */
    @FXML
    public void switchToCollectionView() throws IOException {
        CollectionsApplication.setRoot("collection-view");
    }

    /**
     * Switches the program to the Collection View
     *
     * @throws IOException IO Exception
     */
    @FXML
    void goBack(ActionEvent event) throws IOException {
        switchToCollectionView();
    }

    /**
     * Switches the program to the Profile View
     *
     * @throws IOException IO Exception
     */
    @FXML
    public void switchToProfileView(ActionEvent event) throws IOException  {
        CollectionsApplication.setRoot("profile-view");
    }

    /**
     * Presents a Pop-up asking the User if they want to delete the current Item, if they select OK it will delete the Item and send them back to Collection View
     *
     * @param actionEvent a user clicks on the delete item button
     * @throws IOException IO Exception
     */
    public void deleteItem(ActionEvent actionEvent) throws IOException {
        //Call Firebase Writer
        FirebaseWriter fbWriter = new FirebaseWriter();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //Create popup when delete button is clicked
        alert.setTitle("Delete Warning");
        alert.setHeaderText("Are you sure?");
        String s ="This will be deleted forever! (a long time)";
        alert.setContentText(s);

        Optional<ButtonType> result=alert.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

            //Delete Item
            fbWriter.removeCollectionItemFromCollection();

            //Switch to Collection View
            switchToCollectionView();
        }
    }
}
