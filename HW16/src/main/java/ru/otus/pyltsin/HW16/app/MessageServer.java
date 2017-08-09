package ru.otus.pyltsin.HW16.app;

import ru.otus.pyltsin.HW16.messageSystem.Address;
import ru.otus.pyltsin.HW16.messageSystem.TypeAddress;

/**
 * Created by Pyltsin on 02.08.2017.
 */
public interface MessageServer {
    void registerChannel(MsgChannel channel, Address from);
}
