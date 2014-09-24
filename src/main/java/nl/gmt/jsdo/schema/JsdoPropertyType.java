package nl.gmt.jsdo.schema;

public enum JsdoPropertyType {
    CHARACTER("CHARACTER", "string"),
    LONGCHAR("LONGCHAR", "string"),
    LOGICAL("LOGICAL", "boolean"),
    DECIMAL("DECIMAL", "number"),
    INT64("INT64", "integer"),
    INTEGER("INTEGER", "integer"),
    ROWID("ROWID", "string"),
    DATE("DATE", "string"),
    DATETIME("DATETIME", "string"),
    DATETIME_TZ("DATETIME-TZ", "string"),
    CLOB("CLOB", "string"),
    LOB("LOB", "string");

    private final String ablType;
    private final String type;

    private JsdoPropertyType(String ablType, String type) {
        this.ablType = ablType;
        this.type = type;
    }

    public String getAblType() {
        return ablType;
    }

    public String getType() {
        return type;
    }
}
