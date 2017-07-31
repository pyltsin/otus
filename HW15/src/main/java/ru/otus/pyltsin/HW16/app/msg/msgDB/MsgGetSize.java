package ru.otus.pyltsin.HW16.app.msg.msgDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.DBService;
import ru.otus.pyltsin.HW16.app.msg.msgFrontend.MsgGetSizeAnswer;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.MessageSystem;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class MsgGetSize extends MsgToBDService {

    private static Logger LOG = LoggerFactory.getLogger(MsgGetSize.class);
    private final MessageSystem messageSystem;
    private final int idServlet;

    public MsgGetSize(MessageSystem messageSystem, Address from, Address to, int idServlet) {

        super(from, to);
        this.messageSystem = messageSystem;
        this.idServlet = idServlet;
        LOG.debug("MsgGetSize");

    }


    @Override
    public void exec(DBService dbService) {
        LOG.debug("exec");

        int size = dbService.readAll().size();
        messageSystem.sendMessage(new MsgGetSizeAnswer(getTo(), getFrom(), idServlet, size));
    }
}
