package ru.otus.pyltsin.HW15.app;


import ru.otus.pyltsin.HW15.common.DataSet;

import java.util.List;

/**
 * Created by Pyltsin on 11.06.2017.
 */
public interface DBService {
    void init();

    DataSet save(DataSet dataSet);

    DataSet read(long id);

    DataSet readByName(String name);

    List<? extends DataSet> readAll();

    void shutdown();
}
