package edu.netcracker.project.logistic.controllers;

import edu.netcracker.project.logistic.dao.impl.AddressDaoImpl;
import edu.netcracker.project.logistic.model.*;
import edu.netcracker.project.logistic.service.*;
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
    @Autowired
    private AddressDaoImpl addressDao;

    @Autowired
    public AdminController(EmployeeService employeeService, OfficeService officeService,
                           ContactService contactService, RoleService roleService,
                           AdvertisementService advertisementService, AddressService addressService) {
        this.employeeService = employeeService;
        this.officeService = officeService;
        this.contactService = contactService;
        this.roleService = roleService;
        this.advertisementService = advertisementService;
        this.addressService = addressService;
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
                addressDao.findOne1(officeForm.getAddress())
        );
        System.out.println(office);
        officeService.save(office);
        return "redirect:/admin/offices";
    }

    @PostMapping("/FindOfficeByDepartment")
    public String  findByDepartment(@RequestParam String department, Model model)
    {

       model.addAttribute("offices", officeService.findByDepartment(department));
        return "/admin/admin_offices";

    }

}
