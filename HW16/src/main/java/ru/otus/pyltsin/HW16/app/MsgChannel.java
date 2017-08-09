package ru.otus.pyltsin.HW16.app;

import ru.otus.pyltsin.HW16.messageSystem.Msg;

import java.io.IOException;

/**
 * Created by tully.
 */
public interface MsgChannel {
    void send(Msg msg);

    Msg pool();

    Msg take() throws InterruptedException;

    void close() throws IOException;

    void init();
}
