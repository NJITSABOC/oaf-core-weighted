package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;


/**
 * Represents a type of change that affected an abstraction network
 * 
 * @author Chris
 */
public interface AbNChange {
    public String getChangeName(AbNTextConfiguration config);
    public String getChangeDescription(AbNTextConfiguration config);
}
