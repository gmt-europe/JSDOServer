package nl.gmt.jsdo.server;

import org.eclipse.jetty.http.spi.JettyHttpServer;
import org.eclipse.jetty.http.spi.JettyHttpServerProvider;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;

public class JettyServer implements AutoCloseable {
    private final Server server;
    private final JettyHttpServer jettyServer;
    private final ContextHandlerCollection handlers;

    public JettyServer(int port) throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(
            server,
            new HttpConnectionFactory(new HttpConfiguration())
        );

        connector.setPort(port);

        server.addConnector(connector);

        JettyHttpServerProvider.setServer(server);

        jettyServer = new JettyHttpServer(server, true);

        handlers = new ContextHandlerCollection();

        server.setHandler(handlers);
    }

    @Override
    public void close() throws Exception {
        server.stop();
    }

    public void registerHandler(String path, HttpServlet servlet) {
        ServletContextHandler handler = new ServletContextHandler();

        handler.setContextPath("/");

        handlers.addHandler(handler);

        handler.addServlet(new ServletHolder(servlet), path);
    }

    public void start() throws Exception {
        server.start();
    }
}
