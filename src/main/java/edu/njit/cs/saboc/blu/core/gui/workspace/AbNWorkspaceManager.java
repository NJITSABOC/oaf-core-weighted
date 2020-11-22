package edu.njit.cs.saboc.blu.core.gui.workspace;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.AbNGraphFrameInitializers;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager;
import java.io.File;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class AbNWorkspaceManager {

    private Optional<AbNGraphFrameInitializers> optInitializers = Optional.empty();
    
    private final MultiAbNGraphFrame graphFrame;

    public AbNWorkspaceManager(MultiAbNGraphFrame graphFrame) {
        this.graphFrame = graphFrame;
    }
    
    public void setInitializers(AbNGraphFrameInitializers initializers) {
        this.optInitializers = Optional.of(initializers);
    }
    
    public void clearInitializers() {
        this.optInitializers = Optional.empty();
    }

    public AbNWorkspace getWorkspaceFor(File file) {
        
        if(!optInitializers.isPresent()) {
            // TODO: Exception
            
            return null;
        }
        
        return new AbNWorkspace(graphFrame, file, optInitializers.get().getAbNParser());
    }

    public void addOrUpdateWorkspace(AbNWorkspace workspace) {
        
        if(!optInitializers.isPresent()) {
            
            
            return;
        }

        File file = workspace.getFile();

        try {
            optInitializers.get().getRecentAbNWorkspaceFiles().addOrUpdateRecentlyOpenedFile(file);
        } catch (OAFRecentlyOpenedFileManager.RecentlyOpenedFileException rofe) {

        }
    }

    public AbNWorkspace createNewWorkspace(File file, String name) {
        
        if(!optInitializers.isPresent()) {
            // TODO: Exception
            
            return null;
        }
        
        AbNWorkspace workspace = AbNWorkspace.createNewWorkspaceFromCurrent(
                graphFrame, 
                name, 
                file, 
                optInitializers.get().getAbNParser());

        this.addOrUpdateWorkspace(workspace);

        return workspace;
        
    }
}
