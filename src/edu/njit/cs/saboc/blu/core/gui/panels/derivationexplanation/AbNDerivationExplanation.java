package edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class AbNDerivationExplanation {
    
    private final ArrayList<AbNDerivationExplanationEntry> entries = new ArrayList<>();
    
    private final AbNConfiguration config;
    
    public AbNDerivationExplanation(AbNConfiguration config) {
        this.config = config;
    }
    
    public AbNConfiguration getConfiguration() {
        return config;
    }
    
    protected void addDerivationExplanationEntry(AbNDerivationExplanationEntry entry) {
        this.entries.add(entry);
    }
    
    public ArrayList<AbNDerivationExplanationEntry> getEntries() {
        return entries;
    }
}
