package nl.gmt.jsdo.query;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

public class JsdoCatalog {
    private final String name;
    private final List<JsdoQueryObject> queryObjects = new ArrayList<>();

    public JsdoCatalog(String name) {
        Validate.notNull(name, "name");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<JsdoQueryObject> getQueryObjects() {
        return queryObjects;
    }
}
