package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

/**
 *
 * @author Chris O
 */
public class ChangeExplanationRowEntry {

    public static final String INDIRECT = "Indirect";
    public static final String DIRECT = "Direct";

    private final String changeType;
    private final String inheritanceType;
    private final String changeSummary;
    private final String changeDescription;

    public ChangeExplanationRowEntry(
            String changeType,
            String inheritanceType,
            String changeSummary,
            String changeDescription) {

        this.changeType = changeType;
        this.inheritanceType = inheritanceType;
        this.changeSummary = changeSummary;
        this.changeDescription = changeDescription;
    }

    public Object[] asRow() {
        return new Object[]{changeType, inheritanceType, changeSummary};
    }
}
