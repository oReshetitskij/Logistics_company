package edu.netcracker.project.logistic.controllers;

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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
    public String employeeProfile(@PathVariable long id, Model model) {
        model.addAttribute("newEmployee", false);

        Optional<Person> opt = employeeService.findOne(id);
        if (!opt.isPresent()) {
            return "redirect:/error/404";
        }
        Person emp = opt.get();

        List<Role> roles =
                roleService.findRolesByPersonId(emp.getId())
                    .stream()
                    .filter(Role::isEmployeeRole)
                    .collect(Collectors.toList());

        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setEmployee(emp);
        employeeForm.setRoles(roles);

        model.addAttribute("form", employeeForm);

        return "/admin/admin_crud_employee";
    }

    @PostMapping("/crud/employee/{id}")
    public String updateEmployee(@PathVariable int id, Model model, BindingResult result,
                                    @ModelAttribute("form") EmployeeForm form) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/crud/employee/{id}/delete")
    public String deleteEmployee(@PathVariable int id) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/crud/employee")
    public String createEmployee(Model model) {
        Person emp = new Person();
        emp.setContact(new Contact());
        CreateEmployeeForm form = new CreateEmployeeForm();
        form.setEmployee(emp);

        List<Role> employeeRoles = roleService.findEmployeeRoles();

        model.addAttribute("form", form);
        model.addAttribute("newEmployee", true);
        model.addAttribute("roles", employeeRoles);

        return "/admin/admin_crud_employee";
    }

    @PostMapping("/crud/employee")
    public String doCreateEmployee(Model model,
                                   @ModelAttribute("form") CreateEmployeeForm form,
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
