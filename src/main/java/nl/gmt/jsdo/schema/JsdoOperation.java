package nl.gmt.jsdo.schema;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

public class JsdoOperation {
    private final String name;
    private final String path;
    private final JsdoOperationType type;
    private final JsdoVerb verb;
    private final List<JsdoOperationParam> params = new ArrayList<>();

    public JsdoOperation(String name, String path, JsdoOperationType type, JsdoVerb verb) {
        Validate.notNull(path, "path");
        Validate.notNull(type, "type");
        Validate.notNull(verb, "verb");

        this.name = name;
        this.path = path;
        this.type = type;
        this.verb = verb;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public JsdoOperationType getType() {
        return type;
    }

    public JsdoVerb getVerb() {
        return verb;
    }

    public List<JsdoOperationParam> getParams() {
        return params;
    }
}
