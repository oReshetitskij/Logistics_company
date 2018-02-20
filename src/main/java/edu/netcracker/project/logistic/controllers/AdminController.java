package edu.netcracker.project.logistic.controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.netcracker.project.logistic.model.*;
import edu.netcracker.project.logistic.service.ContactService;
import edu.netcracker.project.logistic.service.OfficeService;
import edu.netcracker.project.logistic.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.netcracker.project.logistic.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    EmployeeService employeeService;
    OfficeService officeService;
    ContactService contactService;
    RoleService roleService;

    @Autowired
    public AdminController(OfficeService officeService, EmployeeService employeeService,
                           ContactService contactService, RoleService roleService) {
        this.officeService = officeService;
        this.employeeService = employeeService;
        this.contactService = contactService;
        this.roleService = roleService;
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

    @GetMapping("/crud/employee/{id}")
    public String employeeProfile(@PathVariable int id, Model model) {
//        model.addAttribute("newEmployee", false);
//        return "/admin/admin_crud_employee";
        throw new UnsupportedOperationException();
    }

    @PostMapping("/crud/employee/{id}")
    public String updateEmployee(@PathVariable int id, Model model) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/crud/employee")
    public String createEmployee(Model model) {
        Person emp = new Person();
        emp.setContact(new Contact());
        NewEmployeeForm form = new NewEmployeeForm();
        form.setEmployee(emp);

        List<Role> employeeRoles = roleService.findEmployeeRoles();

        model.addAttribute("form", form);
        model.addAttribute("newEmployee", true);
        model.addAttribute("roles", employeeRoles);

        return "/admin/admin_crud_employee";
    }

    @PostMapping("/crud/employee")
    public String doCreateEmployee(Model model,
                                   @ModelAttribute("form") NewEmployeeForm form,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Role> employeeRoles = roleService.findEmployeeRoles();
            model.addAttribute("newEmployee", true);
            model.addAttribute("roles", employeeRoles);

            return "/admin/admin_crud_employee";
        }
        employeeService.save(form.getEmployee(), form.getRoleId());
        return "redirect:/admin/employees";
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
