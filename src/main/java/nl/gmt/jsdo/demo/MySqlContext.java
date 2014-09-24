package nl.gmt.jsdo.demo;

import nl.gmt.jsdo.JsdoContext;
import nl.gmt.jsdo.query.JsdoCatalog;
import nl.gmt.jsdo.query.JsdoField;
import nl.gmt.jsdo.query.JsdoRecord;
import nl.gmt.jsdo.query.JsdoRecordSet;
import nl.gmt.jsdo.schema.JsdoObject;
import nl.gmt.jsdo.schema.JsdoResource;
import nl.gmt.jsdo.schema.JsdoSchema;
import org.apache.commons.lang.Validate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MySqlContext implements JsdoContext, AutoCloseable {
    private JsdoSchema schema;
    private final List<JsdoCatalog> catalogs = new ArrayList<>();
    private final Connection connection;

    protected MySqlContext(String catalogName, String connectionString) throws SQLException, ClassNotFoundException {
        Validate.notNull(catalogName, "catalogName");
        Validate.notNull(connectionString, "connectionString");

        this.catalogs.add(new JsdoCatalog(catalogName));

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(connectionString);
    }

    protected void setSchema(JsdoSchema schema) {
        this.schema = schema;
    }

    protected void addQueryObject(JsdoResource resource) {
        catalogs.get(0).getQueryObjects().add(new MySqlQueryObject(resource, resource.getObjects().get(0), connection));
    }

    @Override
    public JsdoSchema getSchema() {
        return schema;
    }

    @Override
    public List<JsdoCatalog> getCatalogs() {
        return catalogs;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public void create(JsdoRecordSet recordSet) throws Exception {
        for (JsdoRecord record : recordSet.getRecords()) {
            StringBuilder sb = new StringBuilder();
            List<Object> args = new ArrayList<>();

            sb.append("INSERT INTO `").append(recordSet.getObject().getName()).append("` (");

            boolean hadOne = false;

            for (JsdoField field : record.getFields()) {
                if (hadOne) {
                    sb.append(", ");
                } else {
                    hadOne = true;
                }
                sb.append("`").append(field.getName()).append("`");
                args.add(field.getValue());
            }

            if (!hadOne) {
                continue;
            }

            sb.append(") VALUES (");

            for (int i = 0; i < args.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append("?");
            }

            sb.append(")");

            try (PreparedStatement statement = connection.prepareStatement(sb.toString())) {
                for (int i = 0; i < args.size(); i++) {
                    statement.setObject(i + 1, args.get(i));
                }

                statement.execute();
            }
        }
    }

    @Override
    public void update(JsdoRecordSet recordSet) throws Exception {
        for (JsdoRecord record : recordSet.getRecords()) {
            StringBuilder sb = new StringBuilder();
            List<Object> args = new ArrayList<>();

            sb.append("UPDATE `").append(recordSet.getObject().getName()).append("` ");

            boolean hadOne = false;

            for (JsdoField field : record.getFields()) {
                if (!isPrimaryKey(field.getName(), recordSet.getObject())) {
                    if (hadOne) {
                        sb.append(", ");
                    } else {
                        hadOne = true;
                        sb.append(" SET ");
                    }
                    sb.append("`").append(field.getName()).append("` = ?");
                    args.add(field.getValue());
                }
            }

            if (!hadOne) {
                continue;
            }

            hadOne = false;

            for (JsdoField field : record.getFields()) {
                if (isPrimaryKey(field.getName(), recordSet.getObject())) {
                    if (hadOne) {
                        sb.append(" AND ");
                    } else {
                        hadOne = true;
                        sb.append(" WHERE ");
                    }
                    sb.append("`").append(field.getName()).append("` = ?");
                    args.add(field.getValue());
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(sb.toString())) {
                for (int i = 0; i < args.size(); i++) {
                    statement.setObject(i + 1, args.get(i));
                }

                statement.execute();
            }
        }
    }

    @Override
    public void delete(JsdoRecordSet recordSet) throws Exception {
        for (JsdoRecord record : recordSet.getRecords()) {
            StringBuilder sb = new StringBuilder();
            List<Object> args = new ArrayList<>();

            sb.append("DELETE FROM `").append(recordSet.getObject().getName()).append("` WHERE ");

            boolean hadOne = false;

            for (JsdoField field : record.getFields()) {
                if (isPrimaryKey(field.getName(), recordSet.getObject())) {
                    if (hadOne) {
                        sb.append(" AND ");
                    } else {
                        hadOne = true;
                    }
                    sb.append("`").append(field.getName()).append("` = ?");
                    args.add(field.getValue());
                }
            }

            if (!hadOne) {
                continue;
            }

            try (PreparedStatement statement = connection.prepareStatement(sb.toString())) {
                for (int i = 0; i < args.size(); i++) {
                    statement.setObject(i + 1, args.get(i));
                }

                statement.execute();
            }
        }
    }

    private boolean isPrimaryKey(String name, JsdoObject object) {
        for (String primaryKey : object.getPrimaryKey()) {
            if (name.equalsIgnoreCase(primaryKey)) {
                return true;
            }
        }

        return false;
    }
}
