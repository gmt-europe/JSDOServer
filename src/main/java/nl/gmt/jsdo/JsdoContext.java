package nl.gmt.jsdo;

import nl.gmt.jsdo.query.JsdoCatalog;
import nl.gmt.jsdo.query.JsdoRecordSet;
import nl.gmt.jsdo.schema.JsdoSchema;

import java.util.List;

public interface JsdoContext {
    JsdoSchema getSchema();

    List<JsdoCatalog> getCatalogs();

    void create(JsdoRecordSet recordSet) throws Exception;

    void update(JsdoRecordSet recordSet) throws Exception;

    void delete(JsdoRecordSet recordSet) throws Exception;
}
