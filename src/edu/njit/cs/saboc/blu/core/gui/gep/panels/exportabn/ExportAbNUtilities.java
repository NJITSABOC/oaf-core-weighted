package edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn;


import java.io.File;
import java.util.Optional;
import javax.swing.JFileChooser;

/**
 *
 * @author Chris O
 */
public class ExportAbNUtilities {

    public static enum DialogType {
        OPEN,
        SAVE
    }

    public static Optional<File> displayFileSelectDialog(DialogType option) {
        final JFileChooser chooser = new JFileChooser();

        int returnVal = JFileChooser.CANCEL_OPTION;
        
        switch(option) {
            case OPEN:
                returnVal = chooser.showOpenDialog(null);
                break;
            case SAVE:
                returnVal = chooser.showSaveDialog(null);
                break;
                
            default:
        }
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            if (!file.isDirectory()) {
                return Optional.of(file);
            }
        }
        
        return Optional.empty();
    }
    
    public static Optional<File> displayFileSelectOpenDialog() {
        return displayFileSelectDialog(DialogType.OPEN);
    }

    public static Optional<File> displayFileSelectSaveDialog() {
        return displayFileSelectDialog(DialogType.SAVE);
    }
}
