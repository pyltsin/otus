package ru.otus.pyltsin.HW16.messageSystem.msg.msgServer;

import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.Msg;

/**
 * Created by Pyltsin on 01.08.2017.
 */
public abstract class MsgServer extends Msg {
    public MsgServer(Address from, Address to, Class<?> clazz) {
        super(from, to, clazz);
    }
}