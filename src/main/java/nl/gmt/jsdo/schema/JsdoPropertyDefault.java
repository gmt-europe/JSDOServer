package nl.gmt.jsdo.schema;

public enum JsdoPropertyDefault {
    NOW("now"),
    TODAY("today");

    private final String code;

    private JsdoPropertyDefault(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
