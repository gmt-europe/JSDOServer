package nl.gmt.jsdo.query;

import nl.gmt.jsdo.schema.JsdoObject;
import nl.gmt.jsdo.schema.JsdoResource;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

public class JsdoRecordSet {
    private final JsdoResource resource;
    private final JsdoObject object;
    private final List<JsdoRecord> records = new ArrayList<>();

    public JsdoRecordSet(JsdoResource resource, JsdoObject object) {
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

    public List<JsdoRecord> getRecords() {
        return records;
    }
}
