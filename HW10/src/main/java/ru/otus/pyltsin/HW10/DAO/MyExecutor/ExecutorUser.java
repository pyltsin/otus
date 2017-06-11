package ru.otus.pyltsin.HW10.DAO.MyExecutor;

import ru.otus.pyltsin.HW10.common.DataSet;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
public interface ExecutorUser {
    <T extends DataSet> void save(T dataSet);
    <T extends DataSet> T load(long id, Class<T> clazz);

    <T extends DataSet> T findByParam(String param, String name, Class<T> clazz);
}
