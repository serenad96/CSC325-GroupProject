package csc325.collectionsproject.model;

public class CollectionSession {
        // Singleton instance
        private static csc325.collectionsproject.model.CollectionSession instance;
        // Variable to store the logged-in user's information
        private String selectedCollectionName;
        private String selectedCollectionItemName;
         private String selectedCollectionDescription;

        // Private constructor (to prevent instantiation before registration/login events occur)
        private CollectionSession() {}

        // Method to get the singleton instance
        public static csc325.collectionsproject.model.CollectionSession getInstance() {
            if (instance == null) {
                instance = new csc325.collectionsproject.model.CollectionSession();
            }
            return instance;
        }

    public void setSelectedCollectionName(String collectionName) {
        this.selectedCollectionName = collectionName;
    }

    public String getSelectedCollectionName() {
        return selectedCollectionName;
    }

    public void setSelectedCollectionDescription(String descriptionText) { this.selectedCollectionDescription = descriptionText; }


    public String getSelectedCollectionDescription() { return selectedCollectionDescription;}

    public void setSelectedCollectionItemName(String selectedCollectionItemName) {
        this.selectedCollectionItemName = selectedCollectionItemName;
    }

    public String getSelectedCollectionItemName() {
            return selectedCollectionItemName;
    }
}
