package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate.FrameState;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager;

/**
 *
 * @author Chris O
 */
public interface AbNGraphFrameInitializers {
    
    public OAFRecentlyOpenedFileManager getRecentAbNWorkspaceFiles();
    public Ontology getSourceOntology();
    public AbNDerivationParser getAbNParser();
    
    
    public GraphFrameInitializer<PAreaTaxonomy, PAreaTaxonomyConfiguration> getPAreaTaxonomyInitializer(FrameState fs);
    public GraphFrameInitializer<PAreaTaxonomy, PAreaTaxonomyConfiguration> getAreaTaxonomyInitializer(FrameState fs);
    
    public GraphFrameInitializer<ClusterTribalAbstractionNetwork, TANConfiguration> getTANInitializer(FrameState fs);
    public GraphFrameInitializer<ClusterTribalAbstractionNetwork, TANConfiguration> getBandTANInitializer(FrameState fs);
    
    public GraphFrameInitializer<TargetAbstractionNetwork, TargetAbNConfiguration> getTargetAbNInitializer(FrameState fs);
    
    public GraphFrameInitializer<DisjointAbstractionNetwork, DisjointAbNConfiguration> getDisjointPAreaTaxonomyInitializer(FrameState fs);
    public GraphFrameInitializer<DisjointAbstractionNetwork, DisjointAbNConfiguration> getDisjointTANInitializer(FrameState fs);
}
