
package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.HashSet;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a disjoint abstraction network 
 * from a partitioned node in a partitioned abstraction network.
 * 
 * @author Chris O
 * @param <T>
 */
public class SimpleDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation {
    
    private final DisjointAbNFactory factory;
    
    private final AbNDerivation<PartitionedAbstractionNetwork> parentAbNDerivation;
    
    private final Set<Concept> sourceNodeRoots;
    
    public SimpleDisjointAbNDerivation(
            DisjointAbNFactory factory,
            AbNDerivation<PartitionedAbstractionNetwork> parentAbNDerivation, 
            Set<Concept> sourceNodeRoots) {
        
        super(factory);
        
        this.factory = factory;
        this.parentAbNDerivation = parentAbNDerivation;
        this.sourceNodeRoots = sourceNodeRoots;
    }
    
    public SimpleDisjointAbNDerivation(SimpleDisjointAbNDerivation derivedAbN) {
        this(derivedAbN.getFactory(), derivedAbN.getParentAbNDerivation(), derivedAbN.getSourceNodeRoots());
    }
    
    public Set<Concept> getSourceNodeRoots() {
        return sourceNodeRoots;
    }

    public AbNDerivation<PartitionedAbstractionNetwork> getParentAbNDerivation() {
        return parentAbNDerivation;
    }
    
    @Override
    public String getDescription() {
        return String.format("Disjointed %d Nodes", sourceNodeRoots.size());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        PartitionedAbstractionNetwork partitionedAbN = parentAbNDerivation.getAbstractionNetwork(ontology);
        
        Set<T> overlappingNodes = new HashSet<>();
        
        this.sourceNodeRoots.forEach( (concept) -> {
            overlappingNodes.addAll(partitionedAbN.getNodesWith(concept));
        });

        DisjointAbNGenerator generator = new DisjointAbNGenerator<>();

        return generator.generateDisjointAbstractionNetwork(
                factory,
                partitionedAbN,
                overlappingNodes);
    }
    @Override
    public String getName() {
        return String.format("%s (Disjoint)", parentAbNDerivation.getName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Disjoint %s", parentAbNDerivation.getAbstractionNetworkTypeName());
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "SimpleDisjointAbNDerivation");       
        result.put("BaseDerivation", parentAbNDerivation.serializeToJSON());   

        JSONArray arr = new JSONArray();
        
        sourceNodeRoots.forEach(root ->{
            arr.add(root.getIDAsString());
        });
        
        result.put("RootIDs", arr);
                
        return result;
        
    }
}
