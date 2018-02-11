package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    PersonCrudDao personCrudDao;

    @Autowired
    public TestController(PersonCrudDao personCrudDao) {
        this.personCrudDao = personCrudDao;
    }

    @RequestMapping(value = "/test")
    public String test(Model model) {
        Person person = personCrudDao.findOne((long) 2).get();
        personCrudDao.delete((long) 1);
        System.out.println(person);

        return "test";
    }
}
