package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class SavePNGButton<T extends AbstractionNetwork> extends AbNOptionsButton<T> {
    
    private final AbNConfiguration config;
    
    public SavePNGButton(AbNConfiguration config) {
        
        super("BluSavePng.png", "Save current view as .png image.");
        
        this.config = config;
        
        this.addActionListener((ae) -> {
            saveAbNToPNG();
        });
    }
    
    @Override
    public void setEnabledFor(T abn) {
        this.setEnabled(true);
    }
    
    public void saveAbNToPNG() {
        final JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                return f.getName().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "Portal Network Graphics (.png) Images";
            }
        });

        int returnVal = chooser.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            BufferedImage graphImage = config.getUIConfiguration().getDisplayPanel().getCurrentViewImage();

            try {
                String file = chooser.getSelectedFile().getAbsolutePath();

                if (!file.toLowerCase().endsWith(".png")) {
                    file += ".png";
                }

                ImageIO.write(graphImage, "png", new File(file));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
