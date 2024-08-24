package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFound;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.RasterFormatException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")


public class EmployeeController {
    @Autowired
private EmployeeRepository employeeRepository;
    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee( @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    //get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
Employee employee = employeeRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFound("Employee not found:"+id));
return ResponseEntity.ok().body(employee);
    }

    //update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Employee not found:"+id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
       Employee updatedEmployee = employeeRepository.save(employee);
       return ResponseEntity.ok(updatedEmployee);
    }

    //delete employee rest api
    @DeleteMapping("/employees/{id}")
     public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.findById(id).isPresent()) {
            this.employeeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
