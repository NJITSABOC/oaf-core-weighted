package edu.njit.cs.saboc.blu.core.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

/***
 * A dialog which displays information about the developers of BLUSNO/BLUOWL.
 * @author Chris
 */
public class AboutUsDialog extends JDialog {

    public AboutUsDialog(JFrame parentFrame) {
        super(parentFrame, true);
        
        setTitle("About the Ontology Abstraction Framework (OAF)");

        JPanel aboutPanel = new JPanel();

        JPanel centerPane = new JPanel();

        try {
            URL about = OAFMainFrame.class.getResource("/edu/njit/cs/saboc/blu/core/webpages/aboutOAF.html");
            
            JEditorPane aboutBLUSNOPane = new JEditorPane(about);
            aboutBLUSNOPane.setEditable(false);
            
            centerPane.add("About the OAF", aboutBLUSNOPane);
        }
        catch(Exception ble) {
            ble.printStackTrace();
        }

        aboutPanel.setLayout(new BorderLayout());

        aboutPanel.setBackground(Color.WHITE);

        aboutPanel.add(centerPane, BorderLayout.CENTER);

        getContentPane().add(aboutPanel, BorderLayout.CENTER);
        
        pack();
        setMinimumSize(getPreferredSize());
        
        this.setSize(768, 512);
        
        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setVisible(true);
    }
}
