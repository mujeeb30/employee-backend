package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.entity.DepartmentEmployeeCount;
import com.example.employee.service.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET ALL EMPLOYEES
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {

        List<Employee> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }

    // GET EMPLOYEE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable Integer id) {

        Employee employee = employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }

        return ResponseEntity.ok(employee);
    }

    // CREATE EMPLOYEE
    @PostMapping
    public ResponseEntity<?> createEmployee(
            @RequestBody Employee employee) {

        // First name validation
        if (employee.getFirstName() == null ||
                employee.getFirstName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter first name");
        }

        String nameRegex = "^[A-Za-z ]+$";

        if (!employee.getFirstName().matches(nameRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("First name should contain only letters");
        }

        // Last name validation
        if (employee.getLastName() == null ||
                employee.getLastName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter last name");
        }

        if (!employee.getLastName().matches(nameRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("Last name should contain only letters");
        }

        // Email validation
        if (employee.getEmail() == null ||
                employee.getEmail().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter email");
        }

        String emailRegex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!employee.getEmail().matches(emailRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter a proper email");
        }

        // Check duplicate email during CREATE
        if (employeeService.existsByEmail(employee.getEmail())) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Employee with this email already exists");
        }

        // Department validation
        if (employee.getDepartment() == null ||
                employee.getDepartment().getId() == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Please select a department");
        }

        // Salary validation
        if (employee.getSalary() == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter salary");
        }

        if (employee.getSalary() <= 0) {

            return ResponseEntity
                    .badRequest()
                    .body("Salary must be greater than 0");
        }

        Employee savedEmployee =
                employeeService.createEmployee(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedEmployee);
    }

    // UPDATE EMPLOYEE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Integer id,
            @RequestBody Employee employee) {

        // Check employee exists
        Employee existingEmployee =
                employeeService.getEmployeeById(id);

        if (existingEmployee == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }

        String nameRegex = "^[A-Za-z ]+$";

        // First name validation
        if (employee.getFirstName() == null ||
                employee.getFirstName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter first name");
        }

        if (!employee.getFirstName().matches(nameRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("First name should contain only letters");
        }

        // Last name validation
        if (employee.getLastName() == null ||
                employee.getLastName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter last name");
        }

        if (!employee.getLastName().matches(nameRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("Last name should contain only letters");
        }

        // Email validation
        if (employee.getEmail() == null ||
                employee.getEmail().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter email");
        }

        String emailRegex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!employee.getEmail().matches(emailRegex)) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter a proper email");
        }

        // Check duplicate email during UPDATE
        // Excludes the current employee ID
        if (employeeService.existsByEmailAndIdNot(
                employee.getEmail(), id)) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Employee with this email already exists");
        }

        // Department validation
        if (employee.getDepartment() == null ||
                employee.getDepartment().getId() == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Please select a department");
        }

        // Salary validation
        if (employee.getSalary() == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Please enter salary");
        }

        if (employee.getSalary() <= 0) {

            return ResponseEntity
                    .badRequest()
                    .body("Salary must be greater than 0");
        }

        Employee updatedEmployee =
                employeeService.updateEmployee(id, employee);

        return ResponseEntity.ok(updatedEmployee);
    }

    // DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable Integer id) {

        Employee employee =
                employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok(
                "Employee deleted successfully"
        );
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(
        @PathVariable Integer deptId) {

    List<Employee> employees =
            employeeService.getEmployeesByDepartment(deptId);

    return ResponseEntity.ok(employees);
}

    @GetMapping("/salary")
    public ResponseEntity<?> getEmployeesBySalary(
            @RequestParam(name = "minSalary", required = false) BigDecimal minSalary) {
        if (minSalary == null) {
            return ResponseEntity.badRequest()
                    .body("Missing minSalary. Example: /api/employees/salary?minSalary=80000");
        }
        if (minSalary.signum() < 0) {
            return ResponseEntity.badRequest().body("Minimum salary cannot be negative");
        }
        return ResponseEntity.ok(employeeService.getEmployeesBySalary(minSalary));
    }

    @GetMapping("/salary/order")
    public ResponseEntity<?> getEmployeesBySalaryOrder(
            @RequestParam(name = "direction", required = false) String direction) {
        if (direction == null || direction.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Missing direction. Use /api/employees/salary/order?direction=asc or direction=desc");
        }

        String normalizedDirection = direction.trim().toUpperCase(java.util.Locale.ROOT);
        if (!normalizedDirection.equals("ASC") && !normalizedDirection.equals("DESC")) {
            return ResponseEntity.badRequest().body("Direction must be 'asc' or 'desc'");
        }

        return ResponseEntity.ok(employeeService.getEmployeesBySalaryOrder(normalizedDirection));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getEmployeeCount() {
        return ResponseEntity.ok(employeeService.getEmployeeCount());
    }

    @GetMapping("/highest-salary")
    public ResponseEntity<Employee> getHighestSalaryEmployee() {
        Employee employee = employeeService.getHighestSalaryEmployee();
        return employee == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(employee);
    }

    @GetMapping("/department-count")
    public ResponseEntity<List<DepartmentEmployeeCount>> getDepartmentEmployeeCount() {
        return ResponseEntity.ok(employeeService.getDepartmentEmployeeCount());
    }
}
