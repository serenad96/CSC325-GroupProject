package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.Collection;
import csc325.collectionsproject.model.CollectionItem;
import csc325.collectionsproject.model.User;
import csc325.collectionsproject.model.UserSession;

import java.util.HashMap;
import java.util.Map;

public class FirebaseWriter {



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
    }

    public void addCollectionToUser(String collectionTitle, String collectionDescription) {
        // Create a new Collection object in Java
        Collection collection = new Collection(collectionTitle, collectionDescription);

        User active = UserSession.getInstance().getLoggedInUser();
        // Get reference to the user document in FirestoreDB
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(active.getUsername());

        // Create a Map to store the collection data (Title + Description)
        Map<String, Object> collectionData  = new HashMap<>();
        collectionData.put(collectionTitle + "Title", collection);
        collectionData.put("Collection Description", collection.getCollectionDescription());

        // Use set() to add the collection data inside the user document
 //       ApiFuture<WriteResult> result = docRef.set(collectionData, SetOptions.merge());

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.update(collectionData);
  //      System.out.println("Collection added: " + result.get().getUpdateTime());
    }

}
