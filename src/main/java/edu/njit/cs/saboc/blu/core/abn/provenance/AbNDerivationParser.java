package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AggregateAncestorDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AggregateDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AncestorDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.DisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.ExpandedDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.OverlappingNodeDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.PartitionedNodeDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.SimpleDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.SubsetDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.disjoint.DisjointSubjectSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateAncestorSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregatePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateRootSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AncestorSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.ExpandedSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.RelationshipSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.RootSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.SimplePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateAncestorSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateRootSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AncestorSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ExpandedSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.RootSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.SimpleClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.TANFromPartitionedNodeDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.TANFromSinglyRootedNodeDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateAncestorTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateDescendantTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AncestorTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.DescendantTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.ExpandedTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNFromPAreaDerivation;
import edu.njit.cs.saboc.blu.core.graph.tan.DisjointTANFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.ConceptLocationDataFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.PropertyLocationDataFactory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author hl395
 * @author Chris O
 * 
 */
public abstract class AbNDerivationParser {
    
    public static class AbNParseException extends Exception {
        public AbNParseException(String message) {
            super(message);
        }
    }

    private final Ontology<Concept> sourceOntology;
    private final ConceptLocationDataFactory conceptFactory;
    private final PropertyLocationDataFactory propertyFactory;
    private final AbNDerivationFactory derivationFactory;

    public AbNDerivationParser(
            Ontology<Concept> ontology, 
            ConceptLocationDataFactory conceptFactory, 
            PropertyLocationDataFactory propertyFactory, 
            AbNDerivationFactory derivationFactory) {
        
        this.sourceOntology = ontology;
        this.conceptFactory = conceptFactory;
        this.propertyFactory = propertyFactory;
        this.derivationFactory = derivationFactory;
    }
    
    public PropertyLocationDataFactory getPropertyLocationFactory() {
        return propertyFactory;
    }

    public AbNDerivation parseDerivationHistory(JSONObject obj) throws AbNParseException {

        if(!obj.containsKey("ClassName")) {
            throw new AbNParseException("Derivation history type not specified.");
        }
        
        String className = obj.get("ClassName").toString();

        if (className.equalsIgnoreCase("SimplePAreaTaxonomyDerivation")) {
            return parseSimplePAreaTaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("RootSubtaxonomyDerivation")) {
            return parseRootSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("RelationshipSubtaxonomyDerivation")) {
            return parseRelationshipSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("ExpandedSubtaxonomyDerivation")) {
            return parseExpandedSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("AncestorSubtaxonomyDerivation")) {
            return parseAncestorSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateRootSubtaxonomyDerivation")) {
            return parseAggregateRootSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregatePAreaTaxonomyDerivation")) {
            return parseAggregatePAreaTaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubtaxonomyDerivation")) {
            return parseAggregateAncestorSubtaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("SimpleDisjointAbNDerivation")) {
            return parseSimpleDisjointAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("SubsetDisjointAbNDerivation")) {
            return parseSubsetDisjointAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("OverlappingNodeDisjointAbNDerivation")) {
            return parseOverlappingNodeDisjointAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("ExpandedDisjointAbNDerivation")) {
            return parseExpandedDisjointAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("AncestorDisjointAbNDerivation")) {
            return parseAncestorDisjointAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateDisjointAbNDerivation")) {
            return parseAggregateDisjointAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateAncestorDisjointAbNDerivation")) {
            return parseAggregateAncestorDisjointAbNDerivation(obj);
        } else if(className.equals("PartitionedNodeDisjointAbNDerivation")) {
            return parsePartitionedNodeDisjointAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("SimpleClusterTANDerivation")) {
            return parseSimpleClusterTANDerivation(obj);
        } else if (className.equalsIgnoreCase("RootSubTANDerivation")) {
            return parseRootSubTANDerivation(obj);
        } else if (className.equalsIgnoreCase("ExpandedSubTANDerivation")) {
            return parseExpandedSubTANDerivation(obj);
        } else if (className.equalsIgnoreCase("AncestorSubTANDerivation")) {
            return parseAncestorSubTANDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateTANDerivation")) {
            return parseAggregateTANDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateRootSubTANDerivation")) {
            return parseAggregateRootSubTANDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateAncestorSubTANDerivation")) {
            return parseAggregateAncestorSubTANDerivation(obj);
        } else if (className.equalsIgnoreCase("TANFromPartitionedNodeDerivation")) {
            return parseTANFromPartitionedNodeDerivation(obj);
        } else if (className.equalsIgnoreCase("TANFromSinglyRootedNodeDerivation")) {
            return parseTANFromSinglyRootedNodeDerivation(obj);
        } else if (className.equalsIgnoreCase("TargetAbNDerivation")) {
            return parseTargetAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("ExpandedTargetAbNDerivation")) {
            return parseExpandedTargetAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("AggregateTargetAbNDerivation")) {
            return parseAggregateTargetAbNDerivation(obj);
        } else if (className.equalsIgnoreCase("AncestorTargetAbN")) {
            return parseAncestorTargetAbNDerivation(obj);
        } else if(className.equalsIgnoreCase("DescendantTargetAbN")) {
            return parseDescendantTargetAbNDerivation(obj);
        } else if(className.equalsIgnoreCase("AggregateAncestorTargetAbN")) {
            return parseAggregateAncestorTargetAbNDerivation(obj);
        } else if(className.equalsIgnoreCase("AggregateDescendantTargetAbN")) {
            return parseAggregateDescendantTargetAbNDerivation(obj);
        } else if(className.equalsIgnoreCase("TargetAbNFromPAreaDerivation")) {
            return parseTargetAbNFromPAreaDerivation(obj);
        } else if(className.equalsIgnoreCase("DisjointSubjectSubtaxonomy")) {
            return parseDisjointSubjectSubtaxonomyDerivation(obj);
        } else {
            throw new AbNParseException("Unknown derivation history type specified");
        }
    }
    
    protected Concept getRoot(JSONObject obj, String key) throws AbNParseException {
        if(!obj.containsKey(key)) {
            throw new AbNParseException("Root ID not specified.");
        }
        
        String conceptID = obj.get(key).toString();
        
        Set<Concept> root = conceptFactory.getConceptsFromIds(new ArrayList<>(Arrays.asList(conceptID)));
        
        if(root.isEmpty()) {
            throw new AbNParseException("Concept with given root ID does not exist.");
        }
        
        return root.iterator().next();
    }
    
    protected Concept getRoot(JSONObject obj) throws AbNParseException {
        return getRoot(obj, "ConceptID");
    }
    
    protected int getBound(JSONObject obj) throws AbNParseException {
        if (!obj.containsKey("Bound")) {
            throw new AbNParseException("Aggregate bound not specified.");
        }
        
        try {
            return Integer.parseInt(obj.get("Bound").toString());
        } catch (NumberFormatException nfe) {
            throw new AbNParseException("Incorrectly formatted aggregate bound");
        }
    }
    
    protected boolean isWeightedAggregated(JSONObject obj) throws AbNParseException{
        if (!obj.containsKey("isWeightedAggregated")) {
            throw new AbNParseException("Weighted Aggregate not specified.");
        }
        
        System.out.println("isWeightedAggregated: " + obj.get("isWeightedAggregated"));
        
        return Boolean.valueOf(obj.get("isWeightedAggregated").toString());   
    }
    
    protected int getAutoBound(JSONObject obj) throws AbNParseException {
        if (!obj.containsKey("AutoScaleBound")) {
            throw new AbNParseException("Auto Scale bound not specified.");
        }
        
        try {
            return Integer.parseInt(obj.get("AutoScaleBound").toString());
        } catch (NumberFormatException nfe) {
            throw new AbNParseException("Incorrectly formatted Auto Scale bound");
        }
    }
      
