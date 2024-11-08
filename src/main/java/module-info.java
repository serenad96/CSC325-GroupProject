module csc325.collectionsproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires com.google.api.apicommon;

    opens csc325.collectionsproject to javafx.fxml;
    exports csc325.collectionsproject;
    exports csc325.collectionsproject.controller;
    opens csc325.collectionsproject.controller to javafx.fxml;
    exports csc325.collectionsproject.model;
    opens csc325.collectionsproject.model to javafx.fxml;
}