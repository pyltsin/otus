package ru.otus.pyltsin.HW12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.pyltsin.HW12.common.AddressDataSet;
import ru.otus.pyltsin.HW12.common.PhoneDataSet;
import ru.otus.pyltsin.HW12.common.UserDataSet;
import ru.otus.pyltsin.HW12.myCache.CacheEngine;
import ru.otus.pyltsin.HW12.myCache.CacheEngineImpl;
import ru.otus.pyltsin.HW12.service.DBService;
import ru.otus.pyltsin.HW12.service.HibernateDBService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pyltsin on 29.06.2017.
 */
@SuppressWarnings("Duplicates")
public class Runner {
    private final static int PORT = 8081;
    private final static String PUBLIC_HTML = "HW12/public_html";

    private static CacheEngine<Long, UserDataSet> cacheEngine;

    public static void main(String[] args) throws Exception {

        cache();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);
//        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(
                new ServletHolder(
                        new AdminServlet(cacheEngine)), "/login");
        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));
        server.start();
        server.join();

    }

    private static void cache() throws IOException {
        //check Cache

        cacheEngine = new CacheEngineImpl<>(2, 1_000);
        DBService dbService = new HibernateDBService(cacheEngine);

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        Set<PhoneDataSet> list1 = new HashSet<>();
        list1.add(new PhoneDataSet(12, "12345"));
        list1.add(new PhoneDataSet(22, "22345"));

        dbService.save(new UserDataSet("slon", 12, new AddressDataSet("lik", 12),
                list1));


        UserDataSet userDataSet = (UserDataSet) dbService.read(1);
        userDataSet = (UserDataSet) dbService.read(1);
        userDataSet = (UserDataSet) dbService.read(1);
    }
}
