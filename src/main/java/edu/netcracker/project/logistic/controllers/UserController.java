package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.service.PersonService;
import edu.netcracker.project.logistic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/personal")
    public String viewPersonalArea(Model model, Principal principal){

        String username = principal.getName();

        Optional<Person> person = userService.findOne(username);

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
    public String updatePersonalArea(@PathVariable Long id,
                                     @ModelAttribute("user") Person user,
                                     BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "/user/user_personal_area";
        }

        userService.update(user);
        return "redirect:/user/personal";
    }

    @PostMapping(value = "/personal/{id}", params = "action=delete")
    public String deletePersonalArea(@PathVariable Long id){

        userService.delete(id);
        return "redirect:/login?delete";

    }
}
