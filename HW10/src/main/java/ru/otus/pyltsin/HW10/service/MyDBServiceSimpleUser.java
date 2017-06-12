package ru.otus.pyltsin.HW10.service;

import ru.otus.pyltsin.HW10.DAO.MyExecutor.Helper.ConnectionHelper;
import ru.otus.pyltsin.HW10.DAO.MyExecutor.MyExecutorUser;
import ru.otus.pyltsin.HW10.common.DataSet;
import ru.otus.pyltsin.HW10.common.SimpleUser;
import ru.otus.pyltsin.HW10.common.UserDataSet;

import java.util.List;

/**
 * Created by Pyltsin on 11.06.2017.
 */
public class MyDBServiceSimpleUser implements DBService {
    private final MyExecutorUser myExecutorUser;

    public MyDBServiceSimpleUser(MyExecutorUser myExecutorUser) {
        this.myExecutorUser = myExecutorUser;
    }

    public MyDBServiceSimpleUser() {
        myExecutorUser = new MyExecutorUser();
    }

    @Override
    public DataSet save(DataSet dataSet) {
        if (dataSet instanceof SimpleUser) {
            SimpleUser simpleUser = (SimpleUser) dataSet;
            myExecutorUser.save(simpleUser);
        } else {
            throw new UnsupportedOperationException();
        }
        return dataSet;
    }

    @Override
    public DataSet read(long id) {
        return myExecutorUser.load(id, SimpleUser.class);
    }

    @Override
    public DataSet readByName(String name) {
        return myExecutorUser.findByParam("name", name, SimpleUser.class);
    }

    @Override
    public List<UserDataSet> readAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLocalStatus() {
        return ConnectionHelper.test();
    }

    @Override
    public void shutdown() {
    }
}
