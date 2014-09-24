package nl.gmt.jsdo.schema;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsdoSchema {
    private final String version;
    private final Date lastModified;
    private final List<JsdoService> services = new ArrayList<>();

    public JsdoSchema(String version, Date lastModified) {
        Validate.notNull(version, "version");
        Validate.notNull(lastModified, "lastModified");

        this.version = version;
        this.lastModified = lastModified;
    }

    public String getVersion() {
        return version;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public List<JsdoService> getServices() {
        return services;
    }
}
