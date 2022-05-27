package ru.msu.cmc.prac_web;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.msu.cmc.prac_web.DAO.AirportDAO;
import ru.msu.cmc.prac_web.classes.Airport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirportDAOTest {
    @Autowired
    private AirportDAO airportDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void getFilter() throws Exception {
        List<Airport> t1 = airportDAO.findAirport(1, null,null,null);
        assertEquals("Якутск", (t1.get(0).getCity()));

        List<Airport> t2 = airportDAO.findAirport(null, "KEJ",null,null);
        assertEquals("Кемерово", (t2.get(0).getName()));

        List<Airport> t3 = airportDAO.findAirport(null, null,"Пулково","Санкт-Петербург");
        assertEquals("LED", (t3.get(0).getCode()));
    }

    @Test
    public void testUpdate() {
        List<Airport> airport = airportDAO.findAirport(1, null, null, null);
        String tmp = airport.get(0).getName();
        airport.get(0).setName("Абракадабра");
        airportDAO.update(airport.get(0));
        airport = airportDAO.findAirport(1, null, null, null);
        assertEquals("Абракадабра", airport.get(0).getName());

        //return to valid data
        airport.get(0).setName(tmp);
        airportDAO.update(airport.get(0));
        assertNotEquals("Aбракадабра", airport.get(0).getName());
    }

    @Test
    public void testSaveAndDelete() {
        //create new client
        Airport airport = new Airport(11, "DME", "Domodedovo", "Moscow");
        airportDAO.save(airport);
        assertEquals("DME", airportDAO.findAirport(
                                        null, null, "Domodedovo", null).get(0).getCode());

        //delete new client
        Airport delAirport = airportDAO.findAirport(null, null, "Domodedovo", null).get(0);
        airportDAO.delete(delAirport);
        List<Airport> afterDel = airportDAO.findAirport(null, null, "Domodedovo", null);
        assertEquals(0, afterDel.size());

        //delete non existing client
        airport = new Airport(11, "DME", "Domodedovo", "Moscow");
        airportDAO.delete(airport);
        afterDel = airportDAO.getAll();
        assertEquals(10, afterDel.size());

    }

    @Test
    public void getAllAirports() throws Exception {
        List<Airport> airport = airportDAO.getAll();
        System.out.print("jlksjd\n");
        assertEquals(10, (airport.size()));
    }

    @Test
    public void getById() throws Exception {
        Airport airport = airportDAO.getById(1);
        assertEquals(null, airport);
    }
}
