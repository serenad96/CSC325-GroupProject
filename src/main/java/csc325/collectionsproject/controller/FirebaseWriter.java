package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.database.DataSnapshot;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.Collection;
import csc325.collectionsproject.model.CollectionItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class FirebaseWriter {



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

    public void addCollectionToUser(String username, String collectionTitle, String collectionDescription) {

        Collection collection = new Collection(collectionTitle, collectionDescription);
        //User active = UserSession.getInstance().getLoggedInUser();

        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username)
                                                                  .collection("Collections").document(collectionTitle + "Collection");


        Map<String, Object> data = new HashMap<>();

        data.put("Collection Description", collectionDescription);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }

}
