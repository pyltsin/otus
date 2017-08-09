package ru.otus.pyltsin.HW16.server;


import org.eclipse.jetty.util.BlockingArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.AddressableMsgChannel;
import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.channel.AddressableSocketClientChannel;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.Msg;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

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
public class MessageServerImpl implements MessageServerImplMBean, Addressee {
    private static final Logger logger = LoggerFactory.getLogger(MessageServerImpl.class.getName());

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 8080;
    private static final int MIRROR_DELAY = 100;

    private final ExecutorService executor;
    private final List<AddressableMsgChannel> channels;
    private final Address address;

    public MessageServerImpl(Address address) {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        channels = new BlockingArrayQueue<>();
        this.address = address;
    }

    public void init() {
        executor.submit(MessageServerImpl.this::sendMessages);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket client = serverSocket.accept(); //blocks
                logger.debug("connect client");
                AddressableSocketClientChannel channel = new AddressableSocketClientChannel(client);
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
            for (AddressableMsgChannel channel : channels) {
                Msg msg = channel.pool();
                if (msg != null) {
                    logger.debug("Get the message: " + msg.toString());
                    if (msg.getTo().getTypeAddress() == TypeAddress.MESSAGE_SERVER) {
                        logger.debug("Get the message for server: " + msg.toString());
                        registerChannel(channel, msg.getFrom());
                    } else {
                        sendWithBalanced(msg);
                    }
                }
            }
            Thread.sleep(MIRROR_DELAY);
        }
    }

    private void sendWithBalanced(Msg msg) {
        Address addressTo = msg.getTo();
        logger.debug("Get the message for : " + addressTo.getId());

        if (addressTo.getId() == null || "".equals(addressTo.getId())) {
            sendByType(msg);
        } else {
            sendById(msg);
        }
    }

    private void sendByType(Msg msg) {
        List<AddressableMsgChannel> list = new ArrayList<>();
        for (AddressableMsgChannel channel : channels) {
            if (channel.getTypeAddress() == msg.getTo().getTypeAddress()) {
                list.add(channel);
            }
        }
        if (list.size() == 0) {
            logger.warn("Not found Type: " + msg.getTo().getTypeAddress());
        } else {
            Random random = new Random();
            int randomNumber = random.nextInt(list.size());
            AddressableMsgChannel channelTo = list.get(randomNumber);
            logger.debug("Send messege for : " + channelTo.getIdAddress());
            Address to = channelTo.getAddress();
            msg.setTo(to);
            channelTo.send(msg);
        }
    }

    private void sendById(Msg msg) {
        for (AddressableMsgChannel channel : channels) {
            if (channel.getIdAddress() != null && channel.getIdAddress().equals(msg.getTo().getId())) {
                logger.debug("Send messege for : " + channel.getIdAddress());
                channel.send(msg);
                return;
            }
        }
        logger.warn("Not found Address: " + msg.getTo().getId());

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

    private void registerChannel(AddressableMsgChannel channel, Address from) {
        channel.setAddress(from);
    }
}
