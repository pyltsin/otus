package ru.otus.pyltsin.HW16.app.msg.msgFrontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.messageSystem.Address;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class MsgGetSizeAnswer extends MsgToFrontend {
    private final int size;
    private final int idServlet;

    private static Logger LOG = LoggerFactory.getLogger(MsgGetSizeAnswer.class);

    public MsgGetSizeAnswer(Address from, Address to,  int idServlet, int size) {
        super(from, to);
        this.size = size;
        this.idServlet = idServlet;
        LOG.debug("MsgGetSizeAnswer");
    }

    @Override
    public void exec(FrontendService frontendService) {
        LOG.debug("exec");
        frontendService.sendSize(idServlet, size);
    }
}
