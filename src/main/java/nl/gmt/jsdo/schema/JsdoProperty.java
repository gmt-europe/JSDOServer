package nl.gmt.jsdo.schema;

import org.apache.commons.lang.Validate;

public class JsdoProperty {
    private final String name;
    private final JsdoPropertyType type;
    private final Object defaultValue;
    private final String title;
    private final JsdoContentEncoding contentEncoding;
    private final boolean readOnly;
    private final boolean required;

    public JsdoProperty(String name, JsdoPropertyType type, Object defaultValue, String title, JsdoContentEncoding contentEncoding, boolean readOnly, boolean required) {
        Validate.notNull(name, "name");
        Validate.notNull(type, "type");
        Validate.notNull(title, "title");

        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.title = title;
        this.contentEncoding = contentEncoding;
        this.readOnly = readOnly;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public JsdoPropertyType getType() {
        return type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getTitle() {
        return title;
    }

    public JsdoContentEncoding getContentEncoding() {
        return contentEncoding;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isRequired() {
        return required;
    }
}
