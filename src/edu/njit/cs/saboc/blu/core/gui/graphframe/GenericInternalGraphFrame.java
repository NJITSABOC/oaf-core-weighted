package edu.njit.cs.saboc.blu.core.gui.graphframe;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.BaseAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PopupToggleButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileFilter;

public abstract class GenericInternalGraphFrame<T extends AbstractionNetwork>
        extends JInternalFrame {

    private final JFrame parentFrame;

    private final AbNExplorationPanel gep;

    private final JTabbedPane tabbedPane = new JTabbedPane();

    private final JScrollPane scroller;
    private final JPanel scrollerContentPanel;

    private Optional<AbstractionNetworkGraph<T>> optCurrentGraph = Optional.empty();

    private final JLabel hierarchyInfoLabel = new JLabel();

    private final JPanel menuPanel = new JPanel();
    
    private final JPanel otherOptionsPanel = new JPanel();

    private final JPanel reportsButtonPanel = new JPanel();

    private final JPanel toggleButtonPanel = new JPanel();

    private ArrayList<PopupToggleButton> toggleMenuButtons = new ArrayList<>();
    
    private final AbNWarningManager warningManager;

    protected GenericInternalGraphFrame(JFrame parentFrame, String title) {
        
        super(title,
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        this.parentFrame = parentFrame;

        this.scrollerContentPanel = new JPanel(new BorderLayout());
        this.scroller = new JScrollPane(scrollerContentPanel);

        this.gep = new AbNExplorationPanel();
        this.gep.showLoading();
        
        this.warningManager = new AbNWarningManager();

        tabbedPane.addTab("Explore", gep);
        tabbedPane.addTab("Edit", scroller);
        tabbedPane.setEnabled(false);

        this.setSize(1200, 512);

        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                toggleMenuButtons.forEach((button) -> {
                    button.disposePopup();
                });

                gep.getDisplayPanel().kill();
            }

            public void internalFrameDeactivated(InternalFrameEvent e) {
                for (PopupToggleButton button : toggleMenuButtons) {
                    if (button.isSelected()) {
                        button.closePopup();
                    }
                }
            }

            public void internalFrameIconified(InternalFrameEvent e) {
                for (PopupToggleButton button : toggleMenuButtons) {
                    if (button.isSelected()) {
                        button.closePopup();
                    }
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                JInternalFrame frame = (JInternalFrame) e.getSource();

                for (PopupToggleButton button : toggleMenuButtons) {
                    if (button.isSelected()) {
                        button.updatePopupLocation(frame.getSize());
                    }
                }
            }

            public void componentMoved(ComponentEvent e) {
                JInternalFrame frame = (JInternalFrame) e.getSource();

                for (PopupToggleButton button : toggleMenuButtons) {
                    if (button.isSelected()) {
                        button.updatePopupLocation(frame.getSize());
                    }
                }
            }
        });

        menuPanel.add(new JSeparator(SwingConstants.VERTICAL));
        menuPanel.add(Box.createHorizontalStrut(5));
        
        menuPanel.add(otherOptionsPanel);
        
        menuPanel.add(new JSeparator(SwingConstants.VERTICAL));
        menuPanel.add(Box.createHorizontalStrut(5));

        menuPanel.add(reportsButtonPanel);

        menuPanel.add(new JSeparator(SwingConstants.VERTICAL));
        menuPanel.add(Box.createHorizontalStrut(5));

        menuPanel.add(hierarchyInfoLabel);

        menuPanel.add(new JSeparator(SwingConstants.VERTICAL));
        menuPanel.add(Box.createHorizontalStrut(5));

        JButton goToRootBtn = new JButton("Return to Root");

        goToRootBtn.setToolTipText("Click to return to the root of this abstraction network.");

        goToRootBtn.addActionListener((ae) -> {
            gep.getDisplayPanel().getAutoScroller().resetDisplay();
        });

        final JButton saveButton = new JButton("Save As Image");
        saveButton.setToolTipText("Save the current screen as a PNG image. Available only in Graph Exploration mode.");

        saveButton.addActionListener((ae) -> {
            saveCurrentView();
        });

        menuPanel.add(goToRootBtn);
        menuPanel.add(saveButton);

        menuPanel.add(new JSeparator(SwingConstants.VERTICAL));
        menuPanel.add(Box.createHorizontalStrut(5));

        menuPanel.add(toggleButtonPanel);

        tabbedPane.addChangeListener((ce) -> {
            if (tabbedPane.getSelectedIndex() == 1) {
                saveButton.setEnabled(true);
                saveButton.setToolTipText("Save the current screen as a PNG image.");
            } else {
                saveButton.setEnabled(false);
                saveButton.setToolTipText("Save the current screen as a PNG image. "
                        + "Available only in Graph Exploration mode.");
            }
        });

        saveButton.setEnabled(true);

        add(menuPanel, BorderLayout.NORTH);

        add(tabbedPane);

        setVisible(true);
    }

    public Optional<AbstractionNetworkGraph<T>> getGraph() {
        return optCurrentGraph;
    }

    public AbNExplorationPanel getAbNExplorationPanel() {
        return gep;
    }
    
    public JFrame getParentFrame() {
        return parentFrame;
    }
    
    public void addOtherOptionsComponent(JComponent component) {
        this.otherOptionsPanel.add(component);
        otherOptionsPanel.revalidate();
        otherOptionsPanel.repaint();
    }
    
    public void removeOtherOptionsComponent(JComponent component) {
        this.otherOptionsPanel.remove(component);
        otherOptionsPanel.revalidate();
        otherOptionsPanel.repaint();
    }

    public void addReportButtonToMenu(final AbstractButton button) {
        reportsButtonPanel.add(button);
        reportsButtonPanel.revalidate();
        reportsButtonPanel.repaint();
    }

    public void removeReportButtonFromMenu(final AbstractButton button) {
        reportsButtonPanel.remove(button);
        reportsButtonPanel.revalidate();
        reportsButtonPanel.repaint();
    }

    protected void addToggleableButtonToMenu(final PopupToggleButton button) {
        toggleMenuButtons.add(button);
        
        toggleButtonPanel.add(button);
    }

    public void focusOnComponent(final Component c) {

        SwingUtilities.invokeLater(() -> {

            if (!optCurrentGraph.isPresent()) {
                return;
            }

            AbstractionNetworkGraph<T> abnGraph = optCurrentGraph.get();

            int x1 = c.getX();
            int y1 = c.getY();

            if (c instanceof SinglyRootedNodeEntry) {
                SinglyRootedNodeEntry group = ((SinglyRootedNodeEntry) c);
                x1 = group.getAbsoluteX();
                y1 = group.getAbsoluteY();
            }

            int width1 = c.getWidth();
            int height1 = c.getHeight();
            int midPointX = x1 + width1 / 2;
            int midPointY = y1 + height1 / 2;
            int scrollXPos = midPointX - getWidth() / 2;
            int scrollYPos = midPointY - getHeight() / 2;

            if (scrollXPos < 0) {
                scrollXPos = 0;
            } else if (scrollXPos > abnGraph.getWidth() - getWidth()) {
                scrollXPos = abnGraph.getWidth() - getWidth();
            }

            if (scrollYPos < 0) {
                scrollYPos = 0;
            } else if (scrollYPos > abnGraph.getHeight() - getHeight()) {
                scrollYPos = abnGraph.getHeight() - getHeight();
            }

            scroller.getViewport().setViewPosition(new Point(scrollXPos, scrollYPos));

            abnGraph.repaint();
            gep.getDisplayPanel().getAutoScroller().navigateToPoint(new Point(midPointX, midPointY));
        });
    }

    protected void setHierarchyInfoText(String text) {
        hierarchyInfoLabel.setText(text);
    }

    public void displayAbstractionNetwork(
            AbstractionNetworkGraph graph,
            AbNPainter painter,
            AbNConfiguration gepConfiguration) {

        displayAbstractionNetwork(
                graph,
                painter,
                gepConfiguration,
                new BaseAbNExplorationPanelInitializer(warningManager));
    }

    public void displayAbstractionNetwork(
            AbstractionNetworkGraph<T> graph,
            AbNPainter painter,
            AbNConfiguration gepConfiguration,
            AbNExplorationPanelGUIInitializer initializer) {

        this.optCurrentGraph = Optional.of(graph);

        gep.showLoading();
        tabbedPane.setEnabled(false);

        SwingUtilities.invokeLater(() -> {

            gep.initialize(graph, gepConfiguration, painter, initializer);

            scrollerContentPanel.add(graph, BorderLayout.CENTER);

            scroller.revalidate();
            scroller.repaint();

            initializeTabs(graph, gepConfiguration);

            tabbedPane.setEnabled(true);
            
            tabbedPane.validate();
            tabbedPane.repaint();
        });
    }

    protected void initializeTabs(AbstractionNetworkGraph graph, AbNConfiguration gepConfiguration) {

        String abnTypeName = gepConfiguration.getTextConfiguration().getAbNTypeName(false);

        tabbedPane.setTitleAt(0, String.format("Explore %s", abnTypeName));
        tabbedPane.setTitleAt(1, String.format("Edit %s", abnTypeName));

        tabbedPane.setToolTipTextAt(0,
                String.format("<html><b>Explore %s</b> allows you to quickly<br>"
                        + "explore the %s. Additional information is displayed by selecting the different %s elements.",
                        abnTypeName,
                        abnTypeName.toLowerCase(),
                        abnTypeName.toLowerCase()));

        tabbedPane.setToolTipTextAt(1,
                String.format("<html><b>Edit %s Graph</b> allows you to edit some of the %s's layout and draw edges.", abnTypeName,
                        abnTypeName.toLowerCase()));
    }

    private void saveCurrentView() {
        final JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                return f.getName().endsWith(".png");
            }

            public String getDescription() {
                return "Portal Network Graphics (.png) Images";
            }
        });

        int returnVal = chooser.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            BufferedImage graphImage = gep.getDisplayPanel().getCurrentViewImage();

            try {
                String file = chooser.getSelectedFile().getAbsolutePath();

                if (!file.toLowerCase().endsWith(".png")) {
                    file += ".png";
                }

                ImageIO.write(graphImage, "png", new File(file));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
