package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PopupToggleButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.BaseAbNSearchButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 *
 * @author CHris O
 */
public abstract class TaskBarPanel extends JPanel {
    
    private final MultiAbNGraphFrame graphFrame;
    
    private final JLabel abnMetricsLabel = new JLabel();
    
    private final JPanel menuPanel = new JPanel();
    
    private final JPanel otherOptionsPanel = new JPanel();

    private final JPanel toggleButtonPanel = new JPanel();

    private final ArrayList<PopupToggleButton> toggleMenuButtons = new ArrayList<>();
    
    private final BaseAbNSearchButton abnSearchBtn;
    
    public TaskBarPanel(
            MultiAbNGraphFrame graphFrame,
            AbNConfiguration config) {
        
        this.graphFrame = graphFrame;

        add(new JSeparator(SwingConstants.VERTICAL));
        add(Box.createHorizontalStrut(5));
        
        add(otherOptionsPanel);
        
        add(new JSeparator(SwingConstants.VERTICAL));
        add(Box.createHorizontalStrut(5));

        add(abnMetricsLabel);

        add(new JSeparator(SwingConstants.VERTICAL));
        add(Box.createHorizontalStrut(5));

        JButton goToRootBtn = new JButton("Reset View");

        goToRootBtn.setToolTipText("Click to return to the root of this abstraction network.");

        goToRootBtn.addActionListener((ae) -> {
            graphFrame.getAbNExplorationPanel().getDisplayPanel().getAutoScroller().resetDisplay();
        });


        add(goToRootBtn);

        add(new JSeparator(SwingConstants.VERTICAL));
        add(Box.createHorizontalStrut(5));

        add(toggleButtonPanel);
        
        this.abnSearchBtn = getAbNSearchButton(config);
        
        this.addToggleableButtonToMenu(abnSearchBtn);

        displayAbNMetrics(config);

        add(menuPanel, BorderLayout.CENTER);
    }
    
    public MultiAbNGraphFrame getGraphFrame() {
        return graphFrame;
    }
    
    public void clear() {
        this.disposeAllPopupButtons();
    }
    
    protected abstract BaseAbNSearchButton getAbNSearchButton(AbNConfiguration config);
    
    protected abstract String getAbNMetricsLabel(AbNConfiguration config);
    
    private void displayAbNMetrics(AbNConfiguration config) {
        abnMetricsLabel.setText(getAbNMetricsLabel(config));
    }

    public final void disposeAllPopupButtons() {
        toggleMenuButtons.forEach((button) -> {
            button.disposePopup();
        });
    }
    
    public final void closeAllPopupButtons() {
        toggleMenuButtons.forEach((button) -> {
            button.closePopup();
        });
    }
    
    public final void closeAllPopupButtonsException(PopupToggleButton btn) {
        toggleMenuButtons.stream().filter( (button) -> {
            return button != btn;
        }).forEach( (button) -> {
            button.closePopup();
        });
    }
    
    public final void updatePopupLocations(Dimension parentFrameSize) {
        toggleMenuButtons.forEach((button) -> {
            if (button.isSelected()) {
                button.updatePopupLocation(parentFrameSize);
            }
        });
    }
     
    public final void addOtherOptionsComponent(JComponent component) {
        this.otherOptionsPanel.add(component);
        
        otherOptionsPanel.revalidate();
        otherOptionsPanel.repaint();
    }
    
    public void removeOtherOptionsComponent(JComponent component) {
        this.otherOptionsPanel.remove(component);
        
        otherOptionsPanel.revalidate();
        otherOptionsPanel.repaint();
    }

    public final void addToggleableButtonToMenu(final PopupToggleButton button) {
        toggleMenuButtons.add(button);
        toggleButtonPanel.add(button);
        
        button.setTaskBarPanel(this);
    }
}
