package ru.msu.cmc.prac_web;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.msu.cmc.prac_web.DAO.FlightDAO;
import ru.msu.cmc.prac_web.DAO.PersonDAO;
import ru.msu.cmc.prac_web.DAO.TicketDAO;
import ru.msu.cmc.prac_web.classes.Flight;
import ru.msu.cmc.prac_web.classes.Person;
import ru.msu.cmc.prac_web.classes.Ticket;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketDAOTest {
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private FlightDAO flightDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void getFilter() throws Exception {
        List<Ticket> t1 = ticketDAO.findTicket(5L, (Flight) null, null,
                                                null);
        assertEquals("economy", (t1.get(0).getSeat_class()));


        Person person = personDAO.findPerson(null, "ARTUR", "GEERASIMOV", null,
                                                    "ag@bk.ru", null).get(0);
        Flight flight = flightDAO.findFlight(5L, "PG0461", null,
                                                null, null).get(0);

        List<Ticket> t3 = ticketDAO.findTicket(null, flight, person,
                null);
        System.out.print(t3);
        assertEquals(4, (t3.get(0).getTicket_id()));

    }

    @Test
    public void testUpdate() {
        List<Ticket> tickets = ticketDAO.findTicket(5L, (Flight) null, null,
                null);
        String tmp = tickets.get(0).getSeat_class();
        tickets.get(0).setSeat_class("Абракадабра");
        ticketDAO.update(tickets.get(0));
        tickets = ticketDAO.findTicket(5L, (Flight) null, null,
                null);
        assertEquals("Абракадабра", tickets.get(0).getSeat_class());

        //return to valid data
        tickets.get(0).setSeat_class(tmp);
        ticketDAO.update(tickets.get(0));
        assertNotEquals("Aбракадабра", tickets.get(0).getSeat_class());
    }

    @Test
    public void testSaveAndDelete() {
        //create new client
//        Airport airport = air.findAirport(10, null, null, null).get(0);
//        Airport airport1 = airportDAO.findAirport(2, null, null, null).get(0);
        Person person = personDAO.findPerson(null, "ARTUR", "GEERASIMOV", null,
                "ag@bk.ru", null).get(0);
        Flight flight = flightDAO.findFlight(5L, "PG0461", null,
                null, null).get(0);

        Timestamp time1 = Timestamp.valueOf("2021-08-18 16:25:00.000000");
        Timestamp time2 = Timestamp.valueOf("2021-08-18 20:25:00.000000");
        Ticket ticket = new Ticket(1L, flight, person, 12000L, "business");
        ticketDAO.save(ticket);
        assertEquals(12000L, ticketDAO.findTicket(null, flight, person, "business").get(0).getPrice());

        //delete new client
        Ticket delTicket = ticketDAO.findTicket(null, flight, person, "business").get(0);
        ticketDAO.delete(delTicket);
        List<Ticket> afterDel = ticketDAO.findTicket(null, flight, person, "business");
        assertEquals(0, afterDel.size());

        //delete non existing client
//        person = new Person(10L, "DIMA", "PETROV", "dpt@bk.ru", "{\"country\":\"Russia\",\"city\":\"Moscow\",\"street\":\"arbat\",\"building\":\"10\",\"flat\":\"1\"}", "+73214672");
//        personDAO.delete(person);
//        afterDel = personDAO.getAll();
//        assertEquals(8, afterDel.size());

    }
    //    //
//    @Test
//    public void getAllPersons() throws Exception {
//        List<Person> person = personDAO.getAll();
//        assertEquals(8, (person.size()));
//    }
//
    @Test
    public void getById() throws Exception {
        Ticket ticket = ticketDAO.getById(1);
        assertEquals(null, (ticket));
    }
}
