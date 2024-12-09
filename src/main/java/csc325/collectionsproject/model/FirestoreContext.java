package csc325.collectionsproject.model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class FirestoreContext {

    /**
     * This initializes the Firestore database with a service key for storing information in the program.
     * @return
     */
    public Firestore firebase() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/csc325/collectionsproject/key.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return FirestoreClient.getFirestore();
    }


}
