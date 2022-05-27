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
import ru.msu.cmc.prac_web.DAO.Transaction_historyDAO;
import ru.msu.cmc.prac_web.classes.Flight;
import ru.msu.cmc.prac_web.classes.Person;
import ru.msu.cmc.prac_web.classes.Ticket;
import ru.msu.cmc.prac_web.classes.Transaction_history;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Transaction_historyDAOTest {
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private FlightDAO flightDAO;

    @Autowired
    private Transaction_historyDAO transaction_historyDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void getFilter() throws Exception {
        Ticket ticket = ticketDAO.findTicket(5L, (Flight) null, null,
                null).get(0);

        Person person = personDAO.findPerson(4L, null, null, null,
                null, null).get(0);
        Flight flight = flightDAO.findFlight(6L, null, null,
                null, null).get(0);
        System.out.print(ticket + "\n");
        System.out.print(person + "\n");
        System.out.print(flight + "\n");
        List<Transaction_history> t3 = transaction_historyDAO.findTrans(null, person, flight, ticket, null);
        assertEquals(5, (t3.get(0).getTransaction_id()));

    }

//    @Test
//    public void testUpdate() {
//        List<Ticket> tickets = ticketDAO.findTicket(5L, (Flight) null, null,
//                null);
//        String tmp = tickets.get(0).getSeat_class();
//        tickets.get(0).setSeat_class("Абракадабра");
//        ticketDAO.update(tickets.get(0));
//        tickets = ticketDAO.findTicket(5L, (Flight) null, null,
//                null);
//        assertEquals("Абракадабра", tickets.get(0).getSeat_class());
//
//        //return to valid data
//        tickets.get(0).setSeat_class(tmp);
//        ticketDAO.update(tickets.get(0));
//        assertNotEquals("Aбракадабра", tickets.get(0).getSeat_class());
//    }
//
    @Test
    public void testSaveAndDelete() {
        //create new client
//        Airport airport = air.findAirport(10, null, null, null).get(0);
//        Airport airport1 = airportDAO.findAirport(2, null, null, null).get(0);
        Ticket ticket = ticketDAO.findTicket(15L, (Flight) null, null,
                null).get(0);

        Person person = personDAO.findPerson(3L, null, null, null,
                null, null).get(0);
        Flight flight = flightDAO.findFlight(9L, null, null,
                null, null).get(0);

        Transaction_history trans = new Transaction_history(1L, person, flight, ticket, Timestamp.valueOf("2021-09-11 06:35:00.000000"), 12000L, 500L);
        transaction_historyDAO.save(trans);
        assertEquals(500L, transaction_historyDAO.findTrans(null, null, null, null, Timestamp.valueOf("2021-09-11 06:35:00.000000")).get(0).getDiscount());

        //delete new client
        Transaction_history delTrans = transaction_historyDAO.findTrans(null, null, null, null, Timestamp.valueOf("2021-09-11 06:35:00.000000")).get(0);
        transaction_historyDAO.delete(delTrans);
        List<Transaction_history> afterDel = transaction_historyDAO.findTrans(null, null, null, null, Timestamp.valueOf("2021-09-11 06:35:00.000000"));
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
        Transaction_history trans = transaction_historyDAO.getById(1);
        assertEquals(null, (trans));
    }
}
