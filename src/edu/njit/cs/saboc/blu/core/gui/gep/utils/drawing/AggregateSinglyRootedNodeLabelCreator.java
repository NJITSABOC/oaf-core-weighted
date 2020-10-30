package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 */
public class AggregateSinglyRootedNodeLabelCreator<T extends SinglyRootedNode> 
    extends SinglyRootedNodeLabelCreator<T> {

//    public String getCountStr(T node) {
//        AggregateNode aggregate = (AggregateNode) node;
//
//        if (aggregate.getAggregatedNodes().isEmpty()) {
//            return super.getCountStr(node);
//        } else {
//            return String.format("(%d) {%d}",
//                    node.getConceptCount(),
//                    aggregate.getAggregatedNodes().size());
//        }
//    }
    
    public String getCountStr(T node) {
        AggregateNode aggregate = (AggregateNode) node;
        T t = (T)aggregate.getAggregatedHierarchy().getRoot();

        if (aggregate.getAggregatedNodes().isEmpty()) {
            return super.getCountStr(node);
        } else {
            return String.format("(%d) {%d} [%d]",
                    node.getConceptCount(),
                    aggregate.getAggregatedNodes().size(),
                    t.getConceptCount());
        }
    }
}
