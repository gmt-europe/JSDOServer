package nl.gmt.jsdo.schema;

public enum JsdoContentEncoding {
    BASE64("base64");

    private final String contentEncoding;

    private JsdoContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }
}
