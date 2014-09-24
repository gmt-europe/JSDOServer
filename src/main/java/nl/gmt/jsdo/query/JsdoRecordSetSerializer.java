package nl.gmt.jsdo.query;

import org.json.JSONWriter;

import java.io.Writer;

public class JsdoRecordSetSerializer {
    public void serialize(Writer writer, JsdoRecordSet records) {
        serializeRecords(new JSONWriter(writer), records);
    }

    private void serializeRecords(JSONWriter w, JsdoRecordSet recordSet) {
        w.object();

        w.key(recordSet.getResource().getName());
        w.object();

        w.key(recordSet.getObject().getName());
        w.array();
        for (JsdoRecord record : recordSet.getRecords()) {
            serializeRecord(w, record);
        }
        w.endArray();

        w.endObject();

        w.endObject();
    }

    private void serializeRecord(JSONWriter w, JsdoRecord record) {
        w.object();

        for (JsdoField field : record.getFields()) {
            w.key(field.getName());

            Object value = field.getValue();
            if (value == null) {
                w.value(null);
            } else if (value instanceof Boolean) {
                w.value((boolean)value);
            } else if (value instanceof Number) {
                w.value(((Number)value).doubleValue());
            } else if (value instanceof String) {
                w.value(value);
            } else {
                w.value(value.toString());
            }
        }

        w.endObject();
    }
}
