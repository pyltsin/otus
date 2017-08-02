package ru.otus.pyltsin.HW16.messageSystem;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.app.MsgChannel;
import ru.otus.pyltsin.HW16.messageSystem.msg.msgServer.MsgHello;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author tully
 * update pyltsin
 */
public final class LocalMessageSystem implements LocalMessageSystemMBean {
    private static final int DEFAULT_STEP_TIME = 10;
    private static Logger log = LoggerFactory.getLogger(LocalMessageSystem.class);
    private final Map<Address, ConcurrentLinkedQueue<Msg>> messagesMap = new HashMap<>();
    private final Map<Address, Addressee> addresseeMap = new HashMap<>();
    private final MsgChannel msgChannel;

    private Thread threadGetMsg;
    private volatile boolean flagShutdown = false;

    public LocalMessageSystem(MsgChannel msgChannel) {
        this.msgChannel = msgChannel;
    }

    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new ConcurrentLinkedQueue<>());
        sendMessage(new MsgHello(addressee.getAddress(), addressee.getTypeAddress()));
        log.debug("send hello");
    }

    public void sendMessage(Msg msg) {
        msgChannel.send(msg);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void start() {
        //start executor
        log.debug("start message system");
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + entry.getKey().getId());
                while (!flagShutdown) {
                    ConcurrentLinkedQueue<Msg> queue = messagesMap.get(entry.getKey());
                    while (!queue.isEmpty()) {
                        Msg msg = queue.poll();
                        msg.exec(this, entry.getValue());
                    }
                    try {
                        Thread.sleep(LocalMessageSystem.DEFAULT_STEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        //start get message;
        getExecuteMsg();
        //start channel
        msgChannel.init();
    }

    private void getExecuteMsg() {
        threadGetMsg = new Thread(() -> {
            while (!flagShutdown) {
                try {
                    Msg msg = msgChannel.take();
                    ConcurrentLinkedQueue<Msg> messages = messagesMap.get(msg.getTo());
                    if (messages == null) {
                        throw new NotFoundException("Not found address");
                    }
                    messages.add(msg);
                } catch (InterruptedException e) {
                    return;
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        threadGetMsg.start();
    }

    @Override
    public boolean getRunning() {
        return flagShutdown;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            try {
                msgChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                threadGetMsg.interrupt();
                flagShutdown = true;
                log.info("Bye.");
            }
        }
    }
}
