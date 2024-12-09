package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.*;
import csc325.collectionsproject.model.Collection;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * This class is for methods that heavily utilize Google Firebase
 */
public class FirebaseWriter {

    /**
     * Creates a Collection Item within a Collection in Google Firebase
     *
     * @param collectionName gets a name for the collection that the item will be added to
     * @param itemName sets a name for a collection item
     * @param itemDescription sets a description for a collection item
     * @param itemRating sets a rating for a collection item
     * @throws ExecutionException Execution Exception
     * @throws InterruptedException Interrupted Exception
     */
    public void addCollectionItemToCollection(String collectionName, String itemName, String itemDescription, int itemRating) throws ExecutionException, InterruptedException {

        UserSession session = UserSession.getInstance();
        User active = session.getLoggedInUser();
        System.out.println("Active user in writer " + active.getUsername());

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                                                                  .collection("Collections").document(collectionName + "Collection")
                                                                  .collection("Collection Items").document(itemName);

        Map<String, Object> data = new HashMap<>();

        data.put("Item Description", itemDescription);
        data.put("Item Name", itemName);
        data.put("Item Rating", itemRating);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);

    }

    /**
     * Creates a Collection within a User in Google Firebase
     *
     * @param collectionTitle sets a name for a collection
     * @param collectionDescription sets a description for a collection
     */
    public void addCollectionToUser(String collectionTitle, String collectionDescription) {
        // Create a new Collection object in Java
        Collection collection = new Collection(collectionTitle, collectionDescription);


        // Gets Username from current session
        UserSession session = UserSession.getInstance();
        User active = session.getLoggedInUser();
        System.out.println("Active user in writer " + active.getUsername());


        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                                                                  .collection("Collections").document(collectionTitle + "Collection");

        // Create a Map to store the collection data (Title + Description)
        Map<String, Object> data  = new HashMap<>();
        data.put("Collection Title", collectionTitle);
        data.put("Collection Description", collectionDescription);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        //print added collection name to console
        System.out.println("Collection " + collectionTitle + " added");

    }

    /**
     * Deletes a Collection and the Items contained inside within Firebase
     */
    public void removeCollectionFromUser() {
        // Gets Username from current session
        UserSession session = UserSession.getInstance();
        User active = session.getLoggedInUser();
        System.out.println("Active user in writer " + active.getUsername());

        //Retrieve the collectionName from the Collection Session
        CollectionSession sessionC = CollectionSession.getInstance();
        String selectedCollection = sessionC.getSelectedCollectionName();

        //Define Collection
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                .collection("Collections").document(selectedCollection + "Collection");

        //Define Collection Items Subcollection
        CollectionReference collectRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                .collection("Collections").document(selectedCollection + "Collection").collection("Collection Items");

        //Delete Collection Items Subcollection
        deleteCollection(collectRef, 100);

        //Delete Collection
        docRef.delete();

    }

    /**
     * Deletes a CollectionItem within Firebase
     */
    public void removeCollectionItemFromCollection() {
        // Gets Username from current session
        UserSession session = UserSession.getInstance();
        User active = session.getLoggedInUser();
        System.out.println("Active user in writer " + active.getUsername());

        //Retrieve the collectionName from the Collection Session
        CollectionSession sessionC = CollectionSession.getInstance();
        String selectedCollection = sessionC.getSelectedCollectionName();
        String selectedItem = sessionC.getSelectedCollectionItemName();

        //Define Collection
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                .collection("Collections").document(selectedCollection + "Collection").collection("Collection Items").document(selectedItem);

        docRef.delete();


    }

    /**
     * Deletes all the items within a Collection before deleting the Collection itself
     *
     * @param collection the collection that is going to be deleted
     * @param batchSize the size of the segments the collection will be deleted in to prevent out-of-memory errors
     */
    void deleteCollection(CollectionReference collection, int batchSize) {
        try {
            // retrieve a small batch of documents to avoid out-of-memory errors
            ApiFuture<QuerySnapshot> future = collection.limit(batchSize).get();
            int deleted = 0;
            // future.get() blocks on document retrieval
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                document.getReference().delete();
                ++deleted;
            }
            if (deleted >= batchSize) {
                // retrieve and delete another batch
                deleteCollection(collection, batchSize);
            }
        } catch (Exception e) {
            System.err.println("Error deleting collection : " + e.getMessage());
        }
    }

    /**
     * Sets a Collection as the Favorite Collection
     */
    void setFavoriteCollection() {
        // Gets Username from current session
        UserSession session = UserSession.getInstance();
        User active = session.getLoggedInUser();

        //Retrieve the collectionName from the Collection Session
        CollectionSession sessionC = CollectionSession.getInstance();
        String selectedCollection = sessionC.getSelectedCollectionName();

        String username = active.getUsername();


        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username);

        Map<String, Object> data = new HashMap<>();
        data.put("Favorite Collection", selectedCollection);
        UserSession.getInstance().getLoggedInUser().setFavCollectionString(selectedCollection);
        ApiFuture<WriteResult> result = docRef.update(data);
    }
}
