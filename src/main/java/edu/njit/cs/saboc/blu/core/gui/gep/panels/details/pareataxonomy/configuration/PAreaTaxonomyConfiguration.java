package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.PartitionedNodeDisjointAbN;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyConfiguration extends PartitionedAbNConfiguration<PArea, Area> {
    
    public PAreaTaxonomyConfiguration(PAreaTaxonomy taxonomy) {
        super(taxonomy);
    }
    
    public PAreaTaxonomy getPAreaTaxonomy() {
        return (PAreaTaxonomy)super.getAbstractionNetwork();
    }
    
    public void setUIConfiguration(PAreaTaxonomyUIConfiguration uiConfig) {
        super.setUIConfiguration(uiConfig);
    }
    
    public void setTextConfiguration(PAreaTaxonomyTextConfiguration textConfig) {
        super.setTextConfiguration(textConfig);
    }
    
    @Override
    public PAreaTaxonomyUIConfiguration getUIConfiguration() {
        return (PAreaTaxonomyUIConfiguration)super.getUIConfiguration();
    }

    @Override
    public PAreaTaxonomyTextConfiguration getTextConfiguration() {
        return (PAreaTaxonomyTextConfiguration)super.getTextConfiguration();
    }

    @Override
    public int getPartitionedNodeLevel(Area node) {
        Area area = (Area)node;
        
        return area.getRelationships().size();
    }

    @Override
    public DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> getDisjointAbstractionNetworkFor(Area node) {
        return getDisjointPAreaTaxonomyFor((Area)node);
    }
    
    public DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> getDisjointPAreaTaxonomyFor(Area area) {
        
        DisjointAbNGenerator<PAreaTaxonomy<PArea>, PArea> generator = new DisjointAbNGenerator<>();
        
        DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy = 
                generator.generateDisjointAbstractionNetwork(
                        new DisjointPAreaTaxonomyFactory(), 
                        getPAreaTaxonomy(), 
                        area.getPAreas());

        return new PartitionedNodeDisjointAbN<>(disjointTaxonomy, area);
    }
}
