package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class MouseOverJTable extends JTable {

    private int rollOverRowIndex = -1;

    public MouseOverJTable(TableModel model) {
        super(model);
        
        RollOverListener lst = new RollOverListener();
        
        addMouseMotionListener(lst);
        addMouseListener(lst);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        
        Component c = super.prepareRenderer(renderer, row, column);
        
        if (isRowSelected(row)) {
            
            c.setForeground(getSelectionForeground());
            c.setBackground(getSelectionBackground());
            
        } else if (row == rollOverRowIndex) {
            
            c.setForeground(Color.BLACK);
            c.setBackground(Color.YELLOW);
            
        } else {
            
            c.setForeground(getForeground());
            c.setBackground(getBackground());
            
        }
        
        return c;
    }

    private class RollOverListener extends MouseInputAdapter {

        @Override
        public void mouseExited(MouseEvent e) {
            rollOverRowIndex = -1;
            repaint();
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
            int row = rowAtPoint(e.getPoint());
            
            if (row != rollOverRowIndex) {
                rollOverRowIndex = row;
                repaint();
            }          
        }
    }
}
