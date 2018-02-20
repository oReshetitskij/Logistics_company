package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.model.Advertisement;
import edu.netcracker.project.logistic.model.AdvertisementType;
import edu.netcracker.project.logistic.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private AdvertisementService advertisementService;

    @Autowired
    public AdminController(AdvertisementService advertisementService){
        this.advertisementService = advertisementService;
    }

    @GetMapping("/advertisements")
    public String adminAdvertisements(Model model) {
        Advertisement advertisement = new Advertisement();
        advertisement.setType(new AdvertisementType());
        model.addAttribute("advertisement", advertisement);
        return "/admin/admin_advertisements";
    }

    @PostMapping("/advertisements")
    public String publishAdvertisement(@ModelAttribute(value = "advertisement") Advertisement advertisement, Model model) {
        advertisementService.save(advertisement);
        model.addAttribute("success", new Object());
        return "redirect:/admin/advertisements?success";
    }

    @GetMapping("/crud/employee")
    public String adminCrudEmployee() {
        return "/admin/admin_crud_employee";
    }

    @GetMapping("/crud/office")
    public String adminCrudOffice() {
        return "/admin/admin_crud_office";
    }

    @GetMapping("/employees")
    public String adminEmployees() {
        return "/admin/admin_employees";
    }

    @GetMapping("/offices")
    public String adminOffices() {
        return "/admin/admin_offices";
    }

}
