package edu.netcracker.project.logistic.controllers;



import edu.netcracker.project.logistic.dao.AdvertisementDao;
import edu.netcracker.project.logistic.dao.ContactDao;
// import edu.netcracker.project.logistic.dao.OfficeDao;
import edu.netcracker.project.logistic.dao.OfficeDao;
import edu.netcracker.project.logistic.model.*;
// import edu.netcracker.project.logistic.model.Office;
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

 @Autowired
 private OfficeDao officeDao;

 @Autowired
    AdvertisementDao advertisementDao;

   private PersonService personService;

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

//        LocalDateTime localDate = LocalDateTime.now();
//
//        Person person1 = new Person(1L,"nick_name", "1121212", localDate, "sdfffsfsdf");
//        personService.savePerson(person1);
//        Contact contact = new Contact( 1L ,"lol", "lol", "+2312312313", 1L);
//         Office office = new Office(1L, "werewr", "werer");
//         officeDao.save(office);
//         officeDao.findOne(1L);
//         officeDao.contains(1L);
//         officeDao.delete(1L);
//        contactDao.save(contact);
//        contactDao.findOne(1L);
//        System.out.println(contact);

        Advertisement advertisement = new Advertisement();
        advertisement.setName("Caption");
        advertisement.setDescription("Description");
        advertisement.setType(new AdvertisementType());
        advertisement.getType().setName("Other");

        Advertisement advertisement2 = new Advertisement();
        advertisement2.setName("Caption");
        advertisement2.setDescription("Description");
        advertisement2.setType(new AdvertisementType());
        advertisement2.getType().setName("Advertisement");

        advertisementDao.save(advertisement);
        advertisementDao.save(advertisement2);


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
