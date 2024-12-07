package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.database.DataSnapshot;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.*;
import csc325.collectionsproject.model.Collection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class FirebaseWriter {

   // int itemRating
    public void addCollectionItemToCollection(String collectionName, String itemName, String itemDescription) throws ExecutionException, InterruptedException {

        UserSession session = UserSession.getInstance();
        User active = session.getLoggedInUser();
        System.out.println("Active user in writer " + active.getUsername());

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                                                                  .collection("Collections").document(collectionName + "Collection")
                                                                  .collection("Collection Items").document(itemName);

        Map<String, Object> data = new HashMap<>();

        data.put("Item Description", itemDescription);
        data.put("Item Name", itemName);
        //data.put("Item Rating", itemRating);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);

/*
        //asynchronously read data
        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();
        if (document.exists()) {
            //System.out.println("Document data: " + document.getData());
        } else {
            System.out.println("Collection not found!");
        }

         */
    }

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

    public void removeCollectionItemFromCollection(String itemName) {
        // Gets Username from current session
        UserSession session = UserSession.getInstance();
        User active = session.getLoggedInUser();
        System.out.println("Active user in writer " + active.getUsername());

        //Retrieve the collectionName from the Collection Session
        CollectionSession sessionC = CollectionSession.getInstance();
        String selectedCollection = sessionC.getSelectedCollectionName();

        //Define Collection
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                .collection("Collections").document(selectedCollection + "Collection").collection("Collection Items").document(itemName);

        docRef.delete();


    }

    //THIS CODE DOES N O T DELETE ONE OF OUR COLLECTIONS!!! IT IS A FIREBASE METHOD TO DELETE STUFF IN THERE!!! ok luv u guys <3
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


}
