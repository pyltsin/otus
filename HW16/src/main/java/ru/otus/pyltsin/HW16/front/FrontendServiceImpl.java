package ru.otus.pyltsin.HW16.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.common.UserDataSet;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.MessageSystemContext;
import ru.otus.pyltsin.HW16.messageSystem.Msg;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;
import ru.otus.pyltsin.HW16.messageSystem.msg.msgDB.MsgGetListId;
import ru.otus.pyltsin.HW16.messageSystem.msg.msgDB.MsgGetUser;

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
    private static Logger log = LoggerFactory.getLogger(FrontendServiceImpl.class);
    private final Address address;
    private final MessageSystemContext context;
    private Map<Integer, Queue<List<Integer>>> addressIntServlet = new ConcurrentHashMap<>();
    private Map<Integer, Queue<UserDataSet>> addressUserServlet = new ConcurrentHashMap<>();

    public FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    public void init() {
        context.getLocalMessageSystem().addAddressee(this);
        log.debug("init");
    }

    @Override
    public void getListId(Queue<List<Integer>> exc) {
        log.debug("get ids");

        int idServlet = ID_GENERATOR.getAndIncrement();
        addressIntServlet.put(idServlet, exc);
        Msg msg = new MsgGetListId(getAddress(), context.getServiceAddress(), idServlet);
        context.getLocalMessageSystem().sendMessage(msg);
    }

    @Override
    public void sendListId(int idFront, List<Integer> ids) {
        log.debug("send ids");

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
        Msg msg = new MsgGetUser(getAddress(), context.getServiceAddress(), idServlet, id);
        context.getLocalMessageSystem().sendMessage(msg);
    }


    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public TypeAddress getTypeAddress() {
        return TypeAddress.FRONTEND;
    }
}
