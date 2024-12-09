package csc325.collectionsproject.model;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final String ICONS_PATH = "/csc325/collectionsproject/icons/";
    private static final Map<String, Image> resources = new HashMap<>();

    /**
     * This initializes icons utilized by the program on CollectionApplication start() running, so that there
     * are no runtime errors when loading icons dynamically at runtime.
     */
    public static void initialize() {
        String[] iconFiles = {
                "toggle_on.png",
                "toggle_off.png",
                "rating_empty_star.png",
                "rating_full_gold_star.png",
                "rating_half_gold_star.png",
                "orange_heart.png",
                "fav_collection.png",
                "cats-08.png",
                "cats-02.png",
                "cats-01.png"
        };

        for (String fileName : iconFiles) {
            try {
                URL resource = ResourceManager.class.getResource(ICONS_PATH + fileName);
                if (resource != null) {
                    Image image = new Image(resource.toExternalForm());
                    resources.put(fileName, image);
                    System.out.println("Loaded: " + fileName);
                } else {
                    System.err.println("Error: Resource not found for " + fileName);
                }
            } catch (Exception e) {
                System.err.println("Error loading resource: " + fileName);
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the requested preloaded icon.
     * @param fileName The String name of the Icon being requested by the program.
     * @return Returns the requested icon Image.
     */
    public static Image getImage(String fileName) {
        return resources.get(fileName);
    }
}
