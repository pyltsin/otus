package ru.otus.pyltsin.HW16.server;


import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.app.MessageServer;
import ru.otus.pyltsin.HW16.app.MsgChannel;
import ru.otus.pyltsin.HW16.channel.SocketClientChannel;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.Msg;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;
import ru.otus.pyltsin.HW16.messageSystem.msg.msgServer.MsgServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tully.
 * update pyltsin
 */
public class MessageServerImpl implements MessageServerImplMBean, Addressee, MessageServer {
    private static final Logger logger = LoggerFactory.getLogger(MessageServerImpl.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 8080;
    private static final int MIRROR_DELAY = 100;

    private final ExecutorService executor;
    private final List<MsgChannel> channels;
    private final Map<TypeAddress, Map<Address, MsgChannel>> channelsWithAdress;
    private final Address address;

    public MessageServerImpl(Address address) {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        channels = new ArrayList<>();
        channelsWithAdress = new HashMap<>();
        this.address = address;
    }

    public void init() {
        executor.submit(MessageServerImpl.this::sendMessages);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket client = serverSocket.accept(); //blocks
                logger.debug("connect client");
                SocketClientChannel channel = new SocketClientChannel(client);
                channel.init();
                channels.add(channel);
            }
        } catch (IOException e) {
            logger.error("socket failed");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private Object sendMessages() throws InterruptedException {
        while (true) {
            for (MsgChannel channel : channels) {
                Msg msg = channel.pool();
                if (msg != null) {
                    logger.debug("Get the message: " + msg.toString());
                    if (msg.getTypeReciever() == TypeAddress.MESSAGE_SERVER || msg.getTo().equals(address)) {
                        logger.debug("Get the message for server: " + msg.toString());
                        serverGetMessage(msg, channel);
                    } else {
                        Address addressTo = msg.getTo();
                        logger.debug("Get the message for : " + addressTo.getId());
                        Address addressSend = getAddressWithBalanced(msg);
                        logger.debug("sendMessages" + addressSend.getId());
                        msg.setTo(addressSend);
                        logger.debug("sendMessages to : " + addressSend.getId());
                        if (addressSend != null) {
                            channelsWithAdress.get(msg.getTypeReciever()).get(addressSend).send(msg);
                        } else {
                            logger.error("address send not found for : " + addressTo.getId());
                        }
                    }
                }
            }
            Thread.sleep(MIRROR_DELAY);
        }
    }

    @Nullable
    private Address getAddressWithBalanced(Msg msg) {
        Address addressTo = msg.getTo();
        logger.debug("try search" + addressTo.getId());
        TypeAddress typeAddress = msg.getTypeReciever();
        if (typeAddress.equals(TypeAddress.DB)) {
            if (!channelsWithAdress.containsKey(typeAddress)) {
                logger.debug("not found:" + addressTo.getId());
                return null;
            } else {
                Map<Address, MsgChannel> dbs = channelsWithAdress.get(typeAddress);
                List<Address> lists = new ArrayList<>(dbs.keySet());
                logger.debug("sendMessages to random db");
                int ind = new Random().nextInt(lists.size());
                logger.debug("sendMessages to ind db" + ind);
                return lists.get(ind);
            }
        } else {
            if (!channelsWithAdress.containsKey(typeAddress)) {
                logger.debug("not found:" + addressTo.getId());
                return null;
            } else {
                logger.debug("sendMessages to current address");
                return addressTo;
            }
        }
    }

    private void serverGetMessage(Msg msg, MsgChannel channel) {
        if (msg instanceof MsgServer && msg.getTypeReciever().equals(TypeAddress.MESSAGE_SERVER)) {
            ((MsgServer) msg).execServer(this, channel);
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

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public TypeAddress getTypeAddress() {
        return TypeAddress.MESSAGE_SERVER;
    }

    @Override
    public void registerChannel(MsgChannel channel, Address from, TypeAddress typeAddress) {
        if (!channelsWithAdress.containsKey(typeAddress)) {
            channelsWithAdress.put(typeAddress, new HashMap<>());
            logger.debug("map create new type" + typeAddress);
        }
        logger.debug("set" + from.getId());

        channelsWithAdress.get(typeAddress).put(from, channel);
    }
}
