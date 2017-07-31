package ru.otus.pyltsin.HW16.app.msg.msgFrontend;

import ru.otus.pyltsin.HW16.app.FrontendService;
import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.Addressee;
import ru.otus.pyltsin.HW16.messageSystem.Message;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            exec((FrontendService) addressee);
        }
    }

    public abstract void exec(FrontendService frontendService);
}