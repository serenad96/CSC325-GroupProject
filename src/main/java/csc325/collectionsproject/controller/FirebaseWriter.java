package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.database.DataSnapshot;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.Collection;
import csc325.collectionsproject.model.CollectionItem;
import csc325.collectionsproject.model.User;
import csc325.collectionsproject.model.UserSession;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class FirebaseWriter {


/* SELIN PUSHES
    public void addCollectionItemToCollection(String username, String collectionName, String itemName, String itemDescription) {
        CollectionItem collectionItem = new CollectionItem(itemName, itemDescription);

        //Where can i store username to get this properly

        // Reference to the user's collection document
        DocumentReference collectionDocRef = CollectionsApplication.fstoreDB
                .collection("Users").document(username)
                .collection("UserCollections").document(collectionName);

        // Reference to the "items" subcollection within the collection
       // CollectionReference itemsSubCollection = collectionDocRef.collection("items");

        // Add the CollectionItem to the "items" subcollection
      //  ApiFuture<WriteResult> result = itemsSubCollection.document(itemName).set(collectionItem);
//
//        ApiFuture<WriteResult> result = docRef.update(data);
//        System.out.println("Collection Item added at: " + result.get().getUpdateTime());
*/

    public void addCollectionItemToCollection(String collectionName, String itemName, String itemDescription) throws ExecutionException, InterruptedException {

        UserSession session1 = UserSession.getInstance();
        User active = session1.getLoggedInUser();
        //Print Username
        System.out.println(active.getUsername());

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                                                                  .collection("Collections").document(collectionName + "Collection")
                                                                  .collection("Collection Items").document(itemName);

        Map<String, Object> data = new HashMap<>();

        data.put("Item Description", itemDescription);

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
        UserSession session1 = UserSession.getInstance();
        User active = session1.getLoggedInUser();
        //Print Username
        System.out.println(active.getUsername());


        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                                                                  .collection("Collections").document(collectionTitle + "Collection");

        // Get reference to the user document in FirestoreDB
        //DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername());

        //CollectionReference subCollectionRef = docRef.collection("Collections");


        // Create a Map to store the collection data (Title + Description)
        Map<String, Object> data  = new HashMap<>();
        data.put("Collection Title", collectionTitle);
        data.put("Collection Description", collectionDescription);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        //print added collection name to console
        System.out.println("Collection " + collectionTitle + " added");




        //TEST CODE

        //ApiFuture<DocumentReference> result = subCollectionRef.add(collectionData);

        // Use set() to add the collection data inside the user document
 //       ApiFuture<WriteResult> result = docRef.set(collectionData, SetOptions.merge());

        //asynchronously write data
      //  ApiFuture<WriteResult> result = docRef.update(collectionData);
  //      System.out.println("Collection added: " + result.get().getUpdateTime());

    }


    public void removeCollectionFromUser(String collectionTitle) {

        //Get Active User
        UserSession session1 = UserSession.getInstance();
        User active = session1.getLoggedInUser();
        //Print Username
        System.out.println(active.getUsername());

        //Define Collection
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                .collection("Collections").document(collectionTitle + "Collection");

        //Define Collection Items Subcollection
        CollectionReference collectRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                .collection("Collections").document(collectionTitle + "Collection").collection("Collection Items");

        //Delete Collection Items Subcollection
        deleteCollection(collectRef, 100);

        //Delete Collection
        docRef.delete();

    }

    public void removeCollectionItemFromCollection(String collectionTitle, String itemName) {

        //Get Active User
        UserSession session1 = UserSession.getInstance();
        User active = session1.getLoggedInUser();
        //Print Username
        System.out.println(active.getUsername());

        //Define Collection
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername())
                .collection("Collections").document(collectionTitle + "Collection").collection("Collection Items").document(itemName);

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
