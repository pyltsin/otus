package ru.otus.pyltsin.HW16;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.app.MsgChannel;
import ru.otus.pyltsin.HW16.channel.SocketClientManagedChanel;
import ru.otus.pyltsin.HW16.dbService.DBServiceImpl;
import ru.otus.pyltsin.HW16.front.FrontendServiceImpl;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;
import ru.otus.pyltsin.HW16.messageSystem.MessageSystemContext;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Created by Pyltsin on 31.07.2017.
 */
public class RunnerClients {
    private static final String NAME_FRONTEND = "frontend";
    private static final String NAME_DB = "db";
    private final static String PUBLIC_HTML = "HW16/public_html";

    /**
     * @param args - 0 - name client, 1 - host; 2 - port, 3 - address (String), 4 - port for web.
     */
    public static void main(String[] args) throws Exception {

        if (args.length < 4) {
            throw new UnsupportedOperationException("not found all parameter");
        }

        String host = args[1];
        if (host == null || host.equals("")) {
            throw new UnsupportedOperationException("Not found host");
        }
        int port;
        try {
            port = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException(e);
        }

        MsgChannel msgChannel = new SocketClientManagedChanel(host, port);
        LocalMessageSystem localMessageSystem = new LocalMessageSystem(msgChannel);
        MessageSystemContext context = new MessageSystemContext(localMessageSystem);

        Addressee service;

        if (args[3] == null || args[3].equals("")) {
            throw new UnsupportedOperationException("Not found name service");
        }

        String address = args[3];


        int portForWeb = 0;

        FrontendServiceImpl fs = null;
        switch (args[0]) {
            case NAME_DB:
                service = new DBServiceImpl(context, new Address(address));
                break;
            case NAME_FRONTEND:
                fs = new FrontendServiceImpl(context, new Address(address));
                service = fs;
                try {
                    portForWeb = Integer.parseInt(args[4]);
                } catch (NumberFormatException e) {
                    throw new UnsupportedOperationException(e);
                }
                break;
            default:
                throw new UnsupportedOperationException("Not found service");
        }

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=" + args[0]);
        mbs.registerMBean(localMessageSystem, name);

        service.init();
        localMessageSystem.start();
        if (fs != null) {
            startFrontendServer(fs, portForWeb);
        }

    }

    private static void startFrontendServer(FrontendServiceImpl fs, int port) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        UserServlet userServlet = new UserServlet(fs);
        servletContextHandler.addServlet(new ServletHolder(userServlet), "/user");
        servletContextHandler.addServlet(new ServletHolder(userServlet), "/id");
        Server server = new Server(port);
        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));

        server.start();
        server.join();

    }
}
