package nl.gmt.jsdo.query;

import java.util.ArrayList;
import java.util.List;

public class JsdoRecord {
    private final List<JsdoField> fields = new ArrayList<>();

    public List<JsdoField> getFields() {
        return fields;
    }
}
