package ru.msu.cmc.prac_web.DAO;

import ru.msu.cmc.prac_web.classes.Flight;
import ru.msu.cmc.prac_web.classes.Airport;

import java.sql.Timestamp;
import java.util.List;

public interface FlightDAO {
    List<Flight> getAll();
    Flight getById(Integer id);
    //update, delete and save
    void update(Flight flight);
    void delete(Flight flight);
    void save(Flight flight);

    List<Flight> findFlight(Long flight_id, String number, Airport dep_airport, Airport arr_airport, Timestamp dep_date);
}