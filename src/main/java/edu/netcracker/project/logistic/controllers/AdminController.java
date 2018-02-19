package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.dao.OfficeDao;
import edu.netcracker.project.logistic.dao.impl.OfficeDaoImpl;
import edu.netcracker.project.logistic.model.Office;
import edu.netcracker.project.logistic.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {


    private OfficeService officeService;

    @Autowired
    public AdminController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping("/offices")
    public String getAllOffice(Model model)
    {
        model.addAttribute("offices", officeService.allOffices());
        return "/admin/admin_offices";
    }

    @GetMapping("/crud/office")
    public String createOffice(Model model)
    {
       model.addAttribute("office", new Office());
        return "/admin/admin_crud_office";
    }

    @PostMapping("/crud/office")
    public String saveOffice(Office office) {
        officeService.save(office);
        return "redirect:/admin/offices";
    }



//
//    @GetMapping("/advertisements")
//    public String adminAdvertisements() {
//        return "/admin/admin_advertisements";
//    }
//
//    @GetMapping("/crud/employee")
//    public String adminCrudEmployee() {
//        return "/admin/admin_crud_employee";
//    }
//
//    @GetMapping("/crud/office")
//    public String adminCrudOffice() {
//        return "/admin/admin_crud_office";
//    }
//
//    @GetMapping("/employees")
//    public String adminEmployees() {
//        return "/admin/admin_employees";
//    }
//
//    @GetMapping("/offices")
//    public String adminOffices() {
//        return "/admin/admin_offices";
//    }

}
