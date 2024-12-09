package csc325.collectionsproject.model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.List;

public class AccessDataView{

    private final StringProperty username = new SimpleStringProperty();
    private final int rating=0;
    private final StringProperty password = new SimpleStringProperty();
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
