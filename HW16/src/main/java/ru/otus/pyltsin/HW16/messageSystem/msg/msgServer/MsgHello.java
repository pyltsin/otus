package ru.otus.pyltsin.HW16.messageSystem.msg.msgServer;

import ru.otus.pyltsin.HW16.app.MessageServer;
import ru.otus.pyltsin.HW16.app.MsgChannel;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

/**
 * Created by Pyltsin on 31.07.2017.
 */
public class MsgHello extends MsgServer {

    public MsgHello(Address address) {
        super(address, new Address(null, TypeAddress.MESSAGE_SERVER), MsgHello.class);
    }
}
