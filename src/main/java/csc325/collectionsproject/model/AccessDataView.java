package csc325.collectionsproject.model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.List;

public class AccessDataView{
//Need to finalize our user profile before firebase objects can be created properly
    private final StringProperty username = new SimpleStringProperty();
    private final int rating=0;
    private final StringProperty password = new SimpleStringProperty();
    //Observable list? for collections in user class
    private final ReadOnlyBooleanWrapper writePossible = new ReadOnlyBooleanWrapper();

    public AccessDataView() {
        writePossible.bind(username.isNotEmpty());
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {return password;}

    public ReadOnlyBooleanProperty isWritePossibleProperty() {
        return writePossible.getReadOnlyProperty();
    }

}
