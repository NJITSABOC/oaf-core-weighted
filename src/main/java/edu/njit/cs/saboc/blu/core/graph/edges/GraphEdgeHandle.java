package edu.njit.cs.saboc.blu.core.graph.edges;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

/**
 * A handle for selecting and moving an edge
 * 
 * @author David Daudelin
 */
public class GraphEdgeHandle extends JPanel implements MouseInputListener {

    private final AbstractionNetworkGraph<?> graph;
    private final JPanel segment;

    public GraphEdgeHandle(int x, int y, JPanel s, AbstractionNetworkGraph g) {
        setBounds(x, y, 11, 11);
        setVisible(true);
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        setBackground(Color.black);
        segment = s;
        graph = g;
    }

    public JPanel getSegment() {
        return segment;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

        ArrayList<GraphEdgeHandle> activeHandles = graph.getActiveHandles();

        this.setBackground(Color.black);

        for (GraphEdgeHandle h : activeHandles) {
            h.setVisible(true);

            if (h.getSegment().getName().equals("Vertical")) {
                h.setBounds(h.getSegment().getX() + graph.getEdgeWidth() / 2 - 5, h.getSegment().getY() + h.getSegment().getHeight() / 2 - 6, h.getWidth(), h.getHeight());
            } else {
                h.setBounds(h.getSegment().getX() + h.getSegment().getWidth() / 2 - 6, h.getSegment().getY() + graph.getEdgeWidth() / 2 - 5, h.getWidth(), h.getHeight());
            }
        }

        graph.setLastDragged(null);
    }

    public void mouseEntered(MouseEvent e) {
        setBackground(Color.blue);
    }

    public void mouseExited(MouseEvent e) {
        setBackground(Color.black);
    }

    public void mouseDragged(MouseEvent e) {
        if (graph.getLastDragged() != this) {
            for (GraphEdgeHandle h : graph.getActiveHandles()) {
                if (!h.equals(this)) {
                    h.setVisible(false);
                }
            }

            graph.setLastDragged(this);
        }

        HashMap<JPanel, GraphEdge> segmentToEdge = graph.getSegmentToEdge();

        this.setBackground(Color.green);

        e = SwingUtilities.convertMouseEvent(e.getComponent(), e, e.getComponent().getParent());

        JPanel s = this.getSegment();
        GraphEdge selected = segmentToEdge.get(s);

        if (graph.getEdges().contains(selected)) {
            graph.addToManualEdges(selected);
        }

        int i = selected.getSegments().indexOf(s);

        if (this.getSegment().getName().equals("Horizontal")) {
            this.setBounds(this.getX(), e.getY() - this.getHeight() / 2, this.getWidth(), this.getHeight());
            selected.setPoint(i, new Point((int) selected.getPoints().get(i).getX(), e.getY()));
            selected.setPoint(i + 1, new Point((int) selected.getPoints().get(i + 1).getX(), e.getY()));
            selected.updateEdge();
        }

        if (this.getSegment().getName().equals("Vertical")) {
            this.setBounds(e.getX() - this.getWidth() / 2, this.getY(), this.getWidth(), this.getHeight());

            selected.setPoint(i, new Point(e.getX(), (int) selected.getPoints().get(i).getY()));
            selected.setPoint(i + 1, new Point(e.getX(), (int) selected.getPoints().get(i + 1).getY()));
            selected.updateEdge();
        }

    }

    public void mouseMoved(MouseEvent e) {
    }
}
