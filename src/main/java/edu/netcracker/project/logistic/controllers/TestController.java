package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.dao.ContactDao;
import edu.netcracker.project.logistic.dao.OfficeDao;
import edu.netcracker.project.logistic.model.Address;
import edu.netcracker.project.logistic.model.Contact;
import edu.netcracker.project.logistic.model.Office;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.service.AddressService;
import edu.netcracker.project.logistic.service.PersonService;
import edu.netcracker.project.logistic.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;


@Controller
public class TestController {

 @Autowired
 private OfficeDao officeDao;

   private PersonService personService;

   @Autowired
    AddressService addressService;

   @Autowired
    ContactDao contactDao;

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
      
        Address address = new Address("м. Київ");
        Address address1 = new Address((long)3,"м. Житомир");
        addressService.save(address);
        addressService.save(address1);


        LocalDateTime localDate = LocalDateTime.now();
        Contact contact = new Contact( 10L ,"lol", "lol", "+2312312313");
        contactDao.save(contact);
        Person person1 = new Person("nick_nam23e", "112121232", localDate, "sdfffsfs23df",contact);
        personService.savePerson(person1);
        personService.findOne("nick_nam23e");
         Office office = new Office( 1l, "werewr", address1);
        Office office1 = new Office( 1l, "werew1r", address);
        addressService.findOne(3L);
        System.out.println(office);
         officeDao.save(office);
         officeDao.save(office1);
        System.out.println(office1);
         officeDao.findOne(1L);
         officeDao.contains(1L);
        contactDao.save(contact);
        contactDao.findOne(1L);
        System.out.println(contact);

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

}
