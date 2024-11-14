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



    public void addCollectionItem() {

        CollectionItem collectionItem = new CollectionItem("idk wtf");

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("CollectionItems").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Item Name", collectionItem.getItemName());
        data.put("Item Image", collectionItem.getItemImage());

        ApiFuture<WriteResult> result = docRef.set(data);
    }

    public void addCollection() {

        Collection collection = new Collection();

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Collections").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Collection Name", collection.getCollectionTitle());
        data.put("Tags", "test name");

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }

}
