package edu.njit.cs.saboc.blu.core.gui.frame;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 * The main frame of the SNOMED Area Auditing tool. Contains all user interface
 * panels of the program.
 *
 * @author Chris
 */
public class OAFMainFrame extends JFrame {

    /**
     * *
     * File chooser for picking where to save an image of a graph.
     */
    private final JFileChooser pngFileChooser = new JFileChooser();
    

    private JInternalFrame abnSelectionFrame;
    /**
     * *
     * The main pane of the tool. All internal frames are added to this desktop
     * pane.
     */
    private JDesktopPane desktopPane = new JDesktopPane();

    /**
     * *
     * A combo box for selecting a hierarchy that is displayed at the top of the
     * BLUSNO frame.
     */
    private JComboBox hierarchyBox;

    /**
     * *
     * Returns the combo box that contains a list of hierarchies that is
     * displayed at the top of the screen.
     *
     * @return
     */
    public JComboBox getHierarchyBox() {
        return hierarchyBox;
    }
    
    /**
     * *
     * Constructs the main frame of the tool. 
     * 
     * @param abnSelectionFrameFactory
     */
    public OAFMainFrame(AbnSelectionFrameFactory abnSelectionFrameFactory) {
        
        setTitle("Ontology Abstraction Framework (OAF) by SABOC at NJIT");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

        final JMenuBar menu = new JMenuBar();

        /**
         * Menu for File option
         */
        JMenu file = new JMenu("File");
        menu.add(file);

        JMenuItem print = new JMenuItem("Print");
        print.setEnabled(false);
        file.add(print);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((ae) -> {
            Object[] options = {"Yes", "No"};
            
            int n = JOptionPane.showOptionDialog(OAFMainFrame.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            
            if (n == 0) {
                System.exit(0);
            }
        });

        file.add(exit);

        /**
         * Menu for Windows option
         */
        JMenu windows = new JMenu("Windows");
        menu.add(windows);

        JMenuItem cascade = new JMenuItem("Cascade");
        windows.add(cascade);

        JMenuItem horizontalcomp = new JMenuItem("Side-By-Side");
        windows.add(horizontalcomp);

        JMenuItem verticalcomp = new JMenuItem("Tile");
        windows.add(verticalcomp);

        /**
         * Menu for Options option
         */
        JMenu help = new JMenu("Help");

        JMenuItem helpItem = new JMenuItem("Manual");
        helpItem.setEnabled(false);
        help.add(helpItem);

        JMenuItem tutorialItem = new JMenuItem("Tutorial Video");
        tutorialItem.setEnabled(false);
        help.add(tutorialItem);

        JMenuItem aboutUs = new JMenuItem("About Us");
        help.add(aboutUs);

        aboutUs.addActionListener((ae) -> {
            new AboutUsDialog(OAFMainFrame.this);
        });

        menu.add(help);
        menu.setBorder(new BevelBorder(BevelBorder.RAISED));

        setJMenuBar(menu);

        cascade.addActionListener((e) -> {
            cascader(desktopPane);
        });

        verticalcomp.addActionListener((e) -> {
            tiler(desktopPane);
        });

        horizontalcomp.addActionListener((e) -> {
            sideBySide(desktopPane);
        });

        add(desktopPane);

        SwingUtilities.invokeLater(() -> {
            abnSelectionFrame = abnSelectionFrameFactory.createAbNSelectionFrame(OAFMainFrame.this);
            
            desktopPane.add(abnSelectionFrame);

            if (abnSelectionFrame.getWidth() > desktopPane.getWidth() || 
                    abnSelectionFrame.getHeight() > desktopPane.getHeight()) {
                
                abnSelectionFrame.setSize(desktopPane.getSize());
                abnSelectionFrame.setLocation(new Point(0, 0));
                
            } else {
                abnSelectionFrame.setLocation(
                        getWidth() / 2 - abnSelectionFrame.getWidth() / 2,
                        getHeight() / 2 - abnSelectionFrame.getHeight() + 300);
            }

        });

        pngFileChooser.setFileFilter(new FileFilter() {
            
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                return f.getName().toLowerCase().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "Portal Network Graphs (.png) Images";
            }
        });

        setVisible(true);
    }

