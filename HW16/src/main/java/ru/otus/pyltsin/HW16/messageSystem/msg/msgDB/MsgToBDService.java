package ru.otus.pyltsin.HW16.messageSystem.msg.msgDB;

import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.app.DBService;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;
import ru.otus.pyltsin.HW16.messageSystem.Msg;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public abstract class MsgToBDService extends Msg {

    public MsgToBDService(Address from, Address to, Class<?> clazz) {
        super(from, to, clazz);
    }

    @Override
    public void exec(LocalMessageSystem localMessageSystem, Addressee addressee) {
        if (addressee instanceof DBService) {
            exec(localMessageSystem, (DBService) addressee);
        }
    }

    @Override
    public TypeAddress getTypeReciever() {
        return TypeAddress.DB;
    }

    abstract void exec(LocalMessageSystem localMessageSystem, DBService addressee);
}
