package ru.otus.pyltsin.HW15.app.msg.msgFrontend;

import ru.otus.pyltsin.HW15.app.FrontendService;
import ru.otus.pyltsin.HW15.messageSystem.Address;
import ru.otus.pyltsin.HW15.messageSystem.Addressee;
import ru.otus.pyltsin.HW15.messageSystem.Message;

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