package HW11.service;

import HW11.DAO.HibernateDAO;
import HW11.common.AddressDataSet;
import HW11.common.DataSet;
import HW11.common.PhoneDataSet;
import HW11.common.UserDataSet;
import HW11.myCache.CacheElement;
import HW11.myCache.CacheEngine;
import HW11.myCache.CacheEngineImpl;
import HW11.service.Helper.ConnectionHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
    private CacheEngine<Long, UserDataSet> cacheEngine;

    public HibernateDBService() throws IOException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);

        Properties properties = ConnectionHelper.getProperties("hibernate.properties");
        configuration.setProperties(properties);
        sessionFactory = createSessionFactory(configuration);
        this.cacheEngine = new CacheEngineImpl<>(10, 30_000);
    }

    public HibernateDBService(CacheEngine<Long, UserDataSet> cacheEngine) throws IOException {
        this();
        this.cacheEngine = cacheEngine;
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
        cacheEngine.put(new CacheElement<>(out.getId(), out));
        return out;
    }

    public UserDataSet read(long id) {
        CacheElement el = cacheEngine.get(id);
        if (el != null) {
            return (UserDataSet) el.getValue();
        }

        UserDataSet out = runInSession(session -> {
            HibernateDAO dao = new HibernateDAO(session);
            return dao.read(id);
        });
        cacheEngine.put(new CacheElement<>(out.getId(), out));
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
        cacheEngine.dispose();
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
