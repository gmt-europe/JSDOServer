package nl.gmt.jsdo.query;

public class JsdoField {
    private final String name;
    private final Object value;

    public JsdoField(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
