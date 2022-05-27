package ru.msu.cmc.prac_web.DAO.IMPL;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.prac_web.DAO.PersonDAO;
import ru.msu.cmc.prac_web.classes.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAOImpl extends AbstractDAOImpl<Person> implements PersonDAO {
    public PersonDAOImpl() {
        super(Person.class);
    }

    @Override
    public List<Person> findPerson(Long person_id, String name, String last_name,
                                   String mid_name, String email, String phone) {

        try(Session session = this.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Person> query = cb.createQuery(Person.class);
            Root<Person> root = query.from(Person.class);

            // create path for dynamic filter query
            List<Predicate> predicates = new ArrayList<>();
            if(person_id != null) {
                predicates.add(cb.equal(root.get("person_id"), person_id));
            }
            if(name != null && name != "") {
                predicates.add(cb.equal(root.get("name"), name));
            }
            if(last_name != null && last_name != "") {
                predicates.add(cb.equal(root.get("last_name"), last_name));
            }
            if(mid_name != null && mid_name != "") {
                predicates.add(cb.equal(root.get("middle_name"), mid_name));
            }
            if(email != null && email != "") {
                predicates.add(cb.equal(root.get("email"), email));
            }
            if(phone != null && phone != "") {
                predicates.add(cb.equal(root.get("phone_number"), phone));
            }

            //run query
            query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public Person getById(Integer id) {
        return null;
    }
}

