package ru.otus.pyltsin.HW15.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.pyltsin.HW15.common.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Нагло украдено из лекции
 */
@SuppressWarnings("ALL")
public class DAOImpl {

    private final Session session;

    public DAOImpl(Session session) {
        this.session = session;
    }

    public UserDataSet save(UserDataSet dataSet) {
        session.save(dataSet);
        return dataSet;
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }


    public UserDataSet readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> from = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(from.get("nameUser"), name));
        Query<UserDataSet> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public List<UserDataSet> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list();
    }

}
