package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.CollectionItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemCreationController {

    public void addCollectionItem() {

        CollectionItem collectionItem = new CollectionItem("idk wtf");

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("CollectionItems").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Item Name", collectionItem.getItemName());
        data.put("Item Image", "test");

        ApiFuture<WriteResult> result = docRef.set(data);
    }

}
