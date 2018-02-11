package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.dao.RegistrationDataDao;
import edu.netcracker.project.logistic.model.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
    private RegistrationDataDao registrationDataDao;

    @Autowired
    public RegistrationController(RegistrationDataDao registrationDataDao) {
        this.registrationDataDao = registrationDataDao;
    }

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("registrationData", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String doRegistration(@Valid @ModelAttribute("registrationData") RegistrationForm registrationData,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        return "redirect:/index";
    }


}
