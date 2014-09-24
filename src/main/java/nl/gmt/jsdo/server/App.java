package nl.gmt.jsdo.server;

import nl.gmt.jsdo.demo.DemoContext;
import nl.gmt.jsdo.http.JsdoServlet;

public class App {
    public static void main(String[] args) {
        try {
            try (JettyServer server = new JettyServer(5851)) {
                server.registerHandler("/jsdo/*", new JsdoServlet(new DemoContext()));

                server.start();

                System.out.println("Web server started at http://localhost:5851/jsdo");
                System.out.println("Press any key to exit");
                System.in.read();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
