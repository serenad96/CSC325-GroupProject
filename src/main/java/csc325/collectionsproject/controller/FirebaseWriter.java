package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.Collection;
import csc325.collectionsproject.model.CollectionItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FirebaseWriter {



    public void addCollectionItemToCollection(String username, String collectionName, String itemName, String itemDescription) {

        CollectionItem collectionItem = new CollectionItem(itemName);

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username);

        Map<String, Object> data = new HashMap<>();
        data.put(itemName, collectionItem);

        ApiFuture<WriteResult> result = docRef.update(data);
    }

    public void addCollectionToUser(String username, String collectionTitle, String collectionDescription) {

        Collection collection = new Collection(collectionTitle, collectionDescription);

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username);

        Map<String, Object> data = new HashMap<>();
        data.put(collectionTitle + "Collection", collection);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.update(data);
    }

}
