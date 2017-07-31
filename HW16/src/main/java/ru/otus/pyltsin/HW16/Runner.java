package ru.otus.pyltsin.HW16;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.pyltsin.HW16.app.DBService;
import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.app.MessageSystemContext;
import ru.otus.pyltsin.HW16.dbService.DBServiceImpl;
import ru.otus.pyltsin.HW16.front.FrontendServiceImpl;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.MessageSystem;

/**
 * Created by Pyltsin on 29.06.2017.
 */
@SuppressWarnings("Duplicates")
public class Runner {
    private final static int PORT = 8081;
    private final static String PUBLIC_HTML = "HW16/public_html";

    public static void main(String[] args) throws Exception {
        MessageSystem messageSystem = new MessageSystem();

        MessageSystemContext context = new MessageSystemContext(messageSystem);
        Address frontAddress = new Address("Frontend");
        context.setFrontAddress(frontAddress);
        Address dbAddress = new Address("DB");
        context.setServiceAddress(dbAddress);

        FrontendService frontendService = new FrontendServiceImpl(context, frontAddress);
        frontendService.init();

        DBService dbService = new DBServiceImpl(context, dbAddress);
        dbService.init();

        messageSystem.start();


        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        UserServlet userServlet = new UserServlet(frontendService);
        servletContextHandler.addServlet(new ServletHolder(userServlet), "/user");
        servletContextHandler.addServlet(new ServletHolder(userServlet), "/id");
        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));

        server.start();
        server.join();
    }
}
