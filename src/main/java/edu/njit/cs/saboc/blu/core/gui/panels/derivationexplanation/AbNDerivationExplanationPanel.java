package edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation;

import edu.njit.cs.saboc.blu.core.gui.iconmanager.ImageManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Chris O
 */
public class AbNDerivationExplanationPanel extends JPanel {
    
    private final AbNDerivationExplanation explanation;
    
    private int currentLocation = 0;
    
    private final JLabel imageLabel;
    private final JEditorPane descriptionPane;
    
    
    private final JButton backButton = new JButton("Previous");
    private final JButton nextButton = new JButton("Next");
    
    private final JLabel currentLocationLabel;
    
    public AbNDerivationExplanationPanel(AbNDerivationExplanation explanation) {
        this.explanation = explanation;
        
        this.setLayout(new BorderLayout());
        
        
        JLabel titleLabel = new JLabel(String.format("%s Derivation Overview", 
                explanation.getConfiguration().getTextConfiguration().getAbNTypeName(false)));
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(titleLabel);
        
        
        this.add(northPanel, BorderLayout.NORTH);
        
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);
        
        imageLabel = new JLabel();
        imageLabel.setOpaque(false);
        
        descriptionPane = new JEditorPane();
        descriptionPane.setContentType("text/html");
        descriptionPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        descriptionPane.setPreferredSize(new Dimension(-1, 100));
        descriptionPane.setEditable(false);
        
        
        JPanel centerCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        centerCenterPanel.add(imageLabel);
        
        centerPanel.add(centerCenterPanel, BorderLayout.CENTER);
        centerPanel.add(descriptionPane, BorderLayout.SOUTH);
        
        this.add(centerPanel, BorderLayout.CENTER);
                
        
        JPanel southPanel = new JPanel(new BorderLayout());
        this.currentLocationLabel = new JLabel("test");
        FlowLayout pageNumberLayout = new FlowLayout(FlowLayout.CENTER);
        JPanel pageNumberPanel = new JPanel(pageNumberLayout);
        pageNumberPanel.add(currentLocationLabel);
        
        southPanel.add(backButton, BorderLayout.WEST);
        southPanel.add(nextButton, BorderLayout.EAST);
        southPanel.add(pageNumberPanel, BorderLayout.CENTER);
        
        this.nextButton.addActionListener((ae) -> {
            showNext();
        });
        
        this.nextButton.setIcon(ImageManager.getImageManager().getIcon("right-arrow.png"));
        this.nextButton.setHorizontalTextPosition(SwingConstants.LEFT);
        
        this.backButton.addActionListener( (ae) -> {
            showPrevious();
        });
        this.backButton.setIcon(ImageManager.getImageManager().getIcon("left-arrow.png"));
        

        
        
        
        this.add(southPanel, BorderLayout.SOUTH);
        
        initialize();
    }
    
    private void initialize() {
        this.backButton.setEnabled(false);
        
        this.nextButton.setEnabled(explanation.getEntries().size() > 1);
        
        this.currentLocation = 0;
        
        displayEntryAt(0);
    }
    
    private void showNext() {
        if(currentLocation < explanation.getEntries().size() - 1) {
            
            currentLocation++;
            
            displayEntryAt(currentLocation);
        }
            
        if (currentLocation < explanation.getEntries().size() - 1) {
            this.backButton.setEnabled(true);
        } else {
            this.nextButton.setEnabled(false);
        }
    }
    
    private void showPrevious() {
        if (currentLocation >= 1) {
            
            currentLocation--;
                    
            displayEntryAt(currentLocation);
            
            this.nextButton.setEnabled(true);
        } else {
            this.backButton.setEnabled(false);
        }
    }
    
    private void displayEntryAt(int index) {
        AbNDerivationExplanationEntry entry = explanation.getEntries().get(index);
        
        this.imageLabel.setIcon(entry.getImage());
        this.descriptionPane.setText(entry.getText());
        
        currentLocationLabel.setText(Integer.toString(currentLocation + 1));
    }
}
