package edu.njit.cs.saboc.blu.core.gui.utils.renderers;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author hl395
 */
public class SingleLineTextRenderer extends JTextArea implements TableCellRenderer {

    public SingleLineTextRenderer(JTable table) {
        super();
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
            setText(value.toString());

            String line = value.toString();

            int height =(int)this.getFontMetrics(this.getFont()).getLineMetrics(line, null).getHeight();
            
            int currentHeight = table.getRowHeight(row);

            if(row >= 0 && row < table.getRowCount()) {
                table.setRowHeight(row, Math.max(height + 10, currentHeight));
            }
            
        } else {
            setText("");
        }
        
        this.setToolTipText((String) value);
        this.setWrapStyleWord(true);
        this.setLineWrap(true);
        
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
        if (table.getRowHeight(row) != getPreferredSize().height) {
            table.setRowHeight(row, getPreferredSize().height);
        }
        
        return this;
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
