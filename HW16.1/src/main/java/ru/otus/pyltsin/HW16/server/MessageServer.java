package ru.otus.pyltsin.HW16.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.MsgChannel;
import ru.otus.pyltsin.HW16.channel.SocketClientChannel;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.Msg;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;
import ru.otus.pyltsin.HW16.messageSystem.msg.msgServer.MsgHello;
import ru.otus.pyltsin.HW16.messageSystem.msg.msgServer.MsgServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tully.
 * update pyltsin
 */
public class MessageServer implements MessageServerMBean {
    private static final Logger logger = LoggerFactory.getLogger(MessageServer.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 8080;
    private static final int MIRROR_DELAY = 100;

    private final ExecutorService executor;
    private final List<MsgChannel> channels;
    private final Map<TypeAddress, Map<Address, MsgChannel>> channelsWithAdress;

    public MessageServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        channels = new ArrayList<>();
        channelsWithAdress = new HashMap<>();
    }

    public void start() throws Exception {
        executor.submit(MessageServer.this::send);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket client = serverSocket.accept(); //blocks
                logger.debug("connect client");
                SocketClientChannel channel = new SocketClientChannel(client);
                channel.init();
                channels.add(channel);
            }
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private Object send() throws InterruptedException {
        while (true) {
            for (MsgChannel channel : channels) {
                Msg msg = channel.pool();
                if (msg != null) {
                    logger.debug("Get the message: " + msg.toString());
                    if (msg instanceof MsgServer) {
                        logger.debug("Get the message for server: " + msg.toString());
                        serverGetMessage(msg, channel);
                    } else {
                        Address addressTo = msg.getTo();
                        logger.debug("Get the message for : " + addressTo.getId());
                        Address adrressSend = getAddressWithBalanced(addressTo);
                        logger.debug("send" + adrressSend.getId());
                        msg.setTo(adrressSend);
                        logger.debug("send to : " + adrressSend.getId());
                        if (adrressSend != null) {
                            channelsWithAdress.get(adrressSend.getTypeAddress()).get(adrressSend).send(msg);
                        }
                    }
                }
            }
            Thread.sleep(MIRROR_DELAY);
        }
    }

    private Address getAddressWithBalanced(Address addressTo) {
        logger.debug("try search" + addressTo.getId());
        TypeAddress typeAddress = addressTo.getTypeAddress();
        if (typeAddress.equals(TypeAddress.DB)) {
            if (!channelsWithAdress.containsKey(typeAddress)) {
                logger.debug("not found:" + addressTo.getId());
                return null;
            } else {
                Map<Address, MsgChannel> dbs = channelsWithAdress.get(typeAddress);
                List<Address> lists = new ArrayList<>(dbs.keySet());
                logger.debug("send to random db");
                int ind = new Random().nextInt(lists.size());
                logger.debug("send to ind db" + ind);
                return lists.get(ind);
            }
        } else {
            if (!channelsWithAdress.containsKey(typeAddress)) {
                logger.debug("not found:" + addressTo.getId());
                return null;
            } else {
                logger.debug("send to current address");
                return addressTo;
            }
        }
    }

    private void serverGetMessage(Msg msg, MsgChannel channel) {
        if (msg instanceof MsgHello) {
            TypeAddress typeAddress = msg.getFrom().getTypeAddress();
            if (!channelsWithAdress.containsKey(typeAddress)) {
                channelsWithAdress.put(typeAddress, new HashMap<>());
                logger.debug("map create new type" + typeAddress);
            }
            logger.debug("set" + msg.getFrom().getId());

            channelsWithAdress.get(typeAddress).put(msg.getFrom(), channel);
        }
    }


    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            executor.shutdown();
            logger.info("Bye.");
        }
    }
}
