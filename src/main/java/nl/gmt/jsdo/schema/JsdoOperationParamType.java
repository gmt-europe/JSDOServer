package nl.gmt.jsdo.schema;

public enum JsdoOperationParamType {
    REQUEST_BODY("REQUEST_BODY");

    private final String type;

    private JsdoOperationParamType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
