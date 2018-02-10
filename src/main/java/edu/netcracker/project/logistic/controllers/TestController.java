package edu.netcracker.project.logistic.controllers;



import edu.netcracker.project.logistic.Dao.PersonCrudDao;
import edu.netcracker.project.logistic.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @Autowired
    PersonCrudDao personCrudDao;

    @RequestMapping(value = "/test")
    public String test(Model model) {

        Person person = personCrudDao.find_one((long) 2);
        System.out.println(person);

        return "test";
    }
}
