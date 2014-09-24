package nl.gmt.jsdo.schema;

import org.apache.commons.lang.Validate;

public class JsdoRelationField {
    private final String parentFieldName;
    private final String childFieldName;

    public JsdoRelationField(String parentFieldName, String childFieldName) {
        Validate.notNull(parentFieldName, "parentFieldName");
        Validate.notNull(childFieldName, "childFieldName");

        this.parentFieldName = parentFieldName;
        this.childFieldName = childFieldName;
    }

    public String getParentFieldName() {
        return parentFieldName;
    }

    public String getChildFieldName() {
        return childFieldName;
    }
}
