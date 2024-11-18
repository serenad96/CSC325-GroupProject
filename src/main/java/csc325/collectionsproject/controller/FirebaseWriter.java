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

        /*
        DocumentReference docRef = CollectionsApplication.fstoreDB.collection("Users").document(username);



        //asynchronously read data
        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();
        if (document.exists()) {
            //System.out.println("Document data: " + document.getData());
        } else {
            System.out.println("Collection not found!");
        }

        //future.get(Collection collectionName);

        Map<String, Object> userData = document.getData();


        assert userData != null;
        Object collectionData = userData.get(collectionName + "Collection");

        System.out.println(collectionData.getClass().getName());

        //String itemDesc = ;




        //Map<String, Object> data = new HashMap<>();
        //data.put(itemName, collectionItem);

        //ApiFuture<WriteResult> result = docRef.update(data);

         */
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
