package ru.msu.cmc.prac_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.msu.cmc.prac_web.DAO.IMPL.PersonDAOImpl;
import ru.msu.cmc.prac_web.DAO.IMPL.Transaction_historyDAOImpl;
import ru.msu.cmc.prac_web.DAO.PersonDAO;
import ru.msu.cmc.prac_web.DAO.Transaction_historyDAO;
import ru.msu.cmc.prac_web.classes.Transaction_history;

@Controller
@RequestMapping("/trans")
public class TransController {

    @Autowired
    private final PersonDAO personDAO = new PersonDAOImpl();

    @Autowired
    private final Transaction_historyDAO transDAO = new Transaction_historyDAOImpl();

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("trans", transDAO.getAll());
        model.addAttribute("filter", new Transaction_history());
        System.out.printf("hello world\n");
        return "trans";
    }

    @GetMapping("/trans_filtered")
    public String showFiltered(Model model) {
        model.addAttribute("filter", new Transaction_history());
        System.out.printf("hello get filter");
        return "trans/trans_filtered";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Transaction_history trans = transDAO.findTrans(
                Long.valueOf(id), null,null, null, null).get(0);


        model.addAttribute("trans", trans);
        System.out.print("hello show");
        return "trans_show";
    }

    @PostMapping("/trans_filtered")
    public String filter(@ModelAttribute Transaction_history trans, Model model) {
        System.out.printf("hello filter");
        model.addAttribute("filteredTrans", transDAO.findTrans(
              trans.getTransaction_id(), null,null, null, null));

        return "trans_filtered";
    }

}