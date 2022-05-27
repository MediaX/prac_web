package ru.msu.cmc.prac_web;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.msu.cmc.prac_web.DAO.PersonDAO;
import ru.msu.cmc.prac_web.classes.Person;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonDAOTest {
    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void getFilter() throws Exception {
        List<Person> t1 = personDAO.findPerson(1L, null,
                                            null,null,
                                                null, null);
        assertEquals("TIKHONOV", (t1.get(0).getLast_name()));

        List<Person> t2 = personDAO.findPerson(null, "VALERIY",
                                            "TIKHONOV",null,
                                                null, null);
        assertEquals("vt@bk.ru", (t1.get(0).getEmail()));

    }

    @Test
    public void testUpdate() {
        List<Person> person = personDAO.findPerson(null, null, null, null,
                                                    "tk@bk.ru", null);
        String tmp = person.get(0).getName();
        person.get(0).setName("Абракадабра");
        personDAO.update(person.get(0));
        person = personDAO.findPerson(null, null, null, null,
                                "tk@bk.ru", null);
        assertEquals("Абракадабра", person.get(0).getName());

        //return to valid data
        person.get(0).setName(tmp);
        personDAO.update(person.get(0));
        assertNotEquals("Aбракадабра", person.get(0).getName());
    }

    @Test
    public void testSaveAndDelete() {
        //create new client
        Person person = new Person(10L, "DIMA", "PETROV", "dpt@bk.ru", "{\"country\":\"Russia\",\"city\":\"Moscow\",\"street\":\"arbat\",\"building\":\"10\",\"flat\":\"1\"}", "+73214672");
        personDAO.save(person);
        assertEquals("dpt@bk.ru", personDAO.findPerson(null, "DIMA",
                "PETROV", null, null, null).get(0).getEmail());

        //delete new client
        Person delPerson = personDAO.findPerson(null, "DIMA",
                                                "PETROV", null, null, null).get(0);
        personDAO.delete(delPerson);
        List<Person> afterDel = personDAO.findPerson(null, "DIMA",
                "PETROV", null, null, null);
        assertEquals(0, afterDel.size());

        //delete non existing client
//        person = new Person(10L, "DIMA", "PETROV", "dpt@bk.ru", "{\"country\":\"Russia\",\"city\":\"Moscow\",\"street\":\"arbat\",\"building\":\"10\",\"flat\":\"1\"}", "+73214672");
//        personDAO.delete(person);
//        afterDel = personDAO.getAll();
//        assertEquals(8, afterDel.size());

    }
//
    @Test
    public void getAllPersons() throws Exception {
        List<Person> person = personDAO.getAll();
        assertEquals(8, (person.size()));
    }

    @Test
    public void getById() throws Exception {
        Person person = personDAO.getById(1);
        assertEquals(null, (person));
    }
}