    /**
     * *
     * Returns the file chooser for saving a graph as a PNG.
     *
     * @return A file chooser with a PNG filter.
     */
    public JFileChooser getImageFileChooser() {
        return pngFileChooser;
    }

    /**
     * *
     * Returns all non-iconified internal graph frames.
     *
     * @param frames An array of internal frames.
     * @return An array of non-iconified internal graph frames.
     */
    private JInternalFrame[] getGraphFrames(JInternalFrame[] frames) {
        ArrayList<JInternalFrame> graphFrames = new ArrayList<>();

        for (JInternalFrame frame : frames) {
            if (frame instanceof JInternalFrame && !frame.isIcon() && !frame.equals(abnSelectionFrame)) {
                graphFrames.add(frame);
            }
            else {
                frame.moveToBack();
            }
        }

        return graphFrames.toArray(new JInternalFrame[0]);
    }

    /**
     * *
     * Cascades a set of internal graph frames in a JDesktopPane at a specified
     * layer.
     *
     * @param desktopPane The desktop pane.
     * @param layer The layer.
     */
    public void cascader(JDesktopPane desktopPane, int layer) {
        JInternalFrame[] frames = desktopPane.getAllFramesInLayer(layer);

        if (frames.length == 0) {
            return;
        }

        cascader(frames, desktopPane.getBounds(), 24);
    }

    /**
     * *
     * Cascades all of the internal graph frames of a JDesktopPane
     *
     * @param desktopPane The desktop pane.
     */
    public void cascader(JDesktopPane desktopPane) {
        JInternalFrame[] frames = desktopPane.getAllFrames();
        
        if (frames.length == 0) {
            return;
        }

        cascader(frames, desktopPane.getBounds(), 24);
    }

    /**
     * *
     * Cascades a set of internal graph frames in a JDesktopPane within a set
     * boundary.
     *
     * @param frames The set of frames (may include non-graph frames)
     * @param dBounds The maximum bounds in the frame.
     * @param separation The amount of separation between each frame.
     */
    private void cascader(JInternalFrame[] frames, Rectangle dBounds, int separation) {
        frames = getGraphFrames(frames);

        int margin = frames.length * separation + separation;
        int width = dBounds.width - margin;
        int height = dBounds.height - margin;

        for (int i = 0; i < frames.length; i++) {
            frames[i].setBounds(separation + dBounds.x + i * separation,
                    separation + dBounds.y + i * separation,
                    width, height);
        }
    }

    /**
     * *
     * Tiles all of the internal graph frames of a JDesktopPane at a specified
     * layer.
     *
     * @param desktopPane The desktop pane.
     * @param layer The layer.
     */
    public void tiler(JDesktopPane desktopPane, int layer) {
        JInternalFrame[] frames = desktopPane.getAllFramesInLayer(layer);
        
        if (frames.length == 0) {
            return;
        }

        tiler(frames, desktopPane.getBounds());
    }

    /**
     * *
     * Tiles all of the internal graph frames of a JDesktopPane.
     *
     * @param desktopPane The desktop pane.
     */
    public void tiler(JDesktopPane desktopPane) {

        JInternalFrame[] frames = desktopPane.getAllFrames();
        if (frames.length == 0) {
            return;
        }

        tiler(frames, desktopPane.getBounds());
    }

