package nl.gmt.jsdo.schema;

public enum JsdoVerb {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete");

    private final String verb;

    private JsdoVerb(String verb) {
        this.verb = verb;
    }

    public String getVerb() {
        return verb;
    }
}
