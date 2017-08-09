package ru.otus.pyltsin.HW16.messageSystem.msg.msgServer;

import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.app.MessageServer;
import ru.otus.pyltsin.HW16.app.MsgChannel;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;
import ru.otus.pyltsin.HW16.messageSystem.Msg;

/**
 * Created by Pyltsin on 01.08.2017.
 */
public abstract class MsgServer extends Msg {
    MsgServer(Address from, Address to, Class<?> clazz) {
        super(from, to, clazz);
    }

    @Override
    public void exec(LocalMessageSystem localMessageSystem, Addressee addressee) {
        throw new UnsupportedOperationException();
    }
}