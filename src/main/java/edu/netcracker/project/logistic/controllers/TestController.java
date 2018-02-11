package edu.netcracker.project.logistic.controllers;



import edu.netcracker.project.logistic.dao.PersonCrudDao;
import edu.netcracker.project.logistic.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Optional;


@Controller
public class TestController {

    @Autowired
    PersonCrudDao personCrudDao;


    @RequestMapping(value = "/test")
    public String test(Model model) {
        Optional<Person> person = personCrudDao.findOne((long) 2);
        LocalDate localDate = LocalDate.now();
        personCrudDao.delete((long) 23);
        Person person1 = new Person((long)23,"first_name", "last_name"," nick_name", "1121212", localDate, "sdfffsfsdf","1232123123");

        System.out.println( personCrudDao.contains((long) 5));
        System.out.println(person1);

        return "test";
    }
}
