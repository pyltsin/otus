package ru.otus.pyltsin.HW16.app.msg.msgDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.DBService;
import ru.otus.pyltsin.HW16.app.msg.msgFrontend.MsgGetListIdAnswer;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.MessageSystem;

import java.util.List;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class MsgGetListId extends MsgToBDService {

    private static Logger LOG = LoggerFactory.getLogger(MsgGetListId.class);
    private final MessageSystem messageSystem;
    private final int idServlet;

    public MsgGetListId(MessageSystem messageSystem, Address from, Address to, int idServlet) {

        super(from, to);
        this.messageSystem = messageSystem;
        this.idServlet = idServlet;
        LOG.debug("MsgGetSize");

    }


    @Override
    public void exec(DBService dbService) {
        LOG.debug("exec");

        List<Integer> size = dbService.readAllId();
        messageSystem.sendMessage(new MsgGetListIdAnswer(getTo(), getFrom(), idServlet, size));
    }
}
