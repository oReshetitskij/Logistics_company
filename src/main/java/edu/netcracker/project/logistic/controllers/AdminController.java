package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.model.*;
import edu.netcracker.project.logistic.service.*;

import edu.netcracker.project.logistic.model.Advertisement;
import edu.netcracker.project.logistic.model.AdvertisementType;
import edu.netcracker.project.logistic.model.Office;

import edu.netcracker.project.logistic.service.AdvertisementService;

import edu.netcracker.project.logistic.validation.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private EmployeeService employeeService;
    private OfficeService officeService;
    private RoleService roleService;
    private AdvertisementService advertisementService;
    private AddressService addressService;
    private EmployeeValidator employeeValidator;


    @Autowired
    public AdminController(OfficeService officeService, EmployeeService employeeService,
                           RoleService roleService, AdvertisementService advertisementService,
                           AddressService addressService,
                           EmployeeValidator employeeValidator) {
        this.officeService = officeService;
        this.employeeService = employeeService;
        this.roleService = roleService;
        this.advertisementService = advertisementService;
        this.addressService = addressService;
        this.employeeValidator = employeeValidator;
    }

    @GetMapping("/advertisements")
    public String adminAdvertisements(Model model) {
        AdvertisementForm advertisementForm = new AdvertisementForm();
        model.addAttribute("advertisement", advertisementForm);
        return "/admin/admin_advertisements";
    }

    @PostMapping("/advertisements")
    public String publishAdvertisement(@ModelAttribute(value = "advertisement") AdvertisementForm advertisementForm) {

        Advertisement advertisement = new Advertisement();
        advertisement.setId(advertisementForm.getId());
        advertisement.setCaption(advertisementForm.getCaption());
        advertisement.setDescription(advertisementForm.getDescription());
        AdvertisementType advertisementType = new AdvertisementType();
        advertisementType.setName(advertisementForm.getType());
        advertisement.setType(advertisementType);

        advertisementService.save(advertisement);
        return "redirect:/admin/advertisements?success";
    }

    @GetMapping("/offices")
    public String getAllOffice(Model model) {
        model.addAttribute("offices", officeService.allOffices());
        return "/admin/admin_offices";
    }

    @GetMapping("/employees")
    public String getAllEmployees(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {
        List<Person> employees = employeeService.search(searchForm);
        model.addAttribute("employees", employees);
        model.addAttribute("availableRoles", roleService.findEmployeeRoles());
        return "/admin/admin_employees";
    }

    @GetMapping("/crud/employee/{id}")
    public String employeeProfile(@PathVariable long id, Model model) {
        Optional<Person> opt = employeeService.findOne(id);
        if (!opt.isPresent()) {
            return "redirect:/error/404";
        }
        Person emp = opt.get();
        List<Role> employeeRoles = roleService.findEmployeeRoles();

        model.addAttribute("newEmployee", false);
        model.addAttribute("employee", emp);
        model.addAttribute("availableRoles", employeeRoles);


        return "/admin/admin_crud_employee";
    }

    @PostMapping("/crud/employee/{id}")
    public String updateEmployee(@PathVariable long id, Model model,
                                 @ModelAttribute("employee") Person employee,
                                 BindingResult result) {
        employee.setId(id);
        employeeValidator.validateUpdateData(employee, result);
        if (result.hasErrors()) {
            List<Role> employeeRoles = roleService.findEmployeeRoles();
            model.addAttribute("newEmployee", false);
            model.addAttribute("availableRoles", employeeRoles);
            return "/admin/admin_crud_employee";
        }
        employee.setId(id);
        employeeService.update(employee);
        return "redirect:/admin/employees";
    }

    @PostMapping("/crud/employee/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return "/admin/admin_employees";
    }

    @GetMapping("/crud/employee")
    public String createEmployee(Model model) {
        Person employee = new Person();
        employee.setContact(new Contact());
        employee.setRoles(new HashSet<>());

        List<Role> availableRoles = roleService.findEmployeeRoles();

        model.addAttribute("employee", employee);
        model.addAttribute("newEmployee", true);
        model.addAttribute("availableRoles", availableRoles);

        return "/admin/admin_crud_employee";
    }

    @PostMapping("/crud/employee")
    public String doCreateEmployee(Model model,
                                   @ModelAttribute("employee") Person employee,
                                   BindingResult bindingResult) {
        employeeValidator.validateCreateData(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            List<Role> availableRoles = roleService.findEmployeeRoles();
            model.addAttribute("newEmployee", true);
            model.addAttribute("availableRoles", availableRoles);

            return "/admin/admin_crud_employee";
        }
        employeeService.create(employee);
        return "redirect:/admin/employees";
    }

    @GetMapping("/crud/office")
    public String createOffice(Model model) {
        model.addAttribute("office", new Office());
        return "/admin/admin_crud_office";
    }


    @GetMapping("/office/update/{id}")
    public String updateOffice(@PathVariable long id, Model model) {
        model.addAttribute("office", officeService.findOne(id));
        return "/admin/admin_crud_office";
    }

    @GetMapping("/office/delete/{id}")
    public String deleteOffice(@PathVariable Long id) {

        officeService.delete(id);
        return "redirect:/admin/offices";
    }

    @PostMapping("/crud/office")
    public String saveOffice(@ModelAttribute("office") OfficeForm officeForm) {

        Office office = new Office(
                officeForm.getOfficeId(),
                officeForm.getName(),
                addressService.findOne(officeForm.getAddress()).get()
        );
        System.out.println(office);
        officeService.save(office);
        return "redirect:/admin/offices";
    }


    @PostMapping("/FindOfficeByDepartment")
    public String findByDepartment(@RequestParam String department, Model model) {
        model.addAttribute("offices", officeService.findByDepartment(department));
        return "/admin/admin_offices";

    }
}
