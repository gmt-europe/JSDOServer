package nl.gmt.jsdo.schema;

public enum JsdoResourceType {
    OBJECT("object");

    private final String type;

    private JsdoResourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
