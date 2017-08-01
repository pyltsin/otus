package ru.otus.pyltsin.HW16;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.runner.ProcessRunnerImpl;
import ru.otus.pyltsin.HW16.server.MessageServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by tully.
 * update pyltsin
 */
public class ServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ServerMain.class.getName());

    private static final String CLIENT_START_COMMAND_DB1 = "java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4001,suspend=n -jar HW16/target/client.jar db localhost 8080 db1";
    private static final String CLIENT_START_COMMAND_DB2 = "java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4003,suspend=n -jar HW16/target/client.jar db localhost 8080 db2";
    private static final String CLIENT_START_COMMAND_FR1 = "java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4002,suspend=n -jar HW16/target/client.jar frontend localhost 8080 fr1 8081";
    private static final String CLIENT_START_COMMAND_FR2 = "java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=4004,suspend=n -jar HW16/target/client.jar frontend localhost 8080 fr2 8082";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) throws Exception {
        new ServerMain().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        executorService.schedule(() -> startClient(CLIENT_START_COMMAND_DB1), CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
        executorService.schedule(() -> startClient(CLIENT_START_COMMAND_FR1), CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

        executorService.schedule(() -> startClient(CLIENT_START_COMMAND_DB2), CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);
        executorService.schedule(() -> startClient(CLIENT_START_COMMAND_FR2), CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Server");
        MessageServer server = new MessageServer();
        mbs.registerMBean(server, name);

        server.start();

        executorService.shutdown();
    }

    private void startClient(String txt) {
        logger.debug(txt);
        try {
            new ProcessRunnerImpl().start(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

