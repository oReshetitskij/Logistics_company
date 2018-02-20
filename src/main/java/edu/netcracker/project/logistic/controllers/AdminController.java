package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.model.Advertisement;
import edu.netcracker.project.logistic.model.AdvertisementType;
import edu.netcracker.project.logistic.model.Office;

import edu.netcracker.project.logistic.dao.OfficeDao;
import edu.netcracker.project.logistic.dao.impl.OfficeDaoImpl;

import edu.netcracker.project.logistic.service.OfficeService;
import edu.netcracker.project.logistic.service.AdvertisementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private AdvertisementService advertisementService;
  
    OfficeService officeService;

    @Autowired
    public AdminController(OfficeService officeService, AdvertisementService advertisementService) {
        this.officeService = officeService;
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
        return "redirect:/admin/advertisements?success";
    }

    @GetMapping("/crud/employee")
    public String adminCrudEmployee() {
        return "/admin/admin_crud_employee";
    }

    @GetMapping("/offices")
    public String getAllOffice(Model model){
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

}
