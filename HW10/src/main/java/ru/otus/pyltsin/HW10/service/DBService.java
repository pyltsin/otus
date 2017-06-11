package ru.otus.pyltsin.HW10.service;

import ru.otus.pyltsin.HW10.common.DataSet;
import ru.otus.pyltsin.HW10.common.UserDataSet;

import java.util.List;

/**
 * Created by Pyltsin on 11.06.2017. Algo8
 */
public interface DBService {
    void save(DataSet dataSet);

    DataSet read(long id);

    DataSet readByName(String name);

    List<? extends UserDataSet> readAll();

    String getLocalStatus();

    void shutdown();
}
