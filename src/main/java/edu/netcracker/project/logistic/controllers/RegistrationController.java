package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.model.RegistrationForm;
import edu.netcracker.project.logistic.service.impl.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class RegistrationController {
    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String doRegistration(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            registrationService.register(registrationForm);
        } catch (MessagingException ex) {
            logger.error("Exception caught when sending confirmation mail", ex);
        }

        return "redirect:/registration/complete";
    }

    @GetMapping("/registration/complete")
    public String completeRegistration() {
        return "complete_registration";
    }

    @GetMapping("/registration/confirm")
    public String confirmEmail(@RequestParam("id") String idParam) {
        try {
            UUID id = UUID.fromString(idParam);
            registrationService.confirmAccount(id);
        } catch (IllegalArgumentException ex) {
            return "redirect:/error/404";
        }
        return "confirm_email";
    }
}
