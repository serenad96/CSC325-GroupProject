package csc325.collectionsproject;

import csc325.collectionsproject.model.FirestoreContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;

import com.google.firebase.auth.*;
import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;
import java.io.IOException;

public class CollectionsApplication extends Application {
    public static Scene scene;    // Firestore reference
    public static Firestore fstoreDB;
    public static FirebaseAuth fauth;
    private final FirestoreContext contxtFirebase = new FirestoreContext();

    @Override
    public void start(Stage stage) throws IOException {
        //Initialize Firestore, authorization, db
        fstoreDB = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();

 //     FXMLLoader fxmlLoader = new FXMLLoader(CollectionsApplication.class.getResource("collection-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(CollectionsApplication.class.getResource("registration-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 860, 640);
        stage.setTitle("CollectionsApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CollectionsApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }


}