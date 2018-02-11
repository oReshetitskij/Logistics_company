package edu.netcracker.project.logistic.controllers;



import edu.netcracker.project.logistic.Dao.PersonCrudDao;
import edu.netcracker.project.logistic.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;


@Controller
public class TestController {

    @Autowired
    PersonCrudDao personCrudDao;


    @RequestMapping(value = "/test")
    public String test(Model model) {
        Person person = personCrudDao.find_one((long) 2);
        Date date = new Date(1233,12,12);
        personCrudDao.delete((long) 1);
        personCrudDao.save(new Person((long)1,"first_name", "last_name"," nick_name", "1121212", date, "sdfffsfsdf","1232123123"));

        System.out.println(person);

        return "test";
    }
}
