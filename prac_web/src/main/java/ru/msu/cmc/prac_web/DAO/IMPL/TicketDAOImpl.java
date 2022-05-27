package ru.msu.cmc.prac_web.DAO.IMPL;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.prac_web.DAO.TicketDAO;
import ru.msu.cmc.prac_web.classes.Flight;
import ru.msu.cmc.prac_web.classes.Person;
import ru.msu.cmc.prac_web.classes.Ticket;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketDAOImpl extends AbstractDAOImpl<Ticket> implements TicketDAO {
    public TicketDAOImpl() {
        super(Ticket.class);
    }

    @Override
    public List<Ticket> findTicket(Long ticket_id, Flight flight_id, Person person_id, String seat_class) {

        try(Session session = this.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Ticket> query = cb.createQuery(Ticket.class);
            Root<Ticket> root = query.from(Ticket.class);

            // create path for dynamic filter query
            List<Predicate> predicates = new ArrayList<>();
            if(ticket_id != null) {
                predicates.add(cb.equal(root.get("ticket_id"), ticket_id));
            }
            if(flight_id != null) {
                predicates.add(cb.equal(root.get("flight_id"), flight_id));
            }
            if(person_id != null) {
                predicates.add(cb.equal(root.get("person_id"), person_id));
            }

            if(seat_class != null) {
                predicates.add(cb.equal(root.get("seat_class"), seat_class));
            }
            if(seat_class != null && seat_class != "") {
                predicates.add(cb.equal(root.get("seat_class"), seat_class));
            }

            //run query
            query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public Ticket getById(Integer id) {
        return null;
    }

}

