package ru.msu.cmc.prac_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.prac_web.DAO.*;
import ru.msu.cmc.prac_web.DAO.IMPL.*;
import ru.msu.cmc.prac_web.classes.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @Autowired
    private final FlightDAO flightDAO = new FlightDAOImpl();

    @Autowired
    private final TicketDAO ticketDAO = new TicketDAOImpl();

    @Autowired
    private final AirportDAO airportDAO = new AirportDAOImpl();

    @Autowired
    private final Transaction_historyDAO transaction_historyDAO = new Transaction_historyDAOImpl();

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("flight", flightDAO.getAll());
        model.addAttribute("filter", new Flight());
        System.out.printf("hello world\n");
        return "flight";
    }

    @GetMapping("/person_filtered")
    public String showFiltered(Model model) {
        model.addAttribute("filter", new Person());
        System.out.printf("hello get filter");
        return "person/person_filtered";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Flight flight = flightDAO.findFlight(Long.valueOf(id),null, null, null, null).get(0);

        model.addAttribute("flight", flight);
        System.out.print("hello show");
        return "flight_show";
    }

    @GetMapping("/flight_new")
    public String addNewFlight(Model model) {

        model.addAttribute("flight", new Flight());
        System.out.print("hello addNewFlight");
        return "flight_new";
    }

    @PostMapping("/flight_new")
    public String addNewFlightPost(@ModelAttribute("flight") Flight flight, Model model) {

        System.out.print(flight.getArr_airport().getCode());
        Airport ap1 = airportDAO.findAirport(null, flight.getDep_airport().getCode(), null, null).get(0);
        Airport ap2 = airportDAO.findAirport(null, flight.getArr_airport().getCode(), null, null).get(0);
        Flight flight2 = new Flight(1, flight.getFlight_no(), ap1, ap2, flight.getDep_time(), flight.getArr_time(), "{\"nfs\": 30, \"class\": \"economy\", \"price\": 100000}", "aeroflot");
        flightDAO.save(flight2);
        System.out.print("hello addNewFlight");
        return "/flight_new";
    }

    @PostMapping("/flight_filtered")
    public String filter(@ModelAttribute Flight flight, Model model) {
        Airport airport, airport1;
        if (flight.getDep_airport().getCode() == ""){
            airport = null;
        } else
            airport = airportDAO.findAirport(null, flight.getDep_airport().getCode(), null, null).get(0);
        if (flight.getArr_airport().getCode() == ""){
            airport1 = null;
        } else
            airport1 = airportDAO.findAirport(null, flight.getArr_airport().getCode(), null, null).get(0);
        model.addAttribute("filteredFlights", flightDAO.findFlight(null, flight.getFlight_no(),
                                                                                airport,
                                                                                airport1,
                                                                                null));
        return "flight_filtered";
    }

    @PostMapping("/{id}/buy_ticket")
    public String buyTicket(@PathVariable("id") Integer id, @ModelAttribute Flight flight, Model model) {
        Airport airport = null, airport1 = null;
        Flight flight1 = flightDAO.findFlight(Long.valueOf(id), null, null, null, null).get(0);
        model.addAttribute("flight", flight1);
        model.addAttribute("person", new Person());
        return "buy_ticket";
    }

    @PatchMapping("/{id}/buy_ticket")
    public String addClient(@PathVariable("id") Integer id,
                               @ModelAttribute("person") Person person, Model model) {

        Flight flight = flightDAO.findFlight(Long.valueOf(id), null, null, null, null).get(0);
        List<Person> person1 = personDAO.findPerson(null, person.getName(), person.getLast_name(), null, person.getEmail(), person.getPhone_number());
        if (person1.size() == 1){
            Ticket ticket = new Ticket(1L, flight, person1.get(0), 10000L, "economy");
            ticketDAO.save(ticket);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Transaction_history trans = new Transaction_history(1L, person1.get(0), flight, ticket, timestamp, ticket.getPrice(), 0L);
            transaction_historyDAO.save((trans));
        }



        System.out.print("ID" + person.getAddress() + "hello patch");

//        model.addAttribute("uniqueError", 0);
        return "redirect:/trans";
    }

    @PatchMapping("/{id}")
    public String updateClient(@PathVariable("id") Integer id,
                               @ModelAttribute("client") Person person, Model model) {


        Person person1 = personDAO.findPerson(Long.valueOf(id), null, null, null, null, null).get(0);
        person1.setEmail(person.getEmail());
        person1.setName(person.getName());
        person1.setLast_name(person.getLast_name());
        person1.setMiddle_name(person.getMiddle_name());
        person1.setPhone_number(person.getPhone_number());
        System.out.print("ID" + person.getAddress() + "hello patch");
        personDAO.update(person1);

        model.addAttribute("uniqueError", 0);
        return "redirect:/person";
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable("id") Integer id) {
        System.out.print("hello delete");
        Flight flight = flightDAO.findFlight(Long.valueOf(id), null, null, null, null).get(0);
        flightDAO.delete(flight);

        return "redirect:/flight";
    }
}