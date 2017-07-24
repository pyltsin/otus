package ru.otus.pyltsin.HW15.app.msg.msgDB;

import ru.otus.pyltsin.HW15.app.DBService;
import ru.otus.pyltsin.HW15.app.msg.msgFrontend.MsgGetUserAnswer;
import ru.otus.pyltsin.HW15.common.UserDataSet;
import ru.otus.pyltsin.HW15.messageSystem.Address;
import ru.otus.pyltsin.HW15.messageSystem.MessageSystem;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public class MsgGetUser extends MsgToBDService {

    private final MessageSystem messageSystem;
    private final int idServlet;
    private final int idUser;


    public MsgGetUser(MessageSystem messageSystem, Address from, Address to, int idServlet, int idUser) {
        super(from, to);
        this.messageSystem = messageSystem;
        this.idServlet = idServlet;
        this.idUser = idUser;
    }


    @Override
    public void exec(DBService dbService) {
        UserDataSet user = (UserDataSet) dbService.read(idUser);
        messageSystem.sendMessage(new MsgGetUserAnswer(getTo(), getFrom(), idServlet, user));
    }
}
