package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;

/**
 *
 * @author Chris O
 */
public abstract class DisjointTANTextConfiguration extends DisjointAbNTextConfiguration<DisjointNode<Cluster>> {

    private final DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN;
    
    public DisjointTANTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig, 
            DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN) {
        
        super(ontologyEntityNameConfig, disjointTAN);

        this.disjointTAN = disjointTAN;
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if (plural) {
            return "Disjoint Tribal Abstraction Networks";
        } else {
            return "Disjoint Tribal Abstraction Network";
        }
    }

    @Override
    public String getNodeTypeName(boolean plural) {
        if (plural) {
            return "Disjoint Clusters";
        } else {
            return "Disjoint Cluster";
        }
    }

    @Override
    public String getOverlappingNodeTypeName(boolean plural) {
        if (plural) {
            return "Clusters";
        } else {
            return "Cluster";
        }
    }

    @Override
    public String getNodeHelpDescription(DisjointNode<Cluster> node) {
        StringBuilder helpDescription = new StringBuilder();

        helpDescription.append("<html>A <b>disjoint cluster</b> is a summary of a hierarchy of <conceptTypeName count=2> in a cluster tribal abstraction network band. "
                + "Each <conceptTypeName count=2> is summarized by exactly one disjoint cluster.");

        helpDescription.append("A <b>basis</b> (non-overlapping) disjoint cluster is a disjoint cluster that summarizes all of the <conceptTypeName count=2> "
                + "that are summarized by exactly one cluster in a complete cluster tribal abstraction network. "
                + "Basis disjoint clusters are assigned one color and are named after the root concept of this cluster.");

        helpDescription.append("An <b>overlapping</b> disjoint cluster is a disjoint cluster that summarizes a set of <conceptTypeName count=2> that are summarized "
                + "by two or more clusters in a complete cluster tribal abstraction network. Overlapping disjoint clusters are color coded according to the "
                + "clusters in which their <conceptTypeName count=2> overlap and are named after the <conceptTypeName count=2> that is the point of intersection between the clusters. "
                + "There may be multiple points of intersection, thus, there may be multiple similarly color coded overlapping disjoint clusters.");
        
        AbNTextFormatter formatter = new AbNTextFormatter(this);

        return formatter.format(helpDescription.toString());
    }

    
    @Override
    public String getAbNName() {
        return disjointTAN.getDerivation().getName();
    }

    @Override
    public String getAbNHelpDescription() {

        String desc = "<html>A <b>disjoint tribal abstraction network (disjoint TAN)</b> is an abstraction network, derived from a portion of a band from a regular "
                + "tribal abstraction network, that partitions <conceptTypeName count=2> into disjoint groups called <b>disjoint clusters</b>. "
                + "Disjoint clusters are organized into a hierarchy based on the underlying <conceptTypeName> hierarchy. Each <conceptTypeName> "
                + "is summarized by exactly one disjoint cluster in a disjoint tribal abstraction network. "
                + "<p>"
                + "A disjoint tribal abstraction network provides an accurate summary of the intersections in the <conceptTypeName> "
                + "hierarchy in a band, since <conceptTypeName count=2> may be summarized by multiple clusters in a regular "
                + "tribal abstraction network (<i>overlapping <conceptTypeName count=2></i>). "
                + "We note that partial-areas that do not contain any overlapping <conceptTypeName count=2> are not displayed in the disjoint tribal abstraction network."
                + "<p>"
                + "Disjoint clusters that do not contain any overlapping <conceptTypeName count=2> (called "
                + "<b>basis disjoint clusters</b> (or <b>non-overlapping disjoint clusters</b>), "
                + "are assigned a single color and are located at the top of the disjoint tribal abstraction network. Overlapping disjoint clusters,"
                + "which summarize one or more overlapping <conceptTypeName count=2>, are color coded according to the clusters their <conceptTypeName count=2> overlap between. Overlapping "
                + "disjoint clusters are organized into levels according to their degree of overlap, i.e., how many clusters their <conceptTypeName count=2> are summarized by.";
        
        AbNTextFormatter formatter = new AbNTextFormatter(this);

        return formatter.format(desc);
    }
}
