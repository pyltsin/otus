package ru.otus.pyltsin.HW16.messageSystem.msg.msgServer;

import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;
import ru.otus.pyltsin.HW16.messageSystem.Msg;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

/**
 * Created by Pyltsin on 31.07.2017.
 */
public class MsgHello extends Msg {
    public MsgHello(Address address) {
        super(address, new Address("", TypeAddress.MESSAGE_SERVER), MsgHello.class);
    }

    @Override
    public void exec(LocalMessageSystem localMessageSystem, Addressee addressee) {
        return;
    }
}
