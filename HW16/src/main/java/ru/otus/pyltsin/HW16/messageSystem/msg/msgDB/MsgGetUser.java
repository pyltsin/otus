package ru.otus.pyltsin.HW16.messageSystem.msg.msgDB;

import ru.otus.pyltsin.HW16.app.DBService;
import ru.otus.pyltsin.HW16.common.UserDataSet;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;
import ru.otus.pyltsin.HW16.messageSystem.msg.msgFrontend.MsgGetUserAnswer;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class MsgGetUser extends MsgToBDService {

    private final int idServlet;
    private final int idUser;


    public MsgGetUser(Address from, Address to, int idServlet, int idUser) {
        super(from, to, MsgGetUser.class);
        this.idServlet = idServlet;
        this.idUser = idUser;
    }


    @Override
    public void exec(LocalMessageSystem localMessageSystem, DBService dbService) {
        UserDataSet user = (UserDataSet) dbService.read(idUser);
        localMessageSystem.sendMessage(new MsgGetUserAnswer(getTo(), getFrom(), idServlet, user));
    }
}
