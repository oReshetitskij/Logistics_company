package edu.netcracker.project.logistic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    @RequestMapping(method = RequestMethod.GET)
    public String registration(){
        return "registration";
    }

}
