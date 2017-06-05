package ru.otus.pyltsin.HW9.dao;

import ru.otus.pyltsin.HW9.common.*;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
public interface ExecutorUser {
    <T extends DataSet> void save(T dataSet);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
