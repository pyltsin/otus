package ru.otus.pyltsin.HW10.service;

/**
 * Created by Pyltsin on 11.06.2017.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.otus.pyltsin.HW10.DAO.MyExecutor.Helper.ConnectionHelper;
import ru.otus.pyltsin.HW10.DAO.UserDataSetDAOHibernate;
import ru.otus.pyltsin.HW10.common.AddressDataSet;
import ru.otus.pyltsin.HW10.common.DataSet;
import ru.otus.pyltsin.HW10.common.PhoneDataSet;
import ru.otus.pyltsin.HW10.common.UserDataSet;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;


/**
 * большая часть  нагло украдена из лекции
 */
public class DBServiceHibUserDataSet implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibUserDataSet() throws IOException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);

        Properties properties = ConnectionHelper.getProperties("hibernate.properties");
        configuration.setProperties(properties);
        sessionFactory = createSessionFactory(configuration);
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        return configuration.buildSessionFactory();
    }

    public void save(DataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAOHibernate dao = new UserDataSetDAOHibernate(session);
            dao.save((UserDataSet) dataSet);
        }
    }


    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDataSetDAOHibernate dao = new UserDataSetDAOHibernate(session);
            return dao.read(id);
        });
    }

    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDAOHibernate dao = new UserDataSetDAOHibernate(session);
            return dao.readByName(name);
        });
    }

    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            UserDataSetDAOHibernate dao = new UserDataSetDAOHibernate(session);
            return dao.readAll();
        });
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

}
