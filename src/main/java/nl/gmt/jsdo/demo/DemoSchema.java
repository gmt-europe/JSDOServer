package nl.gmt.jsdo.demo;

import nl.gmt.jsdo.schema.*;

import java.util.Date;
import java.util.List;

public class DemoSchema {
    private final JsdoSchema schema;
    private JsdoResource relationResource;
    private JsdoResource addressResource;

    public DemoSchema() {
        this.schema = createSchema();
    }

    public JsdoSchema getSchema() {
        return schema;
    }

    public JsdoResource getRelationResource() {
        return relationResource;
    }

    public JsdoResource getAddressResource() {
        return addressResource;
    }

    private JsdoSchema createSchema() {
        JsdoSchema schema = new JsdoSchema("1.0", new Date());
        schema.getServices().add(createService());
        return schema;
    }

    private JsdoService createService() {
        JsdoService service = new JsdoService("Demo", "/DemoCatalog", true);
        service.getResources().add(createRelationResource());
        service.getResources().add(createAddressResource());
        return service;
    }

    private JsdoResource createAddressResource() {
        addressResource = new JsdoResource("DemoAddress", "/DemoAddress", JsdoResourceType.OBJECT);
        createAddressObjects(addressResource.getObjects());
        createRelations(addressResource.getRelations());
        createOperations(addressResource.getOperations());
        return addressResource;
    }

    private void createAddressObjects(List<JsdoObject> objects) {
        objects.add(createObjectAddress());
    }

    private JsdoResource createRelationResource() {
        relationResource = new JsdoResource("DemoRelation", "/DemoRelation", JsdoResourceType.OBJECT);
        createRelationObjects(relationResource.getObjects());
        createOperations(relationResource.getOperations());
        return relationResource;
    }

    private void createRelationObjects(List<JsdoObject> objects) {
        objects.add(createObjectRelation());
    }

    private JsdoObject createObjectRelation() {
        JsdoObject object = new JsdoObject("DemoRelation", JsdoObjectType.ARRAY);
        createObjectRelationProperties(object.getProperties());
        object.getPrimaryKey().add("Id");
        return object;
    }

    private void createObjectRelationProperties(List<JsdoProperty> properties) {
        properties.add(createProperty("Id", JsdoPropertyType.INTEGER, null, "Id", false, true));
        properties.add(createProperty("Name", JsdoPropertyType.CHARACTER, null, "Name", false, true));
    }

    private JsdoObject createObjectAddress() {
        JsdoObject object = new JsdoObject("DemoAddress", JsdoObjectType.ARRAY);
        createObjectAddressProperties(object.getProperties());
        object.getPrimaryKey().add("Id");
        return object;
    }

    private void createObjectAddressProperties(List<JsdoProperty> properties) {
        properties.add(createProperty("Id", JsdoPropertyType.INTEGER, null, "Id", false, true));
        properties.add(createProperty("Code", JsdoPropertyType.CHARACTER, null, "Code", false, true));
        properties.add(createProperty("Street", JsdoPropertyType.CHARACTER, null, "Street", false, false));
        properties.add(createProperty("City", JsdoPropertyType.CHARACTER, null, "City", false, false));
        properties.add(createProperty("DemoRelationId", JsdoPropertyType.INTEGER, null, "Relation", false, true));
    }

    private void createRelations(List<JsdoRelation> relations) {
        relations.add(createAddressRelationRelation());
    }

    private JsdoRelation createAddressRelationRelation() {
        JsdoRelation relation = new JsdoRelation("DemoAddress_DemoRelation", "DemoRelation", "DemoAddress");
        relation.getRelationFields().add(new JsdoRelationField("Id", "DemoRelationId"));
        return relation;
    }

    private void createOperations(List<JsdoOperation> operations) {
//        operations.add(createOrderReadOperation());
//        operations.add(createUpdateOperation());
//        operations.add(createCreateOperation());
//        operations.add(createDeleteOperation());
//        operations.add(createCustomReadOperation());
//        operations.add(createReadOperation());
        operations.add(createGetAllOperation());
    }

    private JsdoOperation createGetAllOperation() {
        JsdoOperation operation = new JsdoOperation("Select", "/Select?id={id}&startRow={startRow}&rowsPerPage={rowsPerPage}", JsdoOperationType.INVOKE, JsdoVerb.GET);
        return operation;
    }

    private JsdoOperation createOrderReadOperation() {
        JsdoOperation operation = new JsdoOperation("OrderRead", "/OrderRead", JsdoOperationType.INVOKE, JsdoVerb.PUT);
        operation.getParams().add(createOperationParam("Filter"));
        operation.getParams().add(createOperationParam("OrderPosition"));
        return operation;
    }

    private JsdoOperation createUpdateOperation() {
        JsdoOperation operation = new JsdoOperation("Update", "/Update", JsdoOperationType.UPDATE, JsdoVerb.PUT);
        operation.getParams().add(createOperationParam("DsDemo"));
        return operation;
    }

    private JsdoOperation createCreateOperation() {
        JsdoOperation operation = new JsdoOperation("Create", "/Create", JsdoOperationType.CREATE, JsdoVerb.POST);
        operation.getParams().add(createOperationParam("DsDemo"));
        return operation;
    }

    private JsdoOperation createDeleteOperation() {
        JsdoOperation operation = new JsdoOperation("Delete", "/Delete", JsdoOperationType.DELETE, JsdoVerb.DELETE);
        operation.getParams().add(createOperationParam("DsDemo"));
        return operation;
    }

    private JsdoOperation createCustomReadOperation() {
        JsdoOperation operation = new JsdoOperation("CustomRead", "/CustomRead", JsdoOperationType.INVOKE, JsdoVerb.PUT);
        operation.getParams().add(createOperationParam("filter"));
        operation.getParams().add(createOperationParam("QryPosition"));
        return operation;
    }

    private JsdoOperation createReadOperation() {
        return new JsdoOperation("Read", "/Read", JsdoOperationType.READ, JsdoVerb.GET);
    }

    private JsdoOperationParam createOperationParam(String filter) {
        return new JsdoOperationParam(filter, JsdoOperationParamType.REQUEST_BODY);
    }

    private JsdoProperty createProperty(String name, JsdoPropertyType type, Object defaultValue, String title, boolean readOnly, boolean required) {
        return new JsdoProperty(name, type, defaultValue, title, null, readOnly, required);
    }
}
