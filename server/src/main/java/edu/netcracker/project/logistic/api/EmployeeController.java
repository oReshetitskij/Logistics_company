package edu.netcracker.project.logistic.api;

import edu.netcracker.project.logistic.model.Person;
import edu.netcracker.project.logistic.model.SearchForm;
import edu.netcracker.project.logistic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/employees", produces = "application/json")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        List<Person> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Person>> search(@RequestBody SearchForm searchForm) {
        List<Person> employees = employeeService.search(searchForm);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
}
