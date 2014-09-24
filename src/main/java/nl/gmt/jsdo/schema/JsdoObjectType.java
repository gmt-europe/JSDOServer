package nl.gmt.jsdo.schema;

public enum JsdoObjectType {
    ARRAY("array");

    private final String type;

    private JsdoObjectType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
