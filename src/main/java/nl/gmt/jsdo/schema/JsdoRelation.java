package nl.gmt.jsdo.schema;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

public class JsdoRelation {
    private final String relationName;
    private final String parentName;
    private final String childName;
    private final List<JsdoRelationField> relationFields = new ArrayList<>();

    public JsdoRelation(String relationName, String parentName, String childName) {
        Validate.notNull(relationName, "relationName");
        Validate.notNull(parentName, "parentName");
        Validate.notNull(childName, "childName");

        this.relationName = relationName;
        this.parentName = parentName;
        this.childName = childName;
    }

    public String getRelationName() {
        return relationName;
    }

    public String getParentName() {
        return parentName;
    }

    public String getChildName() {
        return childName;
    }

    public List<JsdoRelationField> getRelationFields() {
        return relationFields;
    }
}
