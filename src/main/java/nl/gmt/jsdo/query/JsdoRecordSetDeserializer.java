package nl.gmt.jsdo.query;

import nl.gmt.jsdo.schema.JsdoObject;
import nl.gmt.jsdo.schema.JsdoResource;
import nl.gmt.jsdo.schema.JsdoSchema;
import nl.gmt.jsdo.schema.JsdoService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Reader;

public class JsdoRecordSetDeserializer {
    private final JsdoSchema schema;

    public JsdoRecordSetDeserializer(JsdoSchema schema) {
        this.schema = schema;
    }

    public JsdoRecordSet deserialize(Reader reader) {
        JSONObject object = new JSONObject(new JSONTokener(reader));

        JsdoRecordSet recordSet = deserializeRecordSet(object);

        if (recordSet == null) {
            throw new IllegalArgumentException("Invalid JSDO record set");
        }

        return recordSet;
    }

    private JsdoRecordSet deserializeRecordSet(JSONObject json) {
        for (Object key : json.keySet()) {
            String resourceName = (String)key;
            JsdoResource resource = null;

            for (JsdoService service : schema.getServices()) {
                for (JsdoResource item : service.getResources()) {
                    if (resourceName.equalsIgnoreCase(item.getName())) {
                        resource = item;
                        break;
                    }
                }
            }

            if (resource != null) {
                return deserializeRecordSet(json.getJSONObject(resourceName), resource);
            }
        }

        return null;
    }

    private JsdoRecordSet deserializeRecordSet(JSONObject json, JsdoResource resource) {
        for (Object key : json.keySet()) {
            String objectName = (String)key;
            JsdoObject object = null;

            for (JsdoObject item : resource.getObjects()) {
                if (objectName.equalsIgnoreCase(item.getName())) {
                    object = item;
                    break;
                }
            }

            if (object != null) {
                JsdoRecordSet recordSet = new JsdoRecordSet(resource, object);

                JSONArray array = json.getJSONArray(objectName);

                for (int i = 0; i < array.length(); i++) {
                    recordSet.getRecords().add(deserializeRecord(array.getJSONObject(i)));
                }

                return recordSet;
            }
        }

        return null;
    }

    private JsdoRecord deserializeRecord(JSONObject json) {
        JsdoRecord record = new JsdoRecord();

        for (Object key : json.keySet()) {
            Object value = null;
            String fieldName = (String)key;
            if (!json.isNull(fieldName)) {
                value = json.get(fieldName);
            }
            record.getFields().add(new JsdoField(fieldName, value));
        }

        return record;
    }
}
