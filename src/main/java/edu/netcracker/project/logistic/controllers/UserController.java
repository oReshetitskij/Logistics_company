package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private PersonService personService;

    @Autowired
    public UserController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping("/personal")
    public String viewPersonalArea(Model model, Principal principal){

        String username = principal.getName();

        Optional<Person> person = personService.findOne(username);

        if (!person.isPresent()){
            return "/error/403";
        }

        model.addAttribute("user", person.get());

        return "/user/user_personal_area";
    }

    @GetMapping(value = "/personal/{id}")
    public String changePersonalArea(){
        return "/error/404";
    }

    @PostMapping(value = "/personal/{id}", params = "action=save")
    public String updatePersonalArea(@PathVariable Long id){


        return "/user/user_personal_area";
    }

    @PostMapping(value = "/personal/{id}", params = "action=delete")
    public String deletePersonalArea(@PathVariable Long id){

        personService.delete(id);
        return "redirect:/login?delete";

    }
}
