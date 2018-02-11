package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.model.RegistrationData;
import edu.netcracker.project.logistic.model.RegistrationForm;
import edu.netcracker.project.logistic.service.impl.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String doRegistration(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        RegistrationData regData = registrationService.register(registrationForm);

        return "redirect:/index";
    }
}
