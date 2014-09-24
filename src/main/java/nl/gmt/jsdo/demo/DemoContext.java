package nl.gmt.jsdo.demo;

import nl.gmt.jsdo.JsdoContext;
import nl.gmt.jsdo.query.JsdoCatalog;
import nl.gmt.jsdo.schema.JsdoResource;
import nl.gmt.jsdo.schema.JsdoSchema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DemoContext extends MySqlContext {
    public DemoContext() throws SQLException, ClassNotFoundException {
        super("DemoCatalog", "jdbc:mysql://localhost/jsdo_demo?user=jsdo_demo&password=jsdo_demo");

        DemoSchema schema = new DemoSchema();
        setSchema(schema.getSchema());

        addQueryObject(schema.getRelationResource());
        addQueryObject(schema.getAddressResource());
    }
}
