package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javafx.scene.text.*;

import javafx.stage.FileChooser;

public class AddItemController {

        @FXML
        private ImageView addItemImg, profilePicture, star1, star2, star3, star4, star5;

        @FXML
        private TextField itemNameTF;

        @FXML
        private TextArea itemDescriptionTF;

        @FXML
        private Label starRatingLabel, addItemLbl;

        @FXML
        private HBox starBox;

        @FXML
        private Button addImgBtn, addNewItemBtn, backBtn, newCollectionBtn, profileBtn, collectionViewBtn;

        @FXML
        private ToggleButton itemRating1, itemRating2, itemRating3, itemRating4, itemRating5;

        @FXML
        private Text collectionNameLbl;

        private ToggleGroup ratingToggleGwoup;
        int ratingValue;
       // private CollectionViewController collectionController;

        @FXML
        private void initialize() {
                //i makea da rating pizza
                ratingToggleGwoup = new ToggleGroup();
                itemRating1.setToggleGroup(ratingToggleGwoup);
                itemRating2.setToggleGroup(ratingToggleGwoup);
                itemRating3.setToggleGroup(ratingToggleGwoup);
                itemRating4.setToggleGroup(ratingToggleGwoup);
                itemRating5.setToggleGroup(ratingToggleGwoup);

                //Set profile picture if one has been set previously
                if(!UserSession.getInstance().getLoggedInUser().getProfilePicString().isEmpty()) {
                        profilePicture.setImage(new Image(UserSession.getInstance().getLoggedInUser().getProfilePicString()));
                        System.out.println("Set profile pic on add item view!");
                }
                String selected = CollectionSession.getInstance().getSelectedCollectionName();
                collectionNameLbl.setText("Add Item to " + selected);
        }

        //individual collection controller instance for a specific collection
//        public void setCollectionController(CollectionViewController collectionController) {
//                this.collectionController = collectionController;
//        }

        //This is clicking the add new item button in the minibar
        @FXML
        void addNewItem(ActionEvent event) throws IOException, ExecutionException, InterruptedException {
                CollectionSession session = CollectionSession.getInstance();
                String selectedCollection = session.getSelectedCollectionName();


                //Write Item in to Firebase
                String itemName = itemNameTF.getText();
                String itemDescription = itemDescriptionTF.getText();
               // int itemRating= (int) itemRating1.getUserData();
                System.out.println("Collection name from getCollectionName : " + selectedCollection);
               // addItemLabel.setText(collectionName);
                FirebaseWriter fbWriter = new FirebaseWriter();

                // Add the item to the collection
                fbWriter.addCollectionItemToCollection(selectedCollection ,itemName, itemDescription);
               // addItemLbl.setText("asdf");



                switchToCollectionView();
        }

        // Retrieve the collection name from Firestore
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

                                // Get collection title
                                String collectionTitle = document.getString("Collection Title");
                                System.out.println("Collection Title: " + collectionTitle);
                                return collectionTitle;
                        }
                } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                }
                return null;
        }

        @FXML
        public void switchToCollectionView() throws IOException {
                CollectionsApplication.setRoot("collection-view");
        }

        public void switchToProfileView() throws IOException {
                CollectionsApplication.setRoot("profile-view");
        }

        public void switchToCreateCollectionView() throws IOException {
                CollectionsApplication.setRoot("create-collection-view");
        }

        @FXML
        void itemPrivacyToggleClicked(ActionEvent event) {
        }

        @FXML
        void goBack(ActionEvent event) throws IOException {
                switchToCollectionView();
        }

        @FXML
        void ratingClicked(ActionEvent event) {
                // Get the selected button from the ToggleGroup
                Toggle selectedToggle = ratingToggleGwoup.getSelectedToggle();

                ToggleButton currentStarRATING;

                if (selectedToggle != null) {
                        currentStarRATING = (ToggleButton) selectedToggle;
                        System.out.println("Selected button ID: " + currentStarRATING.getId());
                        // Use a switch based on the fx:id of the selected button
                        switch (currentStarRATING.getId()) {
                                case "itemRating1":
                                        System.out.println("Star Rating ID Value: '" + currentStarRATING.getId() + "'");
                                        System.out.println("Selected Rating 1");
                                        starRatingLabel.setText("1 STAR THIS THING SUCKS");
                                        //set boolean value of item on create item, define value here
                                        //RATING VALUE DOESNT DO ANYTHING RN I THINK IT NEEDS TO BE STORED SOMEWHERE TO WORK
                                        ratingValue = 1;
                                        // Do stuff for Rating 1                         "rating_full_gold_star.png"
                                        star1.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star2.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        star3.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        star4.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        star5.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        break;

                                case "itemRating2":
                                        System.out.println("Selected Rating 2");
                                        starRatingLabel.setText("2 STAR THIS THING IS FINE IG");
                                        // Do stuff for Rating 2
                                        ratingValue = 2;
                                        star1.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star2.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star3.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        star4.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        star5.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        break;
                                case "itemRating3":
                                        System.out.println("Selected Rating 3");
                                        starRatingLabel.setText("3 STARS yeah aight");
                                        // Do stuff for Rating 3
                                        ratingValue = 3;
                                        star1.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star2.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star3.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star4.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        star5.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        break;
                                case "itemRating4":
                                        System.out.println("Selected Rating 4");
                                        starRatingLabel.setText("4 STARs oooo KINDA Spicy");
                                        // Do stuff for Rating 4
                                        ratingValue = 4;
                                        star1.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star2.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star3.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star4.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star5.setImage(ResourceManager.getImage("rating_empty_star.png"));
                                        break;
                                case "itemRating5":
                                        System.out.println("Selected Rating 5");
                                        starRatingLabel.setText("5 STARS BABY!!!!!!!!!");
                                        // Do stuff for Rating 5
                                        ratingValue = 5;
                                        star1.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star2.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star3.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star4.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        star5.setImage(ResourceManager.getImage("rating_full_gold_star.png"));
                                        break;
                                default:
                                        System.out.println("default case");
                                        // set ratingValue to null?
                        }
                }
                else {
                System.out.println("No button is selected");
                // starRatingLabel.setText();
                }
        }


        @FXML
        void uploadImage(ActionEvent event) {
                FileChooser imgChooser = new FileChooser();
                imgChooser.setTitle("Choose an Item Image");
                imgChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"));
                File file = imgChooser.showOpenDialog(addItemImg.getScene().getWindow());
                if (file != null) {
                    addItemImg.setImage(new Image(file.toURI().toString()));
                    //String filePath = "src/main/resources/csc325/collectionsproject/imgs";
                } else {
                        System.out.println("Gay baby error");
                }

                //Need to make images save
        }
}
