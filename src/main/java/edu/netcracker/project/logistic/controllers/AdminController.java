package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.model.Office;
import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.netcracker.project.logistic.service.EmployeeService;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    EmployeeService employeeService;

    OfficeService officeService;

    @Autowired
    public AdminController(OfficeService officeService, EmployeeService employeeService) {
        this.officeService = officeService;
        this.employeeService = employeeService;
    }

    @GetMapping("/offices")
    public String getAllOffice(Model model) {
        model.addAttribute("offices", officeService.allOffices());
        return "/admin/admin_offices";
    }

    @GetMapping("/employees")
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());

        return "/admin/admin_employees";
    }


    @GetMapping("/crud/employee")
    public String adminCrudEmployee(Model model) {
        model.addAttribute("newEmployee", true);
        model.addAttribute("emp", new Person());
        return "/admin/admin_crud_employee";
    }

    @PostMapping("/crud/employee")
    public String creatEmployee(Model model, @ModelAttribute("emp") Person employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/admin_crud_employee";
        }

        return "/admin/admin_crud_employee";
    }

    @GetMapping("/crud/employee/{id}")
    public String updateEmployee(@PathVariable int id, Model model) {
        model.addAttribute("newEmployee", false);
        return "/admin/admin_crud_employee";
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
