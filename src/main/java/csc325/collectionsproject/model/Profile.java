package csc325.collectionsproject.model;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Profile {

public List<Collection> collectionsList;
public Collection showcaseItem;
@FXML
public ImageView profilePicture;

 public Profile(){

  //insert default profile picture
  this.collectionsList = new ArrayList<Collection>();

 }

}
