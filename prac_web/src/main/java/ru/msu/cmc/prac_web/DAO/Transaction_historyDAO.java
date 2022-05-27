package ru.msu.cmc.prac_web.DAO;

import ru.msu.cmc.prac_web.classes.Flight;
import ru.msu.cmc.prac_web.classes.Person;
import ru.msu.cmc.prac_web.classes.Ticket;
import ru.msu.cmc.prac_web.classes.Transaction_history;

import java.sql.Timestamp;
import java.util.List;

public interface Transaction_historyDAO {
    List<Transaction_history> getAll();
    Transaction_history getById(Integer id);
    //update, delete and save
    void update(Transaction_history trh);
    void delete(Transaction_history trh);
    void save(Transaction_history trh);

    List<Transaction_history> findTrans(Long transaction_id, Person person_id, Flight flight_id, Ticket ticket_id,
                                        Timestamp trans_dt);
}