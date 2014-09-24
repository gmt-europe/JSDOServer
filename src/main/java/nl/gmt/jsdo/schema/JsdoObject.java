package nl.gmt.jsdo.schema;

import java.util.ArrayList;
import java.util.List;

public class JsdoObject {
    private final String name;
    private final JsdoObjectType type;
    private final List<String> primaryKey = new ArrayList<>();
    private final List<JsdoProperty> properties = new ArrayList<>();

    public JsdoObject(String name, JsdoObjectType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public JsdoObjectType getType() {
        return type;
    }

    public List<String> getPrimaryKey() {
        return primaryKey;
    }

    public List<JsdoProperty> getProperties() {
        return properties;
    }
}
