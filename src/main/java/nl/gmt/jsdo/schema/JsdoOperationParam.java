package nl.gmt.jsdo.schema;

import org.apache.commons.lang.Validate;

public class JsdoOperationParam {
    private final String name;
    private final JsdoOperationParamType type;

    public JsdoOperationParam(String name, JsdoOperationParamType type) {
        Validate.notNull(name, "name");
        Validate.notNull(type, "type");

        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public JsdoOperationParamType getType() {
        return type;
    }
}

