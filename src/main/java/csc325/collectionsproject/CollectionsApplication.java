package csc325.collectionsproject;

import csc325.collectionsproject.controller.FirebaseWriter;
import csc325.collectionsproject.model.CollectionItem;

import csc325.collectionsproject.model.FirestoreContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class CollectionsApplication extends Application {
    public static Scene scene;    // Firestore reference
    public static Firestore fstoreDB;
    public static FirebaseAuth fauth;
    private final FirestoreContext contxtFirebase = new FirestoreContext();

    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        //Initialize Firestore, authorization, db
        fstoreDB = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(CollectionsApplication.class.getResource("registration-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 860, 640);

//        try {
//            String cssPath = getClass().getResource("src/main/resources/csc325/collectionsproject/style.css").toExternalForm();
//            scene.getStylesheets().add(cssPath);
//        } catch (NullPointerException e) {
//            System.err.println("Error: Could not load style.css. Check the file path.");
//            e.printStackTrace();
//        }

        stage.setMaximized(true);
        stage.setTitle("CollectionsApp");
        stage.setScene(scene);
        stage.show();

        FirebaseWriter writer = new FirebaseWriter();

//        writer.addCollectionToUser("Serena", "woaselinselin");
//        writer.addCollectionItemToCollection("Test","goodItem","gaygaygay");

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