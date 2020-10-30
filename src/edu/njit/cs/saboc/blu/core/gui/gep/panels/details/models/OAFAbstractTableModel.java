package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class OAFAbstractTableModel<T> extends AbstractTableModel {
    
    protected final ArrayList<T> items = new ArrayList<>();
    
    private final String[] columnNames;
    
    private Object[][] data = new Object[0][0];
            
    public OAFAbstractTableModel(String [] columnNames) {
        this.columnNames = columnNames;
    }

    protected abstract Object[] createRow(T item);
    
    public void setContents(ArrayList<T> items) {
        this.items.clear();
        this.items.addAll(items);
        
        data = new Object[items.size()][this.getColumnCount()];
        
        int r = 0;
        
        for(T concept : items) {
            data[r] = createRow(concept);
            r++;
        }
        
        this.fireTableDataChanged();
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        
        if(data.length == 0) {
            return Object.class;
        } else {
            
            if(getValueAt(0,c) != null) {
                return getValueAt(0, c).getClass();
            } else {
                return Object.class;
            }
        }
    }
    
    public T getItemAtRow(int row) {
        return items.get(row);
    }
}
