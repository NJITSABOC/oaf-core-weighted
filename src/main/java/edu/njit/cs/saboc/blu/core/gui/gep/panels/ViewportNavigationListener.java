package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.Viewport;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.NavigationPanel.NavigationPanelListener;

/**
 *
 * @author Chris O
 */
public class ViewportNavigationListener implements NavigationPanelListener {
    
    private final AbNDisplayPanel displayPanel;
    
    public ViewportNavigationListener(AbNDisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }

    @Override
    public void zoomLevelChanged(int zoomLevel) {
        displayPanel.getViewport().setZoomChecked(zoomLevel);
        displayPanel.requestRedraw();
    }

    @Override
    public void navigationButtonPressed(NavigationPanel.NavigationDirection direction) {
        final int MOVE_SPEED = 16;

        Viewport viewport = displayPanel.getViewport();
        
        switch (direction) {
            case Up:
                viewport.moveVertical(-MOVE_SPEED);
                break;

            case Down:
                viewport.moveVertical(MOVE_SPEED);
                break;

            case Left:
                viewport.moveHorizontal(-MOVE_SPEED);
                break;

            case Right:
                viewport.moveHorizontal(MOVE_SPEED);
                break;
        }

        displayPanel.requestRedraw();
    }
}