package nl.gmt.jsdo.schema;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

public class JsdoService {
    private final String name;
    private final String address;
    private final boolean useRequest;
    private final List<JsdoResource> resources = new ArrayList<>();

    public JsdoService(String name, String address, boolean useRequest) {
        Validate.notNull(name, "name");
        Validate.notNull(address, "address");

        this.name = name;
        this.address = address;
        this.useRequest = useRequest;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isUseRequest() {
        return useRequest;
    }

    public List<JsdoResource> getResources() {
        return resources;
    }
}
