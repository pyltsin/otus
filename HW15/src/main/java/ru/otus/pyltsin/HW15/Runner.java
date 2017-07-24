package ru.otus.pyltsin.HW15;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.pyltsin.HW15.service.HibernateDBService;

/**
 * Created by Pyltsin on 29.06.2017.
 */
@SuppressWarnings("Duplicates")
public class Runner {
    private final static int PORT = 8081;
    private final static String PUBLIC_HTML = "HW15/public_html";

    public static void main(String[] args) throws Exception {

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        UserServlet userServlet = new UserServlet(new HibernateDBService());
        servletContextHandler.addServlet(new ServletHolder(userServlet), "/user");
        servletContextHandler.addServlet(new ServletHolder(userServlet), "/id");
        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));
        server.start();
        server.join();
    }
}
