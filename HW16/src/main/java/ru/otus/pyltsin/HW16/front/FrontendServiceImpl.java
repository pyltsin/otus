package ru.otus.pyltsin.HW16.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.app.MessageSystemContext;
import ru.otus.pyltsin.HW16.app.msg.msgDB.MsgGetListId;
import ru.otus.pyltsin.HW16.app.msg.msgDB.MsgGetUser;
import ru.otus.pyltsin.HW16.common.UserDataSet;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.Addressee;
import ru.otus.pyltsin.HW16.messageSystem.Message;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class FrontendServiceImpl implements FrontendService, Addressee {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final Address address;
    private final MessageSystemContext context;
    private Map<Integer, Queue<List<Integer>>> addressIntServlet = new ConcurrentHashMap<>();
    private Map<Integer, Queue<UserDataSet>> addressUserServlet = new ConcurrentHashMap<>();

    private static Logger LOG = LoggerFactory.getLogger(FrontendServiceImpl.class);

    public FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
        LOG.debug("init");
    }

    @Override
    public void getListId(Queue<List<Integer>> exc) {
        LOG.debug("get ids");

        int idServlet = ID_GENERATOR.getAndIncrement();
        addressIntServlet.put(idServlet, exc);
        Message message = new MsgGetListId(context.getMessageSystem(), getAddress(), context.getServiceAddress(), idServlet);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void sendListId(int idFront, List<Integer> ids) {
        LOG.debug("send ids");

        Queue<List<Integer>> exchanger = addressIntServlet.remove(idFront);
        exchanger.add(ids);
    }

    @Override
    public void sendUser(int idFront, UserDataSet userDataSet) {
        Queue<UserDataSet> exchanger = addressUserServlet.remove(idFront);
        exchanger.add(userDataSet);
    }

    @Override
    public void getUser(Queue<UserDataSet> exc, int id) {
        int idServlet = ID_GENERATOR.getAndIncrement();
        addressUserServlet.put(idServlet, exc);
        Message message = new MsgGetUser(context.getMessageSystem(), getAddress(), context.getServiceAddress(), idServlet, id);
        context.getMessageSystem().sendMessage(message);
    }


    @Override
    public Address getAddress() {
        return address;
    }
}
