package edu.njit.cs.saboc.blu.core.graph.layout;

import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 * Utilties used 
 * 
 * @author Chris O
 */
public class GraphLayoutUtilities {

    /**
     * Creates a label that is sized to fit the given set of strings
     * 
     * @param entries
     * @param boundingWidth
     * @param fontMetrics
     * @return 
     */
    public static JLabel createFittedPartitionLabel(String[] entries, int boundingWidth, FontMetrics fontMetrics) {
        
        ArrayList<String> lines = new ArrayList<>();

        int remainingSpace = boundingWidth;

        final int SPACER_WIDTH = fontMetrics.stringWidth(", ");

        String line = "";

        int widestLine = -1;

        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];

            int wordWidth = fontMetrics.stringWidth(entry);

            if ((wordWidth + SPACER_WIDTH) > remainingSpace || entry.equals("[[FORCE-NEWLINE]]")) {
                lines.add(line);

                int lineWidth = fontMetrics.stringWidth(line);

                if (lineWidth > widestLine) {
                    widestLine = lineWidth;
                }

                if (entry.equals("[[FORCE-NEWLINE]]")) {
                    line = "";
                } else {
                    line = entry;

                    if (i != entries.length - 2) {
                        line += ", ";
                    } else {
                        line += " ";
                    }
                }

                remainingSpace = boundingWidth - wordWidth;
            } else {
                line += entry;

                if (i != entries.length - 2) {
                    line += ", ";
                } else {
                    line += " ";
                }

                remainingSpace -= (wordWidth + SPACER_WIDTH);
            }
        }

        line = line.substring(0, line.length() - 2);

        int lineWidth = fontMetrics.stringWidth(line);

        if (lineWidth > widestLine) {
            widestLine = lineWidth;
        }

        lines.add(line);

        String labelStr = "<html><center>";

        for (String labelLine : lines) {
            labelStr += (labelLine + "<br>");
        }

        labelStr = labelStr.substring(0, labelStr.length() - 4);

        labelStr += "</center></html>";

        JLabel partitionLabel = new JLabel();
        partitionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        partitionLabel.setText(labelStr);

        partitionLabel.setBounds(0, 0, widestLine, lines.size() * fontMetrics.getHeight());

        return partitionLabel;
    }
}
