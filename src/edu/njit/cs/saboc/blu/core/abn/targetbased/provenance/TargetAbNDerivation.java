package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkFactory;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a target abstraction network 
 * between two hierarchies
 * 
 * @author Chris O
 */
public class TargetAbNDerivation extends AbNDerivation<TargetAbstractionNetwork> {
    
    private final TargetAbstractionNetworkFactory factory;
    
    private final Concept sourceHierarchyRoot;
    private final InheritableProperty propertyType;
    private final Concept targetHierarchyRoot;
    
    public TargetAbNDerivation(
            TargetAbstractionNetworkFactory factory,
            Concept sourceHierarchyRoot, 
            InheritableProperty propertyType, 
            Concept targetHierarchyRoot) {

        this.factory = factory;
        
        this.sourceHierarchyRoot = sourceHierarchyRoot;
        this.propertyType = propertyType;
        this.targetHierarchyRoot = targetHierarchyRoot;
    }
    
    public TargetAbNDerivation(TargetAbNDerivation targetAbN) {
        this(targetAbN.getFactory(), 
                targetAbN.getSourceHierarchyRoot(), 
                targetAbN.getPropertyType(), 
                targetAbN.getTargetHierarchyRoot());
    }

    public TargetAbstractionNetworkFactory getFactory() {
        return factory;
    }
    
    public Concept getSourceHierarchyRoot() {
        return sourceHierarchyRoot;
    }

    public InheritableProperty getPropertyType() {
        return propertyType;
    }

    public Concept getTargetHierarchyRoot() {
        return targetHierarchyRoot;
    }

    @Override
    public String getDescription() {
        return "Derived target abstraction network";
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        TargetAbstractionNetworkGenerator generator = new TargetAbstractionNetworkGenerator();

        TargetAbstractionNetwork<TargetGroup> targetAbN = generator.deriveTargetAbstractionNetwork(
                factory, 
                ontology.getConceptHierarchy().getSubhierarchyRootedAt(sourceHierarchyRoot), 
                propertyType, 
                ontology.getConceptHierarchy().getSubhierarchyRootedAt(targetHierarchyRoot));
        
        return targetAbN;
    }

    @Override
    public String getName() {
        return String.format("%s %s", propertyType.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Target Abstraction Network";
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "TargetAbNDerivation");       
        result.put("SourceRootID", sourceHierarchyRoot.getIDAsString());   
        result.put("PropertyID", propertyType.getIDAsString());
        result.put("TargetRootID", targetHierarchyRoot.getIDAsString());
        
        return result;
    }
}
