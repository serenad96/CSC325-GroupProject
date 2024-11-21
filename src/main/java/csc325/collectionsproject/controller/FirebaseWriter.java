package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.CollectionReference;
import com.google.firebase.database.DataSnapshot;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.Collection;
import csc325.collectionsproject.model.CollectionItem;
import csc325.collectionsproject.model.User;
import csc325.collectionsproject.model.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    public void addCollectionItemToCollection(String username, String collectionName, String itemName, String itemDescription) throws ExecutionException, InterruptedException {


        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username)
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

        UserSession session1 = UserSession.getInstance();
        User active = session1.getLoggedInUser();
        System.out.println(active.getUsername());

        User active = UserSession.getInstance().getLoggedInUser();

       //   username = active.getUsername();

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username)
                                                                  .collection("Collections").document(collectionTitle + "Collection");



        // Get reference to the user document in FirestoreDB
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername());

        CollectionReference subCollectionRef = docRef.collection("Collections");


        User active = UserSession.getInstance().getLoggedInUser();

        // Create a Map to store the collection data (Title + Description)
        Map<String, Object> collectionData  = new HashMap<>();
        collectionData.put(collectionTitle + " Collection", collection);
        collectionData.put("Collection Description", collection.getCollectionDescription());

        ApiFuture<DocumentReference> result = subCollectionRef.add(collectionData);
        // Use set() to add the collection data inside the user document
 //       ApiFuture<WriteResult> result = docRef.set(collectionData, SetOptions.merge());

        //asynchronously write data
      //  ApiFuture<WriteResult> result = docRef.update(collectionData);
  //      System.out.println("Collection added: " + result.get().getUpdateTime());
/*

        Map<String, Object> data = new HashMap<>();

        data.put("Collection Description", collectionDescription);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
*/
    }

}
