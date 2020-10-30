package edu.njit.cs.saboc.blu.core.gui.workspace;

import java.io.File;

/**
 *
 * @author Chris O
 */
public abstract class OAFWorkspace {
    
    private String name;
    private final File file;
    
    public OAFWorkspace(File file) {
        
        this.file = file;
    }
    
    protected void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public File getFile() {
        return file;
    }
    
    public boolean hasFile() {
        return file != null;
    }

    public abstract void loadWorkspace();
    public abstract void saveWorkspace();
}
