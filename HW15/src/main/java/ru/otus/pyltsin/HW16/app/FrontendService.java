package ru.otus.pyltsin.HW16.app;

import ru.otus.pyltsin.HW16.common.UserDataSet;

import java.util.Queue;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public interface FrontendService {
    void init();

    void getSize(Queue<Integer> exc);

    void sendSize(int idFront, int size);

    void sendUser(int idFront, UserDataSet userDataSet);

    void getUser(Queue<UserDataSet> id, int exc);
}
