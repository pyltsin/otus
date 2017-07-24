package ru.otus.pyltsin.HW15.app.msg.msgDB;

import ru.otus.pyltsin.HW15.app.DBService;
import ru.otus.pyltsin.HW15.messageSystem.Address;
import ru.otus.pyltsin.HW15.messageSystem.Addressee;
import ru.otus.pyltsin.HW15.messageSystem.Message;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public abstract class MsgToBDService extends Message {
    public MsgToBDService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    abstract void exec(DBService addressee);
}
