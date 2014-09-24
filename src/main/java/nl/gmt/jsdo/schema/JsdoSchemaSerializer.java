package nl.gmt.jsdo.schema;

import org.json.JSONWriter;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class JsdoSchemaSerializer {
    public void serialize(Writer writer, JsdoSchema schema) {
        serializeSchema(new JSONWriter(writer), schema);
    }

    private void serializeSchema(JSONWriter w, JsdoSchema schema) {
        w.object();

        w.key("version");
        w.value(schema.getVersion());
        w.key("lastModified");
        w.value(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).format(schema.getLastModified()));
        w.key("services");

        w.array();
        for (JsdoService service : schema.getServices()) {
            serializeService(w, service);
        }
        w.endArray();

        w.endObject();
    }

    private void serializeService(JSONWriter w, JsdoService service) {
        w.object();

        w.key("name");
        w.value(service.getName());
        w.key("address");
        w.value(service.getAddress());
        w.key("useRequest");
        w.value(service.isUseRequest());
        w.key("resources");

        w.array();
        for (JsdoResource resource : service.getResources()) {
            serializeResource(w, resource);
        }
        w.endArray();

        w.endObject();
    }

    private void serializeResource(JSONWriter w, JsdoResource resource) {
        w.object();

        w.key("name");
        w.value(resource.getName());
        w.key("path");
        w.value(resource.getPath());
        w.key("schema");

        serializeResourceSchema(w, resource);
        
        w.key("relations");
        
        w.array();
        for (JsdoRelation relation : resource.getRelations()) {
            serializeRelation(w, relation);
        }
        w.endArray();

        w.key("operations");

        w.array();
        for (JsdoOperation operation : resource.getOperations()) {
            serializeOperation(w, operation);
        }
        w.endArray();

        w.endObject();
    }

    private void serializeOperation(JSONWriter w, JsdoOperation operation) {
        w.object();

        w.key("name");
        w.value(operation.getName());
        w.key("path");
        w.value(operation.getPath());
        w.key("type");
        w.value(operation.getType().getOperation());
        w.key("verb");
        w.value(operation.getVerb().getVerb());
        w.key("params");

        w.array();
        for (JsdoOperationParam param : operation.getParams()) {
            serializeOperationParam(w, param);
        }
        w.endArray();

        w.endObject();
    }

    private void serializeOperationParam(JSONWriter w, JsdoOperationParam param) {
        w.object();

        w.key("name");
        w.value(param.getName());
        w.key("type");
        w.value(param.getType().getType());

        w.endObject();
    }

    private void serializeRelation(JSONWriter w, JsdoRelation relation) {
        w.object();

        w.key("relationName");
        w.value(relation.getRelationName());
        w.key("parentName");
        w.value(relation.getParentName());
        w.key("childName");
        w.value(relation.getChildName());
        w.key("relationFields");

        w.array();
        for (JsdoRelationField relationField : relation.getRelationFields()) {
            serializeRelationField(w, relationField);
        }
        w.endArray();

        w.endObject();
    }

    private void serializeRelationField(JSONWriter w, JsdoRelationField relationField) {
        w.object();

        w.key("parentFieldName");
        w.value(relationField.getParentFieldName());
        w.key("childFieldName");
        w.value(relationField.getChildFieldName());

        w.endObject();
    }

    private void serializeResourceSchema(JSONWriter w, JsdoResource resource) {
        w.object();

        w.key("type");
        w.value(resource.getType().getType());
        w.key("additionalProperties");
        w.value(false);
        w.key("properties");

        serializeResourceSchemaProperties(w, resource);

        w.endObject();
    }

    private void serializeResourceSchemaProperties(JSONWriter w, JsdoResource resource) {
        w.object();

        w.key(resource.getName());
        w.object();

        w.key("type");
        w.value(resource.getType().getType());
        w.key("additionalProperties");
        w.value(false);
        w.key("properties");

        w.object();
        for (JsdoObject object : resource.getObjects()) {
            serializeObject(w, object);
        }
        w.endObject();

        w.endObject();

        w.endObject();
    }

    private void serializeObject(JSONWriter w, JsdoObject object) {
        w.key(object.getName());
        w.object();

        w.key("type");
        w.value(object.getType().getType());

        if (object.getPrimaryKey().size() > 0) {
            w.key("primaryKey");
            w.array();
            for (String primaryKey : object.getPrimaryKey()) {
                w.value(primaryKey);
            }
            w.endArray();
        }

        w.key("items");
        w.object();
        w.key("additionalProperties");
        w.value(false);
        w.key("properties");

        w.object();
        for (JsdoProperty property : object.getProperties()) {
            serializeProperty(w, property);
        }
        w.endObject();

        w.endObject();

        w.endObject();
    }

    private void serializeProperty(JSONWriter w, JsdoProperty property) {
        w.key(property.getName());
        w.object();

        w.key("type");
        w.value(property.getType().getType());
        w.key("ablType");
        w.value(property.getType().getAblType());
        w.key("default");
        if (property.getDefaultValue() == null) {
            w.value(null);
        } else if (property.getDefaultValue() instanceof JsdoPropertyDefault) {
            w.value(((JsdoPropertyDefault)property.getDefaultValue()).getCode());
        } else if (property.getDefaultValue() instanceof String) {
            w.value(property.getDefaultValue());
        } else {
            w.value(((Number)property.getDefaultValue()).doubleValue());
        }
        w.key("title");
        w.value(property.getTitle());
        if (property.getContentEncoding() != null) {
            w.key("contentEncoding");
            w.value(property.getContentEncoding().getContentEncoding());
        }
        if (property.isReadOnly()) {
            w.key("readonly");
            w.value(true);
        }
        if (property.isRequired()) {
            w.key("required");
            w.value(true);
        }

        w.endObject();
    }
}