    /**
     * *
     * Tiles a set of internal graph frames within a specified boundary.
     *
     * @param frames A set of internal frames (may include non-graph frames).
     * @param dBounds The specified bounds.
     */
    private void tiler(JInternalFrame[] frames, Rectangle dBounds) {
        frames = getGraphFrames(frames);

        int cols = (int) Math.sqrt(frames.length);
        int rows = (int) (Math.ceil(((double) frames.length) / cols));
        int lastRow = frames.length - cols * (rows - 1);
        int width, height;

        if (lastRow == 0) {
            rows--;
            height = dBounds.height / rows;
        } else {
            height = dBounds.height / rows;
            if (lastRow < cols) {
                rows--;
                width = dBounds.width / lastRow;
                for (int i = 0; i < lastRow; i++) {
                    frames[cols * rows + i].setBounds(i * width, rows * height,
                            width, height);
                }
            }
        }

        width = dBounds.width / cols;
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                frames[i + j * cols].setBounds(i * width, j * height,
                        width, height);
            }
        }
    }

    /**
     * *
     * Organizes all of the internal graph frames of a desktop pane at a
     * specified layer side-by-side.
     *
     * @param desktopPane The desktop pane.
     * @param layer The layer.
     */
    public void sideBySide(JDesktopPane desktopPane, int layer) {

        Dimension desktopSize = desktopPane.getSize();

        JInternalFrame[] frames = desktopPane.getAllFramesInLayer(layer);
        if (frames.length == 0) {
            return; //no frames
        }
        sideBySide(desktopSize, frames, desktopPane.getBounds());
    }

    /**
     * *
     * Organizes all of the internal graph frames of a desktop pane
     * side-by-side.
     *
     * @param desktopPane The desktop pane.
     */
    public void sideBySide(JDesktopPane desktopPane) {

        JInternalFrame[] frames = desktopPane.getAllFrames();
        if (frames.length == 0) {
            return; //no frames
        }
        Dimension desktopSize = desktopPane.getSize();

        sideBySide(desktopSize, frames, desktopPane.getBounds());
    }

    /**
     * Organizes a set of internal graph frames side-by-side within a specified
     * bounds.
     *
     * @param desktopSize The size of the container desktop pane.
     * @param frames The set of internal frames (may include non-graph internal
     * frames which are filtered out).
     * @param dBounds The specified bounds.
     */
    public void sideBySide(Dimension desktopSize, JInternalFrame[] frames, Rectangle dBounds) {

        int count = frames.length;
        if (count == 0) {
            return;
        }

        frames = getGraphFrames(frames);
        // size of window
        int w = desktopSize.width / frames.length;
        int h = desktopSize.height;
        int x = 0;
        int y = 0;

        //make each frame the same size and set its size so all frames fit
        for (int j = 0; j < frames.length; ++j) {
            JInternalFrame f = (JInternalFrame) frames[j];
            f.setBounds(x, y, w, h);
            x += w;
        }
    }

    /**
     * *
     * Adds a new internal frame to <b>desktopPane</b> and places it on top of
     * all other internal frames.
     *
     * @param frame Internal frame to be added.
     */
    public void addInternalFrame(JInternalFrame frame) {
        
        desktopPane.add(frame);
        
        for (int i = desktopPane.getComponentCount() - 2; i >= 0; i--) {
            Component c = desktopPane.getComponent(i);
            int index = desktopPane.getComponentZOrder(c) + 1;
            desktopPane.setComponentZOrder(c, index);
        }

        if(frame.getWidth() > desktopPane.getWidth() || 
                frame.getHeight() > desktopPane.getHeight()) {
            
            frame.setSize(desktopPane.getSize());
            frame.setLocation(new Point(0, 0));
        } else {
            frame.setLocation(getWidth() / 2 - frame.getWidth() / 2, getHeight() / 2 - frame.getHeight() / 2 - 100);
        }

        desktopPane.setComponentZOrder(frame, 0);

        repaint();
    }
    
    public void closeFrames(List<JInternalFrame> frames) {
        frames.forEach( (frame) -> {
            desktopPane.remove(frame);
        });
        
        desktopPane.revalidate();
        desktopPane.repaint();
    }
    
    public ArrayList<JInternalFrame> getContentFrames() {
        ArrayList<JInternalFrame> internalFrames = new ArrayList(Arrays.asList(desktopPane.getAllFrames()));
        
        internalFrames.remove(abnSelectionFrame);
        
        return internalFrames;
    }
}
