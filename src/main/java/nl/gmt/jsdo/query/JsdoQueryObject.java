package nl.gmt.jsdo.query;

import nl.gmt.jsdo.schema.JsdoObject;
import nl.gmt.jsdo.schema.JsdoResource;
import org.apache.commons.lang.Validate;

public abstract class JsdoQueryObject {
    private final JsdoResource resource;
    private final JsdoObject object;

    protected JsdoQueryObject(JsdoResource resource, JsdoObject object) {
        Validate.notNull(resource, "resource");
        Validate.notNull(object, "object");

        this.resource = resource;
        this.object = object;
    }

    public JsdoResource getResource() {
        return resource;
    }

    public JsdoObject getObject() {
        return object;
    }

    public abstract JsdoRecordSet getRecords(String filter) throws Exception;
}
