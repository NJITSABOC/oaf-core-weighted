package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;

/**
 * A manager that creates all of the labels for an AbN's groups
 * @author Chris
 */
public class AbNLabelManager {
    private final int MAX_GROUP_LABELSHEET_SIZE = 2048;
    
    /**
     * Stores the label sheet and position for a given group
     */
    private class GroupLabelPositionEntry {
        public int x;
        public int y;

        public BufferedImage labelSheet = null;
        
        public GroupLabelPositionEntry(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public void setLabelSheet(BufferedImage labelSheet) {
            this.labelSheet = labelSheet;
        }
    }

    private final HashMap<JLabel, BufferedImage> labelImages = new HashMap<>();
    
    private final HashMap<SinglyRootedNode, GroupLabelPositionEntry> groupLabelMap = new HashMap<>();
        
    private final ArrayList<BufferedImage> labelSheets = new ArrayList<>();

    private final SinglyRootedNodeLabelCreator labelCreator;
    
    public AbNLabelManager(AbstractionNetwork abn, SinglyRootedNodeLabelCreator labelCreator) {
        this.labelCreator = labelCreator;
        
        ArrayList<SinglyRootedNode> groupList = new ArrayList<>(abn.getNodes());
                
        createGroupLabelSheets(groupList);
    }
    
    private final void createGroupLabelSheets(ArrayList<SinglyRootedNode> nodes) {
        int pendingCount = nodes.size();
        
        int totalProcessed = 0;
        
        while(pendingCount > 0) {
            
            int size = MAX_GROUP_LABELSHEET_SIZE;

            int rows = size / SinglyRootedNodeEntry.ENTRY_HEIGHT;
            int cols = size / SinglyRootedNodeEntry.ENTRY_WIDTH;

            
            while (doSubdivide(size, pendingCount)) {
                size /= 2;

                rows = size / SinglyRootedNodeEntry.ENTRY_HEIGHT;
                cols = size / SinglyRootedNodeEntry.ENTRY_WIDTH;
            }
            
            BufferedImage labelSheet = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = (Graphics2D) labelSheet.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(new Color(0, 0, 0, 0)); // Transparent background
            g.fillRect(0, 0, labelSheet.getWidth(), labelSheet.getHeight());

            g.setColor(Color.BLACK);

            g.setFont(new Font("SansSerif", Font.PLAIN, 20)); //14
            
            ArrayList<SinglyRootedNode> processedGroups = new ArrayList<>();
            
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    SinglyRootedNode group = nodes.get(totalProcessed);

                    groupLabelMap.put(group, new GroupLabelPositionEntry(x, y));

                    drawGroupLabelAtPosition2(x * SinglyRootedNodeEntry.ENTRY_WIDTH, y * SinglyRootedNodeEntry.ENTRY_HEIGHT, group, g);

                    totalProcessed++;
                    processedGroups.add(group);

                    if (totalProcessed >= nodes.size()) {
                        break;
                    }
                }

                if (totalProcessed >= nodes.size()) {
                    break;
                }
            }

            pendingCount -= (rows * cols);

            BufferedImage mipmap = createMipMap(labelSheet);
            
            labelSheets.add(mipmap);
            
