package ru.otus.pyltsin.HW16.messageSystem.msg.msgFrontend;

import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.common.UserDataSet;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class MsgGetUserAnswer extends MsgToFrontend {
    private final UserDataSet user;
    private final int idServlet;


    public MsgGetUserAnswer(Address from, Address to, int idServlet, UserDataSet user) {
        super(from, to, MsgGetUserAnswer.class);
        this.user = user;
        this.idServlet = idServlet;
    }

    @Override
    public void exec(LocalMessageSystem localMessageSystem, FrontendService frontendService) {
        frontendService.sendUser(idServlet, user);
    }
}
