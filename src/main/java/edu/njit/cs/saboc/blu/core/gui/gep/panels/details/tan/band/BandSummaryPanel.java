package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class BandSummaryPanel extends NodeSummaryPanel<Band> {

    private final TANConfiguration config;
    
    private final ConceptList bandTribesList;
    
    public BandSummaryPanel(TANConfiguration config) {
        this(config, new BandSummaryTextFactory(config));
    }
    
    public BandSummaryPanel(TANConfiguration config, BandSummaryTextFactory textFactory) {
        
        super(textFactory);

        this.bandTribesList = new ConceptList(config, new BandPatriarchTableModel(config)) {

            @Override
            protected String getBorderText(Optional<ArrayList<Concept>> entities) {
                if(entities.isPresent()) {
                    return String.format("Tribes (%d)", entities.get().size());
                } else {
                    return "Tribes";
                }
            }
        };
        
        this.add(bandTribesList);
        
        this.config = config;
    }

    @Override
    public void clearContents() {
        super.clearContents();
        
        bandTribesList.clearContents();
    }

    @Override
    public void setContents(Band band) {       
        super.setContents(band);
                
        ArrayList<Concept> tribalPatriarchs = new ArrayList<>(band.getPatriarchs());
        tribalPatriarchs.sort( (a,b) -> a.getName().compareToIgnoreCase(b.getName()));
        
        bandTribesList.setContents(tribalPatriarchs);
    }
}
