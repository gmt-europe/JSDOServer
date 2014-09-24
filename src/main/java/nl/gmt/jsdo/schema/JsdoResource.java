package nl.gmt.jsdo.schema;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

public class JsdoResource {
    private final String name;
    private final String path;
    private final JsdoResourceType type;
    private final List<JsdoObject> objects = new ArrayList<>();
    private final List<JsdoRelation> relations = new ArrayList<>();
    private final List<JsdoOperation> operations = new ArrayList<>();

    public JsdoResource(String name, String path, JsdoResourceType type) {
        Validate.notNull(name, "name");
        Validate.notNull(path, "path");
        Validate.notNull(type, "type");

        this.name = name;
        this.path = path;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public JsdoResourceType getType() {
        return type;
    }

    public List<JsdoObject> getObjects() {
        return objects;
    }

    public List<JsdoRelation> getRelations() {
        return relations;
    }

    public List<JsdoOperation> getOperations() {
        return operations;
    }
}
