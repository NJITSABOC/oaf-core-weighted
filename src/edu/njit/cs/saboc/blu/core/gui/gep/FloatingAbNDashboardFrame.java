package edu.njit.cs.saboc.blu.core.gui.gep;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public class FloatingAbNDashboardFrame extends JInternalFrame {
    
    private final AbNExplorationPanel abnExplorationPanel;
    
    private final AbNDashboardPanel dashboardPanel;
    
    private final JToggleButton btnShowFullDetails;
    
    public FloatingAbNDashboardFrame(
            AbNExplorationPanel abnExplorationPanel, 
            AbNDashboardPanel dashboardPanel) {
        
        super("Dashboard", true, false, true, true);
        
        this.abnExplorationPanel = abnExplorationPanel;
        this.dashboardPanel = dashboardPanel;
        
        JPanel internalPanel = new JPanel(new BorderLayout());
        
        internalPanel.add(dashboardPanel, BorderLayout.CENTER);
        
        btnShowFullDetails = new JToggleButton("Show More Details");
        
        btnShowFullDetails.addActionListener( (ae) -> { 
            if(btnShowFullDetails.isSelected()) {
                showFull();
                btnShowFullDetails.setText("Show Less Details");
            } else {
                showCompact();
                btnShowFullDetails.setText("Show More Details");
            }
        });
        
        btnShowFullDetails.setSelected(false);
        
        JPanel southPanel = new JPanel();
        
        southPanel.add(btnShowFullDetails);
        
        internalPanel.add(southPanel, BorderLayout.SOUTH);
        
        this.add(internalPanel);

        this.setSize(520, 700);
        
        this.setMinimumSize(new Dimension(520, 150));
        
        showCompact();
        
        /*
        JComponent titlePanel = (BasicInternalFrameTitlePane)((BasicInternalFrameUI)getUI()).getNorthPane();
        titlePanel.setPreferredSize(new Dimension(-1, 10));
        titlePanel.setBackground(new Color(100, 100, 255));
        */
        
        this.setFrameIcon(null);
        
        this.setVisible(true);
    }
    
    private void showCompact() {
        this.setSize(this.getWidth(), Math.min(this.getHeight(), 300));
        
        dashboardPanel.setShowCompact(true);
        
        relocate();
    }
    
    private void showFull() {
        this.setSize(this.getWidth(), Math.min(700, abnExplorationPanel.getHeight()-20));
        
        dashboardPanel.setShowCompact(false);
        
        if(this.getY() + this.getHeight() > abnExplorationPanel.getHeight()) {
            
            int offset = abnExplorationPanel.getHeight() - this.getHeight() - 20; //original:-20
            
            this.setLocation(this.getX(), offset);
        }
        
        relocate();
    }
    
    private void relocate(){
        
        if (this.getX() + this.getWidth()> abnExplorationPanel.getWidth()) {
            this.setLocation(abnExplorationPanel.getWidth()-this.getWidth(),this.getY());
        }

        if (this.getY() + this.getHeight()> abnExplorationPanel.getHeight()) {
            this.setLocation(this.getX(), abnExplorationPanel.getHeight()-this.getHeight());
        }
       
        if (this.getX()<0) {
                    this.setLocation(0, this.getY());
                }

        if (this.getY()<0) {
            this.setLocation(this.getX(), 0);
        }
    
    }
}
