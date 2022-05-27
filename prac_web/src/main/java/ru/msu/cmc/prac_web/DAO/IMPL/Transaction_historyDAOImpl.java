package ru.msu.cmc.prac_web.DAO.IMPL;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.prac_web.DAO.Transaction_historyDAO;
import ru.msu.cmc.prac_web.classes.Flight;
import ru.msu.cmc.prac_web.classes.Person;
import ru.msu.cmc.prac_web.classes.Ticket;
import ru.msu.cmc.prac_web.classes.Transaction_history;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Transaction_historyDAOImpl extends AbstractDAOImpl<Transaction_history> implements Transaction_historyDAO {
    public Transaction_historyDAOImpl() {
        super(Transaction_history.class);
    }

    @Override
    public List<Transaction_history> findTrans(Long transaction_id, Person person_id, Flight flight_id, Ticket ticket_id,
                                               Timestamp trans_dt) {

        try(Session session = this.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Transaction_history> query = cb.createQuery(Transaction_history.class);
            Root<Transaction_history> root = query.from(Transaction_history.class);

            // create path for dynamic filter query
            List<Predicate> predicates = new ArrayList<>();
            if(transaction_id != null) {
                predicates.add(cb.equal(root.get("transaction_id"), transaction_id));
            }
            if(person_id != null) {
                predicates.add(cb.equal(root.get("person_id"), person_id));
            }

            if(ticket_id != null) {
                predicates.add(cb.equal(root.get("ticket_id"), ticket_id));
            }
            if(trans_dt != null) {
                predicates.add(cb.equal(root.get("transaction_day_time"), trans_dt));
            }

            //run query
            query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public Transaction_history getById(Integer id) {
        return null;
    }
}

