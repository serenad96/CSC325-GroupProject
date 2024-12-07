package csc325.collectionsproject.model;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final String ICONS_PATH = "/csc325/collectionsproject/icons/";
    private static final Map<String, Image> resources = new HashMap<>();

    public static void initialize() {
        String[] iconFiles = {
                "toggle_on.png",
                "toggle_off.png",
                "rating_empty_star.png",
                "rating_full_gold_star.png",
                "rating_half_gold_star.png"
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

    public static Image getImage(String fileName) {
        return resources.get(fileName);
    }
}
