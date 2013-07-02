package resourses;

import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.InternalTextureLoader;
import java.util.HashMap;
//must call first, before entity with resources
public class ResourcesManager {
    private static ResourcesManager resourcesManager;
    //slick 2d loader images
    private InternalTextureLoader textureLoader = InternalTextureLoader.get();
    //name to pathToFile with Image
    private HashMap<String, String> nameForPath;
    private HashMap<String, Image> nameForImage;

    private ResourcesManager() {
        /*nameForPath = new HashMap<String, String>();
        nameForPath.put(RubberBall.getName(), "res/rubberbomb.png");
        //nameForPath.put(ship.getName(), "res/ship.png");

        nameForImage = new HashMap<String, Image>();
        for (String className : nameForPath.keySet()) {
            try {
                Image image = new Image(nameForPath.get(className));
                nameForImage.put(className, image);
            } catch (SlickException e) {
                e.printStackTrace();
            }


            //TODO add loading Image, make ship with image)
        }
            */
    }

    public Image getImageByName(String name) {
        if (nameForImage.containsKey(name)) {
            return nameForImage.get(name);
        } else {
            return null;
        }
    }
    public static ResourcesManager get() {
        if (null == resourcesManager) {
            resourcesManager = new ResourcesManager();
            return resourcesManager;
        } else {
            return resourcesManager;
        }
    }

}
