package ru.msu.cmc.prac_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.prac_web.DAO.IMPL.PersonDAOImpl;
import ru.msu.cmc.prac_web.DAO.PersonDAO;
import ru.msu.cmc.prac_web.classes.Person;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("person", personDAO.getAll());
        model.addAttribute("filter", new Person());
        System.out.printf("hello world\n");
        return "person";
    }

    @GetMapping("/person_filtered")
    public String showFiltered(Model model) {
        model.addAttribute("filter", new Person());
        System.out.printf("hello get filter");
        return "person/person_filtered";
    }

    @GetMapping("/person_new")
    public String addNewPerson(Model model) {
        model.addAttribute("person", new Person());
        System.out.printf("hello get filter");
        return "person_new";
    }

    @PostMapping("/person_new")
    public String addNewFlightPost(@ModelAttribute("person") Person person, Model model) {
//        model.addAttribute("person", personDAO.getById(id));

        Person person1 = new Person(1L, person.getName(), person.getLast_name(), person.getEmail(), person.getAddress(), person.getPhone_number());
        personDAO.save(person1);
        System.out.print("hello addNewFlight");
        return "/person_new";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Person person = personDAO.findPerson(Long.valueOf(id), null, null,
                null, null, null).get(0);

        model.addAttribute("person", person);
        System.out.print("hello show");
        return "person_show";
    }

    @PostMapping("/person_filtered")
    public String filter(@ModelAttribute Person person, Model model) {
        System.out.printf("hello filter");
        model.addAttribute("filteredPersons", personDAO.findPerson(null, person.getName(), person.getLast_name(), null, person.getEmail(), person.getPhone_number()));

        return "person_filtered";
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
        Person person = personDAO.findPerson(Long.valueOf(id), null, null, null, null, null).get(0);
        personDAO.delete(person);

        return "redirect:/person";
    }
}