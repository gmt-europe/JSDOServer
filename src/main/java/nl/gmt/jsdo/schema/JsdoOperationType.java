package nl.gmt.jsdo.schema;

public enum JsdoOperationType {
    INVOKE("invoke"),
    UPDATE("update"),
    CREATE("create"),
    DELETE("delete"),
    READ("read");

    private final String operation;

    private JsdoOperationType(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
