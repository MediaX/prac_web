package ru.msu.cmc.prac_web;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.msu.cmc.prac_web.DAO.AirportDAO;
import ru.msu.cmc.prac_web.DAO.FlightDAO;
import ru.msu.cmc.prac_web.classes.Airport;
import ru.msu.cmc.prac_web.classes.Flight;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightDAOTest {
    @Autowired
    private FlightDAO flightDAO;
    @Autowired
    private AirportDAO airportDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void getFilter() throws Exception {
        List<Flight> t1 = flightDAO.findFlight(4L, null, null,
                null, null);
        assertEquals("PG0529", (t1.get(0).getFlight_no()));
        List<Airport> airport = airportDAO.findAirport(10, null, null, null);
        List<Airport> airport1 = airportDAO.findAirport(2, null, null, null);

        List<Flight> t3 = flightDAO.findFlight(null, "PG0621", airport.get(0),
                airport1.get(0), null);
        assertEquals("PG0621", (t3.get(0).getFlight_no()));

    }

    @Test
    public void testUpdate() {
        List<Flight> flights = flightDAO.findFlight(4L, null, null,
                null, null);
        String tmp = flights.get(0).getAirline();
        flights.get(0).setAirline("Абракадабра");
        flightDAO.update(flights.get(0));
        flights = flightDAO.findFlight(4L, null, null,
                null, null);
        assertEquals("Абракадабра", flights.get(0).getAirline());

        //return to valid data
        flights.get(0).setAirline(tmp);
        flightDAO.update(flights.get(0));
        assertNotEquals("Aбракадабра", flights.get(0).getAirline());
    }

    @Test
    public void testSaveAndDelete() {
        //create new client
        Airport airport = airportDAO.findAirport(10, null, null, null).get(0);
        Airport airport1 = airportDAO.findAirport(2, null, null, null).get(0);
        Timestamp time1 = Timestamp.valueOf("2021-08-18 16:25:00.000000");
        Timestamp time2 = Timestamp.valueOf("2021-08-18 20:25:00.000000");
        Flight flight = new Flight(30, "SU2703", airport, airport1, time1, time2, "{\"nfs\": 30, \"class\": \"economy\", \"price\": 100000}", "S7");
        flightDAO.save(flight);
        assertEquals("SU2703", flightDAO.findFlight(Long.valueOf(flight.getId()), null,
                null, null, null).get(0).getFlight_no());

        //delete new client
        Flight delFlight = flightDAO.findFlight(null, "SU2703",
                null, null, null).get(0);
        flightDAO.delete(delFlight);
        List<Flight> afterDel = flightDAO.findFlight(null, "SU2703",
                null, null, null);
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
        Flight flight = flightDAO.getById(1);
        assertEquals(null, (flight));
    }
}
