package ru.otus.pyltsin.HW16.messageSystem.msg.msgServer;

import ru.otus.pyltsin.HW16.app.MessageServer;
import ru.otus.pyltsin.HW16.app.MsgChannel;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

/**
 * Created by Pyltsin on 31.07.2017.
 */
public class MsgHello extends MsgServer {
    private final TypeAddress typeAddress;
    public MsgHello(Address address, TypeAddress typeAddress) {
        super(address, new Address(TypeAddress.MESSAGE_SERVER), MsgHello.class);
        this.typeAddress = typeAddress;
    }

    @Override
    public void execServer(MessageServer messageServer, MsgChannel channel) {
        messageServer.registerChannel(channel, getFrom(), typeAddress);
    }
}
