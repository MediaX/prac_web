package ru.msu.cmc.prac_web.DAO;

import ru.msu.cmc.prac_web.classes.Flight;
import ru.msu.cmc.prac_web.classes.Person;
import ru.msu.cmc.prac_web.classes.Ticket;

import java.util.List;

public interface TicketDAO {
    List<Ticket> getAll();

    Ticket getById(Integer id);
    //update, delete and save
    void update(Ticket ticket);
    void delete(Ticket ticket);
    void save(Ticket ticket);

    List<Ticket> findTicket(Long ticket_id, Flight flight_id, Person person_id, String seat_class);
}