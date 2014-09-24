package nl.gmt.jsdo.http;

import nl.gmt.jsdo.JsdoContext;
import nl.gmt.jsdo.query.*;
import nl.gmt.jsdo.schema.JsdoSchemaSerializer;
import org.apache.commons.lang.Validate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsdoServlet extends HttpServlet {
    private final JsdoContext context;

    public JsdoServlet(JsdoContext context) {
        Validate.notNull(context, "context");

        this.context = context;
    }

    private String translatePath(HttpServletRequest req) {
        String path = req.getRequestURI();
        String prefix = req.getServletPath();
        if (prefix.length() == 0 || prefix.charAt(prefix.length() - 1) != '/') {
            prefix += "/";
        }

        assert path.startsWith(prefix);

        return path.substring(prefix.length());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = translatePath(req);

        switch (path) {
            case "catalog.json":
                doGetCatalog(req, resp);
                break;

            case "":
                doGetWelcome(req, resp);
                break;

            default:
                if (!doGetQuery(req, resp, path)) {
                    super.doGet(req, resp);
                }
                break;
        }
    }

    private boolean doGetQuery(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException {
        int pos = path.indexOf('/');
        if (pos != -1) {
            String catalogName = path.substring(0, pos);
            path = path.substring(pos + 1);
            JsdoCatalog catalog = null;

            for (JsdoCatalog item : context.getCatalogs()) {
                if (item.getName().equals(catalogName)) {
                    catalog = item;
                    break;
                }
            }

            if (catalog != null) {
                pos = path.indexOf('/');
                if (pos == -1) {
                    pos = path.length();
                }
                String objectName = path.substring(0, pos);
                JsdoQueryObject object = null;

                for (JsdoQueryObject item : catalog.getQueryObjects()) {
                    if (item.getObject().getName().equals(objectName)) {
                        object = item;
                        break;
                    }
                }

                if (object != null) {
                    doGetQuery(req, resp, object);
                    return true;
                }
            }
        }

        return false;
    }

    private void doGetQuery(HttpServletRequest req, HttpServletResponse resp, JsdoQueryObject object) throws ServletException {
        try {
            JsdoRecordSet recordSet = object.getRecords(req.getParameter("filter"));

            resp.setContentType("application/json");

            new JsdoRecordSetSerializer().serialize(
                resp.getWriter(),
                recordSet
            );
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void doGetWelcome(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter w = resp.getWriter();
        w.println("Welcome to the demo JSDO server");
    }

    private void doGetCatalog(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.addHeader("Content-Disposition", "attachment; filename=\"catalog.json\"");

        new JsdoSchemaSerializer().serialize(
            resp.getWriter(),
            context.getSchema()
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsdoRecordSet recordSet = new JsdoRecordSetDeserializer(context.getSchema()).deserialize(req.getReader());

        try {
            context.create(recordSet);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsdoRecordSet recordSet = new JsdoRecordSetDeserializer(context.getSchema()).deserialize(req.getReader());

        try {
            context.update(recordSet);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsdoRecordSet recordSet = new JsdoRecordSetDeserializer(context.getSchema()).deserialize(req.getReader());

        try {
            context.delete(recordSet);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
