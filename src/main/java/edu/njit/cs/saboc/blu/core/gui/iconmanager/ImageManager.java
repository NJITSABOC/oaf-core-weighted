package edu.njit.cs.saboc.blu.core.gui.iconmanager;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 *
 * @author Den
 */
public class ImageManager {
    private static ImageManager iconManager = null;
       
    public static ImageManager getImageManager() {
        if(iconManager == null) {
            iconManager = new ImageManager();
        }
        
        return iconManager;
    }
    
    private final Map<String, ImageIcon> icons = new HashMap<>();
    
    public ImageIcon getIcon(String iconFileName) {
        
        if(!icons.containsKey(iconFileName)) {
            URL fileName = ImageManager.class.getResource(String.format("/edu/njit/cs/saboc/blu/core/images/%s", iconFileName));
                        
            icons.put(iconFileName, new ImageIcon(fileName));
        }
        
        return icons.get(iconFileName);
    }
    
    public ImageIcon getDerivationImage(String derivationImageName) {
        URL fileName = ImageManager.class.getResource(String.format("/edu/njit/cs/saboc/blu/core/images/derivation/%s", derivationImageName));
        
        return new ImageIcon(fileName);
    }
}
