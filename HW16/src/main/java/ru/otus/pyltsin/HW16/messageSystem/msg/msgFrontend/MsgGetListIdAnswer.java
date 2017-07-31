package ru.otus.pyltsin.HW16.messageSystem.msg.msgFrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;

import java.util.List;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class MsgGetListIdAnswer extends MsgToFrontend {
    private static Logger LOG = LoggerFactory.getLogger(MsgGetListIdAnswer.class);
    private final List<Integer> ids;
    private final int idServlet;

    public MsgGetListIdAnswer(Address from, Address to, int idServlet, List<Integer> ids) {
        super(from, to, MsgGetListIdAnswer.class);
        this.ids = ids;
        this.idServlet = idServlet;
        LOG.debug("MsgGetSizeAnswer");
    }

    @Override
    public void exec(LocalMessageSystem localMessageSystem, FrontendService frontendService) {
        LOG.debug("exec");
        frontendService.sendListId(idServlet, ids);
    }
}
