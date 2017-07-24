package ru.otus.pyltsin.HW15.app;

import ru.otus.pyltsin.HW15.common.UserDataSet;

import java.util.List;

/**
 * Created by Pyltsin on 24.07.2017.
 */
public interface DAO {
    public List<UserDataSet> readAll();

    public UserDataSet read(long id);

    void init();
}