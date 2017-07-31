package ru.otus.pyltsin.HW16.app.msg.msgFrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.messageSystem.Address;

import java.util.List;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class MsgGetListIdAnswer extends MsgToFrontend {
    private final List<Integer> ids;
    private final int idServlet;

    private static Logger LOG = LoggerFactory.getLogger(MsgGetListIdAnswer.class);

    public MsgGetListIdAnswer(Address from, Address to,  int idServlet, List<Integer> ids) {
        super(from, to);
        this.ids = ids;
        this.idServlet = idServlet;
        LOG.debug("MsgGetSizeAnswer");
    }

    @Override
    public void exec(FrontendService frontendService) {
        LOG.debug("exec");
        frontendService.sendListId(idServlet, ids);
    }
}
