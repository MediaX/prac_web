package ru.msu.cmc.prac_web.DAO.IMPL;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.msu.cmc.prac_web.DAO.AbstractDAO;
import ru.msu.cmc.prac_web.classes.CommonEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Repository
@Service
public abstract class AbstractDAOImpl<T extends CommonEntity> implements AbstractDAO<T> {

    //init logger
//    private static final Logger logger = (Logger) LoggerFactory.getLogger(AbstractDAOImpl.class);

    //init session factory
    private SessionFactory sessionFactory;

    //constructor
    private Class<T> entityClass;
    public AbstractDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //set session factory
    @Autowired
    public void setSessionFactory(LocalSessionFactoryBean sessionFactory) {
        this.sessionFactory = sessionFactory.getObject();
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public T getById(T id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(entityClass, (Serializable) id);
        }
    }

    @Override
    public List<T> getAll() {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = (CriteriaBuilder) session.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(entityClass);
            Root<T> root = query.from(entityClass);
            query.select(root);

            List<T> entityList = session.createQuery(query).getResultList();
//            logger.info("Get all records " + entityClass);

            return entityList;
        }
    }

    @Override
    public void update(T entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(T entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }
    
    @Override
    public void save(T entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }
}