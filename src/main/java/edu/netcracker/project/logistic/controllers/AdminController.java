package edu.netcracker.project.logistic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @GetMapping("/advertisements")
    public String adminAdvertisements() {
        return "/admin/admin_advertisements";
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
