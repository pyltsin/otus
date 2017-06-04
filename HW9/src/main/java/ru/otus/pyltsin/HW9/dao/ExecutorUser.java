package ru.otus.pyltsin.HW9.dao;

import ru.otus.pyltsin.HW9.common.*;

/**
 * Created by Pyltsin on 04.06.2017. Algo8
 */
public interface ExecutorUser {
    void save(User user);
    User load(long id, Class<?> clazz);
}
