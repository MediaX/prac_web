package ru.msu.cmc.prac_web.DAO;

import ru.msu.cmc.prac_web.classes.Person;

import java.util.List;

public interface PersonDAO {

    Person getById(Integer id);
    List<Person> getAll();

    //update, delete and save
    void update(Person person);
    void delete(Person person);
    void save(Person person);

    List<Person> findPerson(Long person_id, String name, String last_name, String mid_name, String email, String phone);
}