package edu.njit.cs.saboc.blu.core.gui.hierarchypainter;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class CompleteHierarchyPanel<T extends Concept> extends JPanel {
    
    private class LevelMetrics {
        public int level;

        public int conceptCount;

        public int rows;
        public int cols;

        public LevelMetrics(int level, int conceptCount, int rows, int cols) {
            this.level = level;
            this.conceptCount = conceptCount;
            this.rows = rows;
            this.cols = cols;
        }
    }
    
    private final BufferedImage hierarchyImage;
    
    public CompleteHierarchyPanel(Hierarchy<T> hierarchy, int panelWidth) {
        this.hierarchyImage = paintHierarchy(hierarchy, panelWidth);
        
        this.setSize(hierarchyImage.getWidth(), hierarchyImage.getHeight());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(hierarchyImage, 0, 0, null);
    }
    
    private BufferedImage paintHierarchy(Hierarchy<T> hierarchy, int maxWidth) {
        
        
        Map<T, Integer> hierarchyDepth = hierarchy.getAllLongestPathDepths();
        
        Map<Integer, Set<T>> conceptsByLevel = new HashMap<>();
        
        hierarchyDepth.forEach( (concept, depth) -> {
            if(!conceptsByLevel.containsKey(depth)) {
                conceptsByLevel.put(depth, new HashSet<>());
            }
            
            conceptsByLevel.get(depth).add(concept);
        });
        

        ArrayList<LevelMetrics> metrics = new ArrayList<>();

        final int MAX_CONCEPTS_PER_ROW = 200;
        
        conceptsByLevel.forEach( (depth, concepts) -> {
            int levelConceptCount = concepts.size();

            int colCount = levelConceptCount;
            int rowCount = 1;

            if (levelConceptCount > MAX_CONCEPTS_PER_ROW) {
                colCount = MAX_CONCEPTS_PER_ROW;
                rowCount = levelConceptCount / MAX_CONCEPTS_PER_ROW;
            }

            metrics.add(new LevelMetrics(depth, levelConceptCount, rowCount, colCount));
        });
        
        final int COL_DIVIDE_WIDTH = 2;
        final int ROW_DIVIDE_HEIGHT = 8;
        final int LEVEL_DIVIDE_HEIGHT = 10;
        
        final int CONCEPT_WIDTH =  4;
        final int CONCEPT_HEIGHT = CONCEPT_WIDTH;

        int maxCols = metrics.get(0).cols;

        int imageHeightScaled = metrics.get(0).rows * (ROW_DIVIDE_HEIGHT + LEVEL_DIVIDE_HEIGHT);

        for (int c = 1; c < metrics.size(); c++) {
            if (metrics.get(c).cols > maxCols) {
                maxCols = metrics.get(c).cols;
            }

            imageHeightScaled += metrics.get(c).rows * (ROW_DIVIDE_HEIGHT + LEVEL_DIVIDE_HEIGHT);
        }

        int imageWidth = maxCols * (CONCEPT_WIDTH + COL_DIVIDE_WIDTH);
        
        int scale = 1;
        
        int imageWidthScaled = (int)(imageWidth * scale);
        
        int colDividerWidthScaled = (int)(COL_DIVIDE_WIDTH * scale);
        int rowDividerHeightScaled = (int)(ROW_DIVIDE_HEIGHT * scale);
        
        int levelDivideHeightScaled = (int)(LEVEL_DIVIDE_HEIGHT * scale);
        
        int conceptWidthScaled = (int)(CONCEPT_WIDTH * scale);
        int conceptHeightScaled = (int)(CONCEPT_HEIGHT * scale);

        Map<T, Point> conceptDrawLocation = new HashMap<>();

        int conceptYPos = 0;

        for (int r = 0; r < metrics.size(); r++) {
            Set<T> levelConcepts = conceptsByLevel.get(r);
            LevelMetrics metric = metrics.get(r);
            
            Iterator<T> conceptsIterator = levelConcepts.iterator();

            int processedConcepts = 0;

            for (int levelRow = 0; levelRow <= metric.rows; levelRow++) {
                
                int remainingConcepts = levelConcepts.size() - processedConcepts;
                int centeringOffset = 0;
                
                if(remainingConcepts < MAX_CONCEPTS_PER_ROW) {
                    int rowWidth = remainingConcepts * (conceptWidthScaled + colDividerWidthScaled);
                    
                    centeringOffset = (imageWidthScaled - rowWidth) / 2;
                }
                
                for (int levelCol = 0; levelCol < metric.cols; levelCol++) {
                    
                    int conceptXPos = levelCol * (conceptWidthScaled + colDividerWidthScaled) + centeringOffset;

                    if (processedConcepts < levelConcepts.size()) {
                        conceptDrawLocation.put(conceptsIterator.next(), new Point(conceptXPos, conceptYPos));

                        processedConcepts++;
                    } else {
                        break;
                    }
                }

                conceptYPos += (conceptHeightScaled + rowDividerHeightScaled);
            }

            conceptYPos += levelDivideHeightScaled;
        }

        imageHeightScaled = conceptYPos;

        BufferedImage image = new BufferedImage(imageWidthScaled, imageHeightScaled, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D) image.getGraphics();

        g.setColor(Color.BLACK);

        g.fillRect(0, 0, imageWidthScaled, imageHeightScaled);

        ArrayList<Color> colors = new ArrayList<>();
        
        colors.add(Color.BLACK);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(new Color(176, 86, 169));
        colors.add(new Color(205, 133, 63));
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);

        int colorsSize = colors.size();

        for (int c = 1; c < colorsSize; c++) {
            colors.add(colors.get(c).brighter());
        }
               
        for (int r = 0; r < metrics.size(); r++) {
            Set<T> levelConcepts = conceptsByLevel.get(r);

            g.setColor(colors.get(r % colors.size()));
            
            levelConcepts.forEach((concept) -> {
                Point childDrawPoint = conceptDrawLocation.get(concept);

                Set<T> parents = hierarchy.getParents(concept);
                
                parents.forEach((parent) -> {
                    Point parentDrawPoint = conceptDrawLocation.get(parent);

                    g.drawLine(childDrawPoint.x + conceptWidthScaled / 2,
                            childDrawPoint.y,
                            parentDrawPoint.x + conceptWidthScaled / 2,
                            parentDrawPoint.y + conceptHeightScaled);

                });
            });
        }
    
        g.setColor(Color.BLACK);

        for (int r = 0; r < metrics.size(); r++) {
            Set<T> levelConcepts = conceptsByLevel.get(r);

            levelConcepts.forEach((concept) -> {
                Point drawPoint = conceptDrawLocation.get(concept);

                if (drawPoint != null) {
                    g.setColor(Color.WHITE);
                    g.fillRect(drawPoint.x, drawPoint.y, conceptWidthScaled, conceptHeightScaled);

                    g.setColor(Color.BLACK);
                    g.drawRect(drawPoint.x, drawPoint.y, conceptWidthScaled, conceptHeightScaled);
                }
            });
        }

        return image;
    }
}
