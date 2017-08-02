package ru.otus.pyltsin.HW16.messageSystem.msg.msgServer;

import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.app.MessageServer;
import ru.otus.pyltsin.HW16.app.MsgChannel;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;
import ru.otus.pyltsin.HW16.messageSystem.Msg;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

/**
 * Created by Pyltsin on 01.08.2017.
 */
public abstract class MsgServer extends Msg {
    public MsgServer(Address from, Address to, Class<?> clazz) {
        super(from, to, clazz);
    }

    @Override
    public TypeAddress getTypeReciever() {
        return TypeAddress.MESSAGE_SERVER;
    }

    @Override
    public void exec(LocalMessageSystem localMessageSystem, Addressee addressee) {
        throw new UnsupportedOperationException();
    }

    public abstract void execServer(MessageServer messageServer, MsgChannel channel);
}