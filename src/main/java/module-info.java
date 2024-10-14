module csc325.collectionsproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens csc325.collectionsproject to javafx.fxml;
    exports csc325.collectionsproject;
}