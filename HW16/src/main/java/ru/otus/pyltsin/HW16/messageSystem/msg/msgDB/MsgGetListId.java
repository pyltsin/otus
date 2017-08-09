package ru.otus.pyltsin.HW16.messageSystem.msg.msgDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.DBService;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;
import ru.otus.pyltsin.HW16.messageSystem.msg.msgFrontend.MsgGetListIdAnswer;

import java.util.List;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class MsgGetListId extends MsgToDBService {

    private static Logger LOG = LoggerFactory.getLogger(MsgGetListId.class);
    private final int idServlet;

    public MsgGetListId(Address from, Address to, int idServlet) {

        super(from, to, MsgGetListId.class);
        this.idServlet = idServlet;
        LOG.debug("MsgGetSize");

    }

    @Override
    public void exec(LocalMessageSystem localMessageSystem, DBService dbService) {
        LOG.debug("exec");
        List<Integer> size = dbService.readAllId();
        localMessageSystem.sendMessage(new MsgGetListIdAnswer(getTo(), getFrom(), idServlet, size));
    }
}
