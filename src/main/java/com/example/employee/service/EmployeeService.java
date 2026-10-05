
package com.example.employee.service;

import com.example.employee.entity.Employee;

import java.util.List;
import java.math.BigDecimal;
import com.example.employee.entity.DepartmentEmployeeCount;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Integer id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Integer id, Employee employee);

    Employee patchEmployee(Integer id, Employee employee);

    boolean existsByEmail(String email);

    void deleteEmployee(Integer id);

    boolean existsByEmailAndIdNot(String email, Integer id);

    // Stored Procedure
    List<Employee> getEmployeesByDepartment(Integer deptId);

    List<Employee> getEmployeesBySalary(BigDecimal minimumSalary);

    List<Employee> getEmployeesBySalaryOrder(String direction);

    Integer getEmployeeCount();

    Employee getHighestSalaryEmployee();

    List<DepartmentEmployeeCount> getDepartmentEmployeeCount();
}
