package csc325.collectionsproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CollectionsApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CollectionsApplication.class.getResource("collectionview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 860, 640);
        stage.setTitle("CollectionsApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}