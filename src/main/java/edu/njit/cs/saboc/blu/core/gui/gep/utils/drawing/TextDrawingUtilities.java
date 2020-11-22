package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;


/**
 *
 * @author Chris
 */
public class TextDrawingUtilities {

    public static void drawPartitionTitle(Graphics2D g, ArrayList<String> lines, int y, Rectangle centeringBounds) {
        for (String line : lines) {
            int x = (centeringBounds.width - g.getFontMetrics().stringWidth(line)) / 2;
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
    }

    public static void drawTextWithinBounds(Graphics2D g, String text, Rectangle bounds, Rectangle centeringBounds) {

        LinkedList<String> queue = new LinkedList<String>();
        Collections.addAll(queue, text.split("\\s+"));

        ArrayList<String> lines = new ArrayList<String>();

        Font font = g.getFont();

        int fontSize = font.getSize();

        boolean finished = false;

        while (!finished) {
            while (!queue.isEmpty()) {
                String line = queue.removeFirst();

                while (!queue.isEmpty()
                        && g.getFontMetrics().stringWidth(line + " "
                        + queue.getFirst()) < bounds.width) {
                    line += (" " + queue.removeFirst());
                }

                lines.add(line);
            }

            if ((lines.size() * g.getFontMetrics().getHeight()) > bounds.height) {
                fontSize -= 1;
                g.setFont(new Font(font.getFontName(), font.getStyle(), fontSize));
                
                lines.clear();
                Collections.addAll(queue, text.split("\\s+"));
            } else {
                finished = true;
            }
        }

        int y = bounds.y;

        for (String line : lines) {
            int x = bounds.x + (centeringBounds.width - g.getFontMetrics().stringWidth(line)) / 2;
            
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }

        g.setFont(new Font("Ariel", Font.PLAIN, 11));
    }
    
        
    public static void drawTextWithNewlines(Graphics2D g, String text, Rectangle bounds, int x, int y) {
        g.setFont(new Font("Ariel", Font.PLAIN, 11));

        LinkedList<String> queue = new LinkedList<String>();
        Collections.addAll(queue, text.split("\\s+"));

        ArrayList<String> lines = new ArrayList<String>();

        boolean finished = false;

        int fontSize = 20;

        while (!finished) {
            while (!queue.isEmpty()) {
                String line = queue.removeFirst();

                while (!queue.isEmpty()
                        && g.getFontMetrics().stringWidth(line + " "
                        + queue.getFirst()) < (2 * bounds.width) - 10) {
                    line += (" " + queue.removeFirst());
                }

                lines.add(line);
            }

            if((lines.size() * g.getFontMetrics().getHeight()) > 2 * bounds.height - 4) {
                fontSize -= 1;
                g.setFont(new Font("Ariel", Font.PLAIN, fontSize));
                lines.clear();
                Collections.addAll(queue, text.split("\\s+"));
            } else {
                finished = true;
            }
        }

        for(String line  : lines) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }

        g.setFont(new Font("Ariel", Font.PLAIN, 11));
    }
}
