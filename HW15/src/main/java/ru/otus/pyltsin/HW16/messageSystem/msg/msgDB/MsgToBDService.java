package ru.otus.pyltsin.HW16.messageSystem.msg.msgDB;

import ru.otus.pyltsin.HW16.app.DBService;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.Addressee;
import ru.otus.pyltsin.HW16.messageSystem.Message;

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
