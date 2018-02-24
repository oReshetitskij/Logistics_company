package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.exception.NonUniqueRecordException;

import edu.netcracker.project.logistic.model.*;
import edu.netcracker.project.logistic.service.*;

import edu.netcracker.project.logistic.model.Advertisement;
import edu.netcracker.project.logistic.model.AdvertisementType;
import edu.netcracker.project.logistic.model.Office;

import edu.netcracker.project.logistic.service.AdvertisementService;

import edu.netcracker.project.logistic.validation.CreateEmployeeValidator;
import edu.netcracker.project.logistic.validation.UpdateEmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private EmployeeService employeeService;
    private OfficeService officeService;
    private ContactService contactService;
    private RoleService roleService;
    private AdvertisementService advertisementService;
    private AddressService addressService;
    private UpdateEmployeeValidator updateEmployeeValidator;
    private CreateEmployeeValidator createEmployeeValidator;



    @Autowired
    public AdminController(OfficeService officeService, EmployeeService employeeService,
                           ContactService contactService, RoleService roleService,
                           AdvertisementService advertisementService, UpdateEmployeeValidator updateEmployeeValidator,
                           AddressService addressService,
                           CreateEmployeeValidator createEmployeeValidator) {
        this.officeService = officeService;
        this.employeeService = employeeService;
        this.contactService = contactService;
        this.roleService = roleService;
        this.advertisementService = advertisementService;
        this.addressService = addressService;
        this.updateEmployeeValidator = updateEmployeeValidator;
        this.createEmployeeValidator = createEmployeeValidator;
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

        List<Long> empRoles =
                roleService.findRolesByPersonId(emp.getId())
                        .stream()
                        .filter(Role::isEmployeeRole)
                        .map(Role::getRoleId)
                        .collect(Collectors.toList());

        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setEmployee(emp);
        employeeForm.setRoleIds(empRoles);

        List<Role> employeeRoles = roleService.findEmployeeRoles();
        model.addAttribute("roles", employeeRoles);
        model.addAttribute("form", employeeForm);

        return "/admin/admin_crud_employee";
    }

    @PostMapping("/crud/employee/{id}")
    public String updateEmployee(@PathVariable long id, Model model,
                                 @ModelAttribute("form") EmployeeForm form,
                                 BindingResult result) {
        updateEmployeeValidator.validate(form, result);
        if (result.hasErrors()) {
            List<Role> employeeRoles = roleService.findEmployeeRoles();
            model.addAttribute("newEmployee", false);
            model.addAttribute("roles", employeeRoles);

            return "/admin/admin_crud_employee";
        }
        form.getEmployee().setId(id);
        employeeService.update(id, form.getEmployee().getContact(), form.getRoleIds());
        return "redirect:/admin/employees";
    }

    @PostMapping("/crud/employee/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return "/admin/admin_employees";
    }

    @GetMapping("/crud/employee")
    public String createEmployee(Model model) {
        Person emp = new Person();
        emp.setContact(new Contact());
        EmployeeForm form = new EmployeeForm();
        form.setEmployee(emp);

        List<Role> employeeRoles = roleService.findEmployeeRoles();

        model.addAttribute("form", form);
        model.addAttribute("newEmployee", true);
        model.addAttribute("roles", employeeRoles);

        return "/admin/admin_crud_employee";
    }

    @PostMapping("/crud/employee")
    public String doCreateEmployee(Model model,
                                   @ModelAttribute("form") EmployeeForm form,
                                   BindingResult bindingResult) {
        createEmployeeValidator.validate(form, bindingResult);
        if (bindingResult.hasErrors()) {
            List<Role> employeeRoles = roleService.findEmployeeRoles();
            model.addAttribute("newEmployee", true);
            model.addAttribute("roles", employeeRoles);

            return "/admin/admin_crud_employee";
        }
        try {
            employeeService.create(form.getEmployee(), form.getRoleIds());
        } catch (NonUniqueRecordException ex) {
            for (String duplicate: ex.duplicateFields()) {
                bindingResult.rejectValue(duplicate, "Duplicate field");
            }
            List<Role> employeeRoles = roleService.findEmployeeRoles();
            model.addAttribute("newEmployee", true);
            model.addAttribute("roles", employeeRoles);

            return "/admin/admin_crud_employee";
        }
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
