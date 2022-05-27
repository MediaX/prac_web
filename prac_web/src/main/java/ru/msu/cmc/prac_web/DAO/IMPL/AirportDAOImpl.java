package ru.msu.cmc.prac_web.DAO.IMPL;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.msu.cmc.prac_web.DAO.AirportDAO;
import ru.msu.cmc.prac_web.classes.Airport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Service
public class AirportDAOImpl extends AbstractDAOImpl<Airport> implements AirportDAO {
    public AirportDAOImpl() {
        super(Airport.class);
    }

    @Override
    public List<Airport> findAirport(Integer id, String code, String name, String city) {
        try(Session session = this.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Airport> query = cb.createQuery(Airport.class);
            Root<Airport> root = query.from(Airport.class);

            // create path for dynamic filter query
            List<Predicate> predicates = new ArrayList<>();
            if(id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }
            if(code != null && code != "") {
                predicates.add(cb.equal(root.get("code"), code));
            }
            if(name != null && name != "") {
                //create LIKE expr
                String sqlName = "%" + name + "%";

                predicates.add(cb.like(root.get("name"), sqlName));
            }
            if(city != null && city != "") {
                predicates.add(cb.equal(root.get("city"), city));
            }

            //run query
            query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public Airport getById(Integer id) {
        return null;
    }
}

