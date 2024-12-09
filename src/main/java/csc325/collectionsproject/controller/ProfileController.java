package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.CollectionSession;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProfileController {

    @FXML
    private Button viewPrimaryCollectionBtn, newCollectionBtn, settingsBtn, viewAllBtn, addItemInGridBtn, uploadProfilePicBtn, deleteCollectionBtn;
    @FXML
    private GridPane itemGrid;
    @FXML
    private Label profileNameLabel, favCollectionLbl;
    @FXML
    private ImageView profilePicture, primaryCollectionImage, showcaseItem1, showcaseItem2, showcaseItem3;

    //Uniform Resource Identifier for remembering image state
    private String uploadedImageUri;

    //GridPane indices
    private int row = 0;
    private int column = 0;


    /**
     * Initializes User profile state, displaying a User's data on their page,
     * including all of their stored Collections, Favorite Collection and locally saved Profile Picture.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void initialize() throws ExecutionException, InterruptedException {
        UserSession session = UserSession.getInstance();
        profileNameLabel.setText("Welcome " + session.getLoggedInUser().getUsername() + "!");

        //Populate all of a users collections
        List<String> collectionNames = getAllCollections(); // Fetch names of user's collection
        for (String collectionName : collectionNames) {
            addItem("", collectionName); // Call addItem for each item
        }

        //Display Favorite Collection
        String favCollection = session.getLoggedInUser().getFavCollectionString();

        if (favCollection != null) { // Check if user has a favorite collection, if not prompt them to add one
            favCollectionLbl.setText(favCollection);
            viewPrimaryCollectionBtn.setManaged(true);
        } else {
            favCollectionLbl.setText("Set a Collection as Favorite and it will show here!");
            viewPrimaryCollectionBtn.setManaged(false);
        }
        System.out.println("User fav Collection on profile init: " + UserSession.getInstance().getLoggedInUser().getFavCollectionString());

        //Remembers last uploaded profile picture
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/csc325/collectionsproject/profilePicImageState.txt"))) {
            uploadedImageUri = reader.readLine(); // Read the saved URI
            if (uploadedImageUri != null) {
                System.out.println("Profile pic set on profile load!");
                profilePicture.setImage(new Image(uploadedImageUri)); // Restore the image
                UserSession.getInstance().getLoggedInUser().setProfilePicString(uploadedImageUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Query Firebase to retrieve all of a user's collections to display on their Profile-View.
     * @return
     */
    public List<String> getAllCollections() {
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

    /**
     * Adds a Collection-Component to the Profile grid upon successful Collection Creation that dynamically sizes according to a
     * user's data.
     * @param imageUrl
     * @param itemName
     */
    public void addItem(String imageUrl, String itemName) {
        try {
            // Load the FXML for the item component
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/csc325/collectionsproject/components/collection-component.fxml"));
            Node itemNode = loader.load();

            // Get the controller of the item component
            CollectionComponentController newCollectionController = loader.getController();
            newCollectionController.setImage(imageUrl != null ? imageUrl : "/csc325/collectionsproject/imgs/defaultCollectionImage.jpg");
            newCollectionController.setLabel(itemName);

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


    /**
     * Displays a user's favorite collection on their profile
     * @param event Handles display of View Favorite Collection Button depending on whether a User has selected
     *              a favorite Collection.
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @FXML
    void openFavoriteCollection(ActionEvent event) throws IOException, ExecutionException, InterruptedException {

        //Get Favorite Collection
        String favCollection = UserSession.getInstance().getLoggedInUser().getFavCollectionString();

        //Start CollectionSession
        CollectionSession session = CollectionSession.getInstance();

        //Set Selected Collection Name
        session.setSelectedCollectionName(favCollection);

        if (favCollection != null) {
            switchToCollectionView();
        } else {
            System.out.println("else in open fav collection - profile cont");
        }
    }


    /**
     * Switches program to Collection-View
     * @throws IOException
     */
    @FXML
    public void switchToCollectionView() throws IOException {
        CollectionsApplication.setRoot("collection-view");
    }

    /**
     * Switches program to Create-Collection-View
     * @throws IOException
     */
    @FXML
    public void switchToCreateCollectionView() throws IOException {
        CollectionsApplication.setRoot("create-collection-view");
    }

    /**
     * Handles profile picture uploading, and storing it locally to circumvent Firebase storage issues.
     * @param event Select Upload Profile Picture button.
     */
    @FXML
     void uploadImage(ActionEvent event) {
        FileChooser imgChooser = new FileChooser();
        imgChooser.setTitle("Choose an Profile Picture");
        imgChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"));
        File file = imgChooser.showOpenDialog(profilePicture.getScene().getWindow());
        if (file != null) {
            uploadedImageUri  = file.toURI().toString();
            UserSession.getInstance().getLoggedInUser().setProfilePicString(uploadedImageUri);
            profilePicture.setImage(new Image(uploadedImageUri));
            // Save the Image State to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/csc325/collectionsproject/profilePicImageState.txt"))) {
                writer.write(uploadedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Image Upload Error");
        }}
}
