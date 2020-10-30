package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class DiffPAreaTaxonomyConfiguration extends PAreaTaxonomyConfiguration {
    
    public DiffPAreaTaxonomyConfiguration(DiffPAreaTaxonomy taxonomy) {
        super(taxonomy);
    }
    
    @Override
    public DiffPAreaTaxonomy getPAreaTaxonomy() {
        return (DiffPAreaTaxonomy)super.getAbstractionNetwork();
    }
    
    public void setUIConfiguration(DiffPAreaTaxonomyUIConfiguration uiConfig) {
        super.setUIConfiguration(uiConfig);
    }
    
    public void setTextConfiguration(DiffPAreaTaxonomyTextConfiguration textConfig) {
        super.setTextConfiguration(textConfig);
    }
    
    @Override
    public DiffPAreaTaxonomyUIConfiguration getUIConfiguration() {
        return (DiffPAreaTaxonomyUIConfiguration)super.getUIConfiguration();
    }

    @Override
    public DiffPAreaTaxonomyTextConfiguration getTextConfiguration() {
        return (DiffPAreaTaxonomyTextConfiguration)super.getTextConfiguration();
    }
}
