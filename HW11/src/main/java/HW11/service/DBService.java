package HW11.service;


import HW11.common.DataSet;

import java.util.List;

/**
 * Created by Pyltsin on 11.06.2017.
 */
public interface DBService {
    DataSet save(DataSet dataSet);

    DataSet read(long id);

    DataSet readByName(String name);

    List<? extends DataSet> readAll();

    String getLocalStatus();

    void shutdown();
}
