package ru.otus.pyltsin.HW16.messageSystem.msg.msgFrontend;

import ru.otus.pyltsin.HW16.app.Addressee;
import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.LocalMessageSystem;
import ru.otus.pyltsin.HW16.messageSystem.Msg;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public abstract class MsgToFrontend extends Msg {
    @Override
    public TypeAddress getTypeReciever() {
        return TypeAddress.FRONTEND;
    }

    public MsgToFrontend(Address from, Address to, Class<?> clazz) {
        super(from, to, clazz);
    }

    @Override
    public void exec(LocalMessageSystem localMessageSystem, Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec(localMessageSystem, (FrontendService) addressee);
        }
    }

    public abstract void exec(LocalMessageSystem localMessageSystem, FrontendService frontendService);
}