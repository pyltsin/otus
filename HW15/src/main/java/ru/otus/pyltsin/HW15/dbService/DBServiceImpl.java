package ru.otus.pyltsin.HW15.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.otus.pyltsin.HW15.dao.DAOImpl;
import ru.otus.pyltsin.HW15.app.DBService;
import ru.otus.pyltsin.HW15.app.MessageSystemContext;
import ru.otus.pyltsin.HW15.common.DataSet;
import ru.otus.pyltsin.HW15.common.UserDataSet;
import ru.otus.pyltsin.HW15.helper.PropertiesHelper;
import ru.otus.pyltsin.HW15.messageSystem.Address;
import ru.otus.pyltsin.HW15.messageSystem.Addressee;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;


/**
 * большая часть  нагло украдена из лекции
 */
@SuppressWarnings("ALL")
public class DBServiceImpl implements DBService, Addressee {
    private final SessionFactory sessionFactory;

    private final Address address;
    private final MessageSystemContext context;


    public DBServiceImpl(MessageSystemContext context, Address address) throws IOException {
        this.context = context;
        this.address = address;

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        Properties properties = PropertiesHelper.getProperties("hibernate.properties");
        configuration.setProperties(properties);
        sessionFactory = createSessionFactory(configuration);
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }


    private SessionFactory createSessionFactory(Configuration configuration) {
        return configuration.buildSessionFactory();
    }

    public DataSet save(DataSet dataSet) {
        if (!(dataSet instanceof UserDataSet)) {
            throw new IllegalArgumentException();
        }
        UserDataSet out = runInSession(session -> {
            DAOImpl dao = new DAOImpl(session);
            return dao.save((UserDataSet) dataSet);
        });
        return out;
    }

    public UserDataSet read(long id) {
        UserDataSet out = runInSession(session -> {
            DAOImpl dao = new DAOImpl(session);
            return dao.read(id);
        });
        return out;
    }

    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            DAOImpl dao = new DAOImpl(session);
            return dao.readByName(name);
        });
    }

    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            DAOImpl dao = new DAOImpl(session);
            return dao.readAll();
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
