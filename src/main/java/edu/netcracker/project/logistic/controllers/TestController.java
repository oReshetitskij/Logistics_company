package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.Dao.UserCrudDao;
import edu.netcracker.project.logistic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @Autowired
    UserCrudDao userCrudDao;

    @RequestMapping(value = "/test")
    public String test(Model model) {

        User user = userCrudDao.find_one((long) 2);

        return "test";
    }
}