            processedGroups.forEach((group) -> {
                groupLabelMap.get(group).setLabelSheet(mipmap);
            });
        }
    }
   
    private boolean doSubdivide(int size, final int remaining) {
        size /= 2;

        int rows = size / SinglyRootedNodeEntry.ENTRY_HEIGHT;
        int cols = size / SinglyRootedNodeEntry.ENTRY_WIDTH;

        return remaining < (4 * rows * cols);
    }

    private BufferedImage createMipMap(BufferedImage image) {
        BufferedImage mipmap = new BufferedImage(image.getWidth() + (image.getWidth() / 2), image.getHeight(), image.getType());
        
        Graphics2D mipmapG = mipmap.createGraphics();
        
        mipmapG.setColor(new Color(0,0,0,0));
        mipmapG.fillRect(0, 0, mipmap.getWidth(), mipmap.getHeight());
        
        mipmapG.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        mipmapG.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        mipmapG.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        mipmapG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw full sized image
        mipmapG.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        
        int scale = 2;
        
        int yPos = 0;
                
        for(int c = 1; c <= 4; c++) {
            int size = image.getWidth() / scale;
            
            mipmapG.drawImage(image, image.getWidth(), yPos, image.getWidth() + size, yPos + size, 0, 0, image.getWidth(), image.getHeight(), null);

            
            yPos += size;
            
            scale *= 2;
        }
        
        return mipmap;
    }

    public void drawGroupLabel(SinglyRootedNode group, double scale, Graphics2D g, int x, int y) {
        GroupLabelPositionEntry entry = groupLabelMap.get(group);
        
        if(entry == null) {
            return;
        }
                
        int labelWidth = SinglyRootedNodeEntry.ENTRY_WIDTH;
        int labelHeight = SinglyRootedNodeEntry.ENTRY_HEIGHT;
        
        int srcWidth = labelWidth;
        int srcHeight = labelHeight;
        
        int mipmapXOffset = 0;
        int mipmapYOffset = 0;
        
        if (scale < 0.55 && scale > 0.25) {
            srcWidth /= 2;
            srcHeight /= 2;
            
            mipmapXOffset = entry.labelSheet.getHeight();
        } else if(scale < 0.125) {
            srcWidth /= 4;
            srcHeight /= 4;
            
            mipmapXOffset = entry.labelSheet.getHeight();
            mipmapYOffset = entry.labelSheet.getHeight() / 2;
        }
        
        int srcX = mipmapXOffset + (int) (entry.x * srcWidth);
        int srcY = mipmapYOffset + (int) (entry.y * srcHeight);

        g.drawImage(entry.labelSheet, x, y, x + (int) (labelWidth * scale), y + (int) (labelHeight * scale), srcX, srcY, srcX + srcWidth, srcY + srcHeight, null);
    }
    
    private void drawGroupLabelAtPosition(int xPos, int yPos, SinglyRootedNode group, Graphics2D g) {

        String rootName = labelCreator.getRootNameStr(group);
        String conceptCountLabel = labelCreator.getCountStr(group);
//        String conceptCountLabel = "";

        boolean finished = false;

        int fontHeight = g.getFontMetrics().getHeight();
        
        int y = 0;

        int strIndex = 0;

        while (!finished) {

            String line = rootName.substring(strIndex, Math.min(strIndex + 20, rootName.length())); 

            if (!line.isEmpty() && line.contains(" ") && !line.substring(0, line.lastIndexOf(" ")).isEmpty()) {
                line = line.substring(0, line.lastIndexOf(" "));

                strIndex += line.length();
            } else {
                finished = true;
                strIndex = rootName.length();
            }

            y += fontHeight;

            if ((y + fontHeight) > SinglyRootedNodeEntry.ENTRY_HEIGHT) {
                finished = true;
            }

            if (strIndex >= rootName.length()) {
                finished = true;
            }

            if (finished) {

                boolean fit = false;

                String appendStr = conceptCountLabel;

                if (strIndex < rootName.length() - 1) { // Didn't use whole string...
                    if (g.getFontMetrics().stringWidth(line + "...") <= SinglyRootedNodeEntry.ENTRY_WIDTH) { // Within bounds with dots...
                        if (g.getFontMetrics().stringWidth(line + "... " + conceptCountLabel) <= SinglyRootedNodeEntry.ENTRY_WIDTH) {
                            line += ("... " + conceptCountLabel);
                            fit = true;
                        } else {
                            appendStr = ("..." + appendStr);
                        }
                    }

                } else { // Used the whole string...
                    if (g.getFontMetrics().stringWidth(rootName) <= SinglyRootedNodeEntry.ENTRY_WIDTH && g.getFontMetrics().stringWidth(conceptCountLabel) <= SinglyRootedNodeEntry.ENTRY_WIDTH) {
                        line = line + "\n" + conceptCountLabel;
                        fit = true;
                    }
                    else if (g.getFontMetrics().stringWidth(line + " " + conceptCountLabel) < SinglyRootedNodeEntry.ENTRY_WIDTH) { // Can fit concept count
                        line += (" " + conceptCountLabel);
                        fit = true;
                    }
                    else {
                        appendStr = ("..." + appendStr); // " "
                    }
                }

                if (!fit) {
                    int boundDifference = g.getFontMetrics().stringWidth(line + appendStr) - SinglyRootedNodeEntry.ENTRY_WIDTH;

                    int cutPoint = 1;

                    int chopSize = g.getFontMetrics().stringWidth(line.substring(line.length() - cutPoint, line.length()));

                    while (cutPoint < line.length() && chopSize < boundDifference) {
                        cutPoint++;

                        chopSize = g.getFontMetrics().stringWidth(line.substring(line.length() - cutPoint, line.length()));
                    }

                    line = line.substring(0, line.length() - cutPoint) + appendStr;
                }
            }

//            int x = (SinglyRootedNodeEntry.ENTRY_WIDTH - g.getFontMetrics().stringWidth(line)) / 2;
                        
            drawStringMultipleLine(g, line, xPos, yPos + y - 4);
//            g.drawString(line, xPos + x, yPos + y - 4);
        }
    }
    
    private void drawGroupLabelAtPosition2(int xPos, int yPos, SinglyRootedNode group, Graphics2D g) {

        String rootName = labelCreator.getRootNameStr(group);
        String conceptCountLabel = labelCreator.getCountStr(group);

        String text = rootName;
        int width = SinglyRootedNodeEntry.ENTRY_WIDTH - 4;
        

        String appendStr = conceptCountLabel;        
        
        if (g.getFontMetrics().stringWidth(text) <= width) {
            text = text + "\n" + conceptCountLabel;
        }
        else if (isFit(g, text + appendStr)) { // Can fit in 3 lines
            text += (" " + conceptCountLabel);

        } else { // cannot fit in 3 lines
            appendStr = ("... " + appendStr);

            while(!isFit(g, text + appendStr)){
                text = text.substring(0, text.length() - 1);
            }           
            text = text  + appendStr;
        }
        
        int y = 0;
        for (String line : text.split("\n")) {
        AttributedString attributedString = new AttributedString(line);
        attributedString.addAttribute(TextAttribute.FONT, g.getFont());
        
        AttributedCharacterIterator characterIterator = attributedString.getIterator();
        
        FontRenderContext fontRenderContext = g.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(characterIterator, fontRenderContext);
        
        while (measurer.getPosition() < characterIterator.getEndIndex()) {
            TextLayout textLayout = measurer.nextLayout(width);
            
            Rectangle2D bounds = textLayout.getBounds();
            int x = (int) ((width - (int) bounds.getWidth()) / 2);
            
            y += (int) textLayout.getAscent();
            textLayout.draw(g, xPos + x, yPos + y + 4);
            y += textLayout.getDescent() + textLayout.getLeading();
            }
        }

    }
    
    private boolean isFit(Graphics2D g, String line){
        float width = (float) SinglyRootedNodeEntry.ENTRY_WIDTH-4;
        AttributedString attributedString = new AttributedString(line);
        attributedString.addAttribute(TextAttribute.FONT, g.getFont());
        
        AttributedCharacterIterator characterIterator = attributedString.getIterator();
        
        FontRenderContext fontRenderContext = g.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(characterIterator, fontRenderContext);        
        measurer.setPosition(characterIterator.getBeginIndex());
        int lines = 0;
        int x = 0;
        while (measurer.getPosition() < characterIterator.getEndIndex()) {                               
            TextLayout textLayout = measurer.nextLayout(width-x);           
            Rectangle2D bounds = textLayout.getBounds();
            x = (int) ((width - (int) bounds.getWidth()) / 2);
            lines++;
        }
    
        return (lines <= 3);
    }
    
    
    
    
    public void drawLabel(Graphics2D graphics, JLabel label, double scale, int x, int y) {
                
        if(!labelImages.containsKey(label)) {
            createLabelImage(label);
        }
        
        BufferedImage labelImage = labelImages.get(label);
        
        graphics.drawImage(labelImage, x, y, (int)(labelImage.getWidth() * scale), (int)(labelImage.getHeight() * scale), null);
    }
    
    public void removeLabel(JLabel label) {
        labelImages.remove(label);
    }
    
    public void clearLabelImages() {
        labelImages.clear();
    }
    
    private void createLabelImage(JLabel label) {
        BufferedImage image = new BufferedImage(label.getWidth(), label.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = (Graphics2D)image.getGraphics();
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        label.print(g2d);
        
        labelImages.put(label, image);
    }
    
    
    public void drawStringMultipleLine(Graphics2D g, String text, int x, int y) {
        boolean firstLine = true;
        for (String line : text.split("\n")) {
            int x_offset = 0;
            if (firstLine) {
                x_offset = (SinglyRootedNodeEntry.ENTRY_WIDTH - g.getFontMetrics().stringWidth(line)) / 2;
                firstLine = false;
            }         
            g.drawString(line, x+ x_offset, y);
            y = y + g.getFontMetrics().getHeight();
        }

    }
}