    protected boolean isAutoScaled(JSONObject obj) throws AbNParseException{
        if (!obj.containsKey("isAutoScaled")) {
            throw new AbNParseException("Auto Scale not specified.");
        }
        
        System.out.println("isAutoScaled: " + obj.get("isAutoScaled"));
        
        return Boolean.valueOf(obj.get("isAutoScaled").toString());   
    }
    
    
    protected <T extends AbNDerivation> T getBaseAbNDerivation(JSONObject obj, String key) throws AbNParseException {
        
        if (!obj.containsKey(key)) {
            throw new AbNParseException("Base AbN Derivation not specified.");
        }

        return (T) this.parseDerivationHistory((JSONObject)obj.get(key));
    }

    protected <T extends AbNDerivation> T getBaseAbNDerivation(JSONObject obj) throws AbNParseException {
        return this.getBaseAbNDerivation(obj, "BaseDerivation");
    }

    public SimplePAreaTaxonomyDerivation parseSimplePAreaTaxonomyDerivation(JSONObject obj) throws AbNParseException {
        return parseSimplePAreaTaxonomyDerivation(obj, derivationFactory.getPAreaTaxonomyFactory());
    }
    
    public SimplePAreaTaxonomyDerivation parseSimplePAreaTaxonomyDerivation(JSONObject obj, PAreaTaxonomyFactory factory) throws AbNParseException {
        
        SimplePAreaTaxonomyDerivation result = new SimplePAreaTaxonomyDerivation(
                getRoot(obj), 
                factory);
        
        return result;
    }

    public RootSubtaxonomyDerivation parseRootSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        return new RootSubtaxonomyDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }

    public RelationshipSubtaxonomyDerivation parseRelationshipSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("PropertyIDs")) {
           throw new AbNParseException("Property IDs not specified.");
        }
        
        ArrayList<String> propertyIds = new ArrayList<>();
        JSONArray propertyIdsJSON = (JSONArray)obj.get("PropertyIDs");
        
        propertyIdsJSON.forEach( (propertyIdStr) -> {
            propertyIds.add(propertyIdStr.toString());
        });
        
        if(propertyIds.isEmpty()) {
            throw new AbNParseException("No property IDs specified.");
        }

        Set<InheritableProperty> selectedProperties = propertyFactory.getPropertiesFromIds(propertyIds);
        
        if(selectedProperties.isEmpty()) {
            throw new AbNParseException("No properties with given IDs found.");
        }
        
        if(propertyIds.size() != selectedProperties.size()) {
            // TODO: Warning?
        }

        return new RelationshipSubtaxonomyDerivation(getBaseAbNDerivation(obj), selectedProperties);
    }

    public ExpandedSubtaxonomyDerivation parseExpandedSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        return new ExpandedSubtaxonomyDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }

    public AncestorSubtaxonomyDerivation parseAncestorSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        return new AncestorSubtaxonomyDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }

    public AggregateRootSubtaxonomyDerivation parseAggregateRootSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregateRootSubtaxonomyDerivation(
                getBaseAbNDerivation(obj), 
                aggregatedProperty, 
                getRoot(obj) 
                );
    }

    public AggregatePAreaTaxonomyDerivation parseAggregatePAreaTaxonomyDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregatePAreaTaxonomyDerivation(getBaseAbNDerivation(obj), aggregatedProperty);
    }

    public AggregateAncestorSubtaxonomyDerivation parseAggregateAncestorSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregateAncestorSubtaxonomyDerivation(getBaseAbNDerivation(obj),aggregatedProperty, getRoot(obj));
    }

    public SimpleDisjointAbNDerivation parseSimpleDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("RootIDs")) {
            throw new AbNParseException("Root IDs not specified.");
        }
     
        JSONArray rootIdsJSON = (JSONArray)obj.get("RootIDs");
        
        ArrayList<String> rootIdStrs = new ArrayList<>();
        rootIdsJSON.forEach( (rootIdJSON) -> {
            rootIdStrs.add(rootIdJSON.toString());
        });
        
        Set<Concept> roots = conceptFactory.getConceptsFromIds(rootIdStrs);
                
        if(roots.isEmpty()) {
            throw new AbNParseException("No concepts with given IDs found.");
        }
        
        if(roots.size() != rootIdStrs.size()) {
            // TODO: Warning?
        }

        return new SimpleDisjointAbNDerivation(new DisjointPAreaTaxonomyFactory(), getBaseAbNDerivation(obj), roots);
    }

    public SubsetDisjointAbNDerivation parseSubsetDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
       
        if(!obj.containsKey("RootNodeIDs")) {
            throw new AbNParseException("Root IDs not specified.");
        }

        DisjointAbNDerivation sourceDisjointAbNDerivation = (DisjointAbNDerivation)getBaseAbNDerivation(obj);
        
        JSONArray rootIdsJSON =  (JSONArray)obj.get("RootNodeIDs");
        
        DisjointAbstractionNetwork<?, ?, ?> disjointAbN = sourceDisjointAbNDerivation.getAbstractionNetwork(sourceOntology);
        
        Map<String, SinglyRootedNode> rootIds = new HashMap<>();
        disjointAbN.getParentAbstractionNetwork().getNodes().forEach( (node) -> {
            rootIds.put(node.getRoot().getIDAsString().toLowerCase(), node);
        });
        
        Set<SinglyRootedNode> subSet = new HashSet<>();
        
        rootIdsJSON.forEach( (rootIDJSON) -> {
            String rootIdStr = rootIDJSON.toString().toLowerCase();
            
            if(rootIds.containsKey(rootIdStr)) {
                subSet.add(rootIds.get(rootIdStr));
            }
        });
        
        if(subSet.isEmpty()) {
            throw new AbNParseException("Root nodes with given IDs found.");
        }
        
        if(subSet.size() != rootIdsJSON.size()) {
            // TODO: Warning?
        }
        
        SubsetDisjointAbNDerivation result = new SubsetDisjointAbNDerivation(sourceDisjointAbNDerivation, subSet);
        
        return result;
    }

    public OverlappingNodeDisjointAbNDerivation parseOverlappingNodeDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("OverlappingNodeRootId")) {
            throw new AbNParseException("Overlapping node ID not specified.");
        }
        
        DisjointAbNDerivation sourceDisjointAbNDerivation = (DisjointAbNDerivation)getBaseAbNDerivation(obj);
        
        String overlappingNodeRootIdStr = ((String)obj.get("OverlappingNodeRootId")).toLowerCase();
        
        DisjointAbstractionNetwork<?, ?, ?> disjointAbN = sourceDisjointAbNDerivation.getAbstractionNetwork(sourceOntology);
        
        Optional<?> theNode = disjointAbN.getParentAbstractionNetwork().getNodes().stream().filter( 
            (node) -> {
                return node.getRoot().getIDAsString().toLowerCase().equals(overlappingNodeRootIdStr); 
            }).findAny();
        
        if(!theNode.isPresent()) {
            throw new AbNParseException("Overlapping node with specified ID not found.");
        }

        SinglyRootedNode overlappingNode = (SinglyRootedNode)theNode.get();

        return new OverlappingNodeDisjointAbNDerivation(sourceDisjointAbNDerivation, overlappingNode);
    }
    
    public PartitionedNodeDisjointAbNDerivation parsePartitionedNodeDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
        
        if (!obj.containsKey("NodeName")) {
            throw new AbNParseException("Partitioned node not specified.");
        }

        AbNDerivation<PartitionedAbstractionNetwork<?, ?>> parentAbNDerivation = getBaseAbNDerivation(obj);

        PartitionedAbstractionNetwork<?, ?> partitionedAbN = 
                (PartitionedAbstractionNetwork<?, ?>) parentAbNDerivation.getAbstractionNetwork(sourceOntology);

        String partitionedNodeName = (String) obj.get("NodeName");

        Optional<?> node = partitionedAbN.getBaseAbstractionNetwork().getNodes().stream().filter( (partitionedNode) -> {
            return partitionedNode.getName().equalsIgnoreCase(partitionedNodeName);
        }).findAny();

        if (!node.isPresent()) {
            throw new AbNParseException("Partitioned node not found.");
        }
        
        return new PartitionedNodeDisjointAbNDerivation(
                new DisjointTANFactory(), 
                parentAbNDerivation, 
                (PartitionedNode)node.get());
    }
    
    public DisjointSubjectSubtaxonomyDerivation parseDisjointSubjectSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {

        if (!obj.containsKey("AreaName")) {
            throw new AbNParseException("Area not specified.");
        }

        PAreaTaxonomyDerivation parentAbNDerivation = getBaseAbNDerivation(obj);

        PAreaTaxonomy<PArea> taxonomy = parentAbNDerivation.getAbstractionNetwork(sourceOntology);

        String areaName = (String) obj.get("AreaName");

        Optional<Area> node = taxonomy.getAreaTaxonomy().getNodes().stream().filter( (area) -> {
            Area a = (Area)area;
            
            return a.getName().equalsIgnoreCase(areaName);
        }).findAny();

        if (!node.isPresent()) {
            throw new AbNParseException("Area not found.");
        }

        return new DisjointSubjectSubtaxonomyDerivation(
                parentAbNDerivation,
                node.get(),
                new DisjointPAreaTaxonomyFactory());
    }

    public ExpandedDisjointAbNDerivation parseExpandedDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
        return new ExpandedDisjointAbNDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }

    public AncestorDisjointAbNDerivation parseAncestorDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
        return new AncestorDisjointAbNDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }

    public AggregateDisjointAbNDerivation parseAggregateDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregateDisjointAbNDerivation(getBaseAbNDerivation(obj), aggregatedProperty);
    }

    public AggregateAncestorDisjointAbNDerivation parseAggregateAncestorDisjointAbNDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregateAncestorDisjointAbNDerivation(getBaseAbNDerivation(obj), aggregatedProperty, getRoot(obj));
    }

    public SimpleClusterTANDerivation parseSimpleClusterTANDerivation(JSONObject obj) throws AbNParseException {
        return this.parseSimpleClusterTANDerivation(obj, derivationFactory.getTANFactory());
    }
    
    public SimpleClusterTANDerivation parseSimpleClusterTANDerivation(JSONObject obj, TANFactory factory) throws AbNParseException {
        
        if(!obj.containsKey("ConceptIDs")) {
            throw new AbNParseException("Concept IDs not specified.");
        }
     
        JSONArray rootIdsJSON = (JSONArray)obj.get("ConceptIDs");
        
        ArrayList<String> rootIdStrs = new ArrayList<>();
        rootIdsJSON.forEach( (rootIdJSON) -> {
            rootIdStrs.add(rootIdJSON.toString());
        });
        
        Set<Concept> roots = conceptFactory.getConceptsFromIds(rootIdStrs);
                
        if(roots.isEmpty()) {
            throw new AbNParseException("No concepts with given IDs found.");
        }
        
        if(roots.size() != rootIdStrs.size()) {
            // TODO: Warning?
        }

        return new SimpleClusterTANDerivation(roots, factory);
    }

    public RootSubTANDerivation parseRootSubTANDerivation(JSONObject obj) throws AbNParseException {
        return new RootSubTANDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }

    public ExpandedSubTANDerivation parseExpandedSubTANDerivation(JSONObject obj) throws AbNParseException {
        return new ExpandedSubTANDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }

    public AncestorSubTANDerivation parseAncestorSubTANDerivation(JSONObject obj) throws AbNParseException {
        return new AncestorSubTANDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }

    public AggregateTANDerivation parseAggregateTANDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
         return new AggregateTANDerivation(getBaseAbNDerivation(obj), aggregatedProperty);
    }

    public AggregateRootSubTANDerivation parseAggregateRootSubTANDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregateRootSubTANDerivation(getBaseAbNDerivation(obj), aggregatedProperty, getRoot(obj));
    }

    public AggregateAncestorSubTANDerivation parseAggregateAncestorSubTANDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregateAncestorSubTANDerivation(getBaseAbNDerivation(obj), aggregatedProperty, getRoot(obj));
    }

    public TANFromPartitionedNodeDerivation parseTANFromPartitionedNodeDerivation(JSONObject obj) throws AbNParseException {

        if(!obj.containsKey("NodeName")) {
            throw new AbNParseException("Partitioned node not specified.");
        }
        
        AbNDerivation parentAbNDerivation = getBaseAbNDerivation(obj);
        
        PartitionedAbstractionNetwork<?, ?> partitionedAbN = 
                (PartitionedAbstractionNetwork<?, ?>)parentAbNDerivation.getAbstractionNetwork(sourceOntology);
        
        String partitionedNodeName = (String) obj.get("NodeName");

        Optional<?> node = partitionedAbN.getBaseAbstractionNetwork().getNodes().stream().filter( (partitionedNode) -> {
            return partitionedNode.getName().equalsIgnoreCase(partitionedNodeName);
        }).findAny();

        if (!node.isPresent()) {
            throw new AbNParseException("Partitioned node not found.");
        }
       
        return new TANFromPartitionedNodeDerivation(
                parentAbNDerivation, 
                derivationFactory.getTANFactory(), 
                (PartitionedNode)node.get());
    }

    public TANFromSinglyRootedNodeDerivation parseTANFromSinglyRootedNodeDerivation(JSONObject obj) throws AbNParseException {
        return new TANFromSinglyRootedNodeDerivation(
                getBaseAbNDerivation(obj), 
                derivationFactory.getTANFactory(), 
                getRoot(obj));
    }

    public abstract TargetAbNDerivation parseTargetAbNDerivation(JSONObject obj) throws AbNParseException;

    public <T extends AbNDerivation> ExpandedTargetAbNDerivation parseExpandedTargetAbNDerivation(JSONObject obj) throws AbNParseException {
        return new ExpandedTargetAbNDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }

    public <T extends AbNDerivation> AggregateTargetAbNDerivation parseAggregateTargetAbNDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregateTargetAbNDerivation(
                getBaseAbNDerivation(obj), 
                aggregatedProperty);
    }
    
    public <T extends AbNDerivation> AncestorTargetAbNDerivation parseAncestorTargetAbNDerivation(JSONObject obj) throws AbNParseException {
        return new AncestorTargetAbNDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }
    
    public <T extends AbNDerivation> DescendantTargetAbNDerivation parseDescendantTargetAbNDerivation(JSONObject obj) throws AbNParseException {
        return new DescendantTargetAbNDerivation(getBaseAbNDerivation(obj), getRoot(obj));
    }
    
    public <T extends AbNDerivation> AggregateAncestorTargetAbNDerivation parseAggregateAncestorTargetAbNDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregateAncestorTargetAbNDerivation(
                getBaseAbNDerivation(obj), 
                aggregatedProperty, 
                getRoot(obj));
    }
    
    public <T extends AbNDerivation> AggregateDescendantTargetAbNDerivation parseAggregateDescendantTargetAbNDerivation(JSONObject obj) throws AbNParseException {
        AggregatedProperty aggregatedProperty = new AggregatedProperty(getBound(obj),isWeightedAggregated(obj), getAutoBound(obj), isAutoScaled(obj));
        return new AggregateDescendantTargetAbNDerivation(
                getBaseAbNDerivation(obj), 
                aggregatedProperty, 
                getRoot(obj));
    }
    
    public <T extends AbNDerivation> TargetAbNFromPAreaDerivation parseTargetAbNFromPAreaDerivation(JSONObject obj) throws AbNParseException {
        return new TargetAbNFromPAreaDerivation(
                getBaseAbNDerivation(obj, "TargetAbNDerivation"), 
                getBaseAbNDerivation(obj, "PAreaTaxonomyDerivation"));
    }
}
