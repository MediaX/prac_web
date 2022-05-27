package ru.msu.cmc.prac_web.DAO.IMPL;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.prac_web.DAO.FlightDAO;
import ru.msu.cmc.prac_web.classes.Airport;
import ru.msu.cmc.prac_web.classes.Flight;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightDAOImpl extends AbstractDAOImpl<Flight> implements FlightDAO {
    public FlightDAOImpl() {
        super(Flight.class);
    }

    @Override
    public List<Flight> findFlight(Long flight_id, String number, Airport dep_airport,
                                            Airport arr_airport, Timestamp dep_date) {

        try(Session session = this.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Flight> query = cb.createQuery(Flight.class);
            Root<Flight> root = query.from(Flight.class);

            // create path for dynamic filter query
            List<Predicate> predicates = new ArrayList<>();
            if(flight_id != null) {
                predicates.add(cb.equal(root.get("flight_id"), flight_id));
            }
            if(number != null && number != "") {
                predicates.add(cb.equal(root.get("flight_no"), number));
            }
            if(dep_airport != null) {
                predicates.add(cb.equal(root.get("dep_airport"), dep_airport));
            }
            if(arr_airport != null) {
                predicates.add(cb.equal(root.get("arr_airport"), arr_airport));
            }
            if(dep_date != null) {
                predicates.add(cb.equal(root.get("dep_time"), dep_date));
            }

            //run query
            query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public Flight getById(Integer id) {
        return null;
    }
}

