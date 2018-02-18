package edu.netcracker.project.logistic.controllers;


import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.service.PersonService;
import edu.netcracker.project.logistic.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@Controller
public class TestController {


   private PersonService personService;


  private   RoleService roleService;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public TestController(PersonService personService,RoleService roleService, BCryptPasswordEncoder passwordEncoder ) {
        this.personService=personService;
        this.roleService=roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/test")
    public String test(Model model) {
        Optional<Person> person = personService.findOne((long) 2);
        LocalDateTime localDate = LocalDateTime.now();
        personService.delete((long) 23);
        Person person1 = new Person((long)23,"nick_name", "1121212", localDate, "sdfffsfsdf");
        Person person2 = new Person((long)23,"nick_name", "1121212", localDate, "sdfffsfsdf");
        Contact contact = new Contact((long) 1 ,"lol", "lol", "+2312312313", (long) 1);
        Contact contact1 = new Contact((long) 1 ,"lol1", "lol1", "+23123123123", (long) 2);
        // contactService.saveContact(contact);
        // contactService.saveContact(contact1);
        System.out.println(contact1);
        personService.savePerson(person1);
        personService.savePerson(person2);
        System.out.println( personService.exists((long) 23));
        System.out.println(person1);
        System.out.println(person2);
        return "test";
    }



    @GetMapping("/error/403")
    public String error403() {
        return "/error/403";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/employee")
    public String admin() {
        return "employee";
    }

    @GetMapping("/admin/advertisements")
    public String adminAdvertisements() {
        return "/admin/admin_advertisements";
    }

    @GetMapping("/admin/crud/employee")
    public String adminCrudEmployee() {
        return "/admin/admin_crud_employee";
    }

    @GetMapping("/admin/crud/office")
    public String adminCrudOffice() {
        return "/admin/admin_crud_office";
    }

    @GetMapping("/admin/employees")
    public String adminEmployees() {
        return "/admin/admin_employees";
    }

    @GetMapping("/admin/offices")
    public String adminOffices() {
        return "/admin/admin_offices";
    }

}
