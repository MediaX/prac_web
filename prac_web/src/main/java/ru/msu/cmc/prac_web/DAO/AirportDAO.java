package ru.msu.cmc.prac_web.DAO;

import ru.msu.cmc.prac_web.classes.Airport;

import java.util.List;

public interface AirportDAO {
    List<Airport> getAll();
    Airport getById(Integer id);
    //update, delete and save
    void update(Airport airport);
    void delete(Airport airport);
    void save(Airport airport);

    List<Airport> findAirport(Integer id, String code, String name, String city);
}