package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.SearchButtonResult;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Chris O
 */
public class DiffSearchResultCellRenderer extends JTextPane implements TableCellRenderer {
    
    public DiffSearchResultCellRenderer(JTable table) {
        super();
        
        this.setContentType("text/html");
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        doStyling(table, value, isSelected, hasFocus, row, column);
        
        if (value != null) {
            
            SearchButtonResult searchResult = (SearchButtonResult)value;
                
            String result = "";
            
            if(searchResult.getResult() instanceof DiffNodeInstance) {
                DiffNodeInstance instance = (DiffNodeInstance)searchResult.getResult();
                Node node = (Node)searchResult.getResult();
                
                String name = node.getName();
                String colorName = getColorFor(instance.getDiffNode().getChangeDetails().getNodeState());
                
                result = String.format("<html><font color='%s'>%s", 
                        colorName,
                        name);

            } else if(searchResult.getResult() instanceof Concept) {
                result = ((Concept)searchResult.getResult()).getName();
                
            } else {
                result = value.toString();
            }
            
            setText(result);
        } else {
            setText("");
        }

        return this;
    }
    
    private String getColorFor(ChangeState nodeState) {
        switch(nodeState) {
            case Introduced:
                return "green";
                
            case Removed:
                return "red";
                
            case Modified:
                return "yellow";
                
            case Unmodified:
            default: 
                
                return "black";
        }
    }
    
    protected void doStyling(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        setFont(table.getFont());

        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));

            if (table.isCellEditable(row, column)) {
                setForeground(UIManager.getColor("Table.focusCellForeground"));
                setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        } else {
            setBorder(new EmptyBorder(1, 2, 1, 2));
        }
    }
}
