package ru.otus.pyltsin.HW15.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.otus.pyltsin.HW15.DAO.HibernateDAO;
import ru.otus.pyltsin.HW15.app.DBService;
import ru.otus.pyltsin.HW15.common.DataSet;
import ru.otus.pyltsin.HW15.common.UserDataSet;
import ru.otus.pyltsin.HW15.helper.PropertiesHelper;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;


/**
 * большая часть  нагло украдена из лекции
 */
@SuppressWarnings("ALL")
public class HibernateDBService implements DBService {
    private final SessionFactory sessionFactory;

    public HibernateDBService() throws IOException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        Properties properties = PropertiesHelper.getProperties("hibernate.properties");
        configuration.setProperties(properties);
        sessionFactory = createSessionFactory(configuration);
    }


    private SessionFactory createSessionFactory(Configuration configuration) {
        return configuration.buildSessionFactory();
    }

    public DataSet save(DataSet dataSet) {
        if (!(dataSet instanceof UserDataSet)) {
            throw new IllegalArgumentException();
        }
        UserDataSet out =  runInSession(session -> {
            HibernateDAO dao = new HibernateDAO(session);
            return dao.save((UserDataSet) dataSet);
        });
        return out;
    }

    public UserDataSet read(long id) {
        UserDataSet out = runInSession(session -> {
            HibernateDAO dao = new HibernateDAO(session);
            return dao.read(id);
        });
        return out;
    }

    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            HibernateDAO dao = new HibernateDAO(session);
            return dao.readByName(name);
        });
    }

    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            HibernateDAO dao = new HibernateDAO(session);
            return dao.readAll();
        });
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> session.getTransaction().getStatus().name());
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
