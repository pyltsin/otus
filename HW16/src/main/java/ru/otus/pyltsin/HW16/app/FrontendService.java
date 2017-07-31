package ru.otus.pyltsin.HW16.app;

import ru.otus.pyltsin.HW16.common.UserDataSet;

import java.util.List;
import java.util.Queue;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public interface FrontendService {
    void init();

    void getListId(Queue<List<Integer>> exc);

    void sendListId(int idFront, List<Integer> ids);

    void sendUser(int idFront, UserDataSet userDataSet);

    void getUser(Queue<UserDataSet> id, int exc);
}
