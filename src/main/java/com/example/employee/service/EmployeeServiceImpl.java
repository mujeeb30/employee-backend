package com.example.employee.service;

import com.example.employee.dao.EmployeeDao;
import com.example.employee.dao.EmployeeProcedureDao;
import com.example.employee.entity.DepartmentEmployeeCount;
import com.example.employee.entity.Employee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final EmployeeProcedureDao employeeProcedureDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao, EmployeeProcedureDao employeeProcedureDao) {
        this.employeeDao = employeeDao;
        this.employeeProcedureDao = employeeProcedureDao;
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeDao.findAll();
    }

    @Override
    public Employee getEmployeeById(Integer id) {

        return employeeDao.findById(id).orElse(null);
    }

    @Override
    public Employee createEmployee(Employee employee) {

        return employeeDao.save(employee);
    }

    @Override
    public Employee updateEmployee(
            Integer id,
            Employee employee) {

        Employee existingEmployee =
                employeeDao.findById(id).orElse(null);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setFirstName(
                employee.getFirstName());

        existingEmployee.setLastName(
                employee.getLastName());

        existingEmployee.setEmail(
                employee.getEmail());

        existingEmployee.setDepartment(
                employee.getDepartment());

        existingEmployee.setSalary(
                employee.getSalary());

        return employeeDao.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Integer id) {

        employeeDao.deleteById(id);
    }
    @Override
public boolean existsByEmail(String email) {
    return employeeDao.existsByEmail(email);
}
@Override
public boolean existsByEmailAndIdNot(String email, Integer id) {
    return employeeDao.existsByEmailAndIdNot(email, id);
}

@Override 
public List<Employee> getEmployeesByDepartment(Integer deptId) {
    return employeeProcedureDao.getEmployeesByDepartment(deptId);
}

    @Override
    public List<Employee> getEmployeesBySalary(BigDecimal minimumSalary) {
        return employeeProcedureDao.getEmployeesBySalary(minimumSalary);
    }

    @Override
    public List<Employee> getEmployeesBySalaryOrder(String direction) {
        return employeeProcedureDao.getEmployeesBySalaryOrder(direction);
    }

    @Override
    public Integer getEmployeeCount() {
        return employeeProcedureDao.getEmployeeCount();
    }

    @Override
    public Employee getHighestSalaryEmployee() {
        return employeeProcedureDao.getHighestSalaryEmployee();
    }

    @Override
    public List<DepartmentEmployeeCount> getDepartmentEmployeeCount() {
        return employeeProcedureDao.getDepartmentEmployeeCount();
    }
    @Override
public Employee patchEmployee(Integer id, Employee employee) {

    Employee existingEmployee =
            employeeDao.findById(id).orElse(null);

    if (existingEmployee == null) {
        return null;
    }

    if (employee.getFirstName() != null) {
        existingEmployee.setFirstName(employee.getFirstName());
    }

    if (employee.getLastName() != null) {
        existingEmployee.setLastName(employee.getLastName());
    }

    if (employee.getEmail() != null) {
        existingEmployee.setEmail(employee.getEmail());
    }

    if (employee.getDepartment() != null) {
        existingEmployee.setDepartment(employee.getDepartment());
    }

    if (employee.getSalary() != null) {
        existingEmployee.setSalary(employee.getSalary());
    }

    return employeeDao.save(existingEmployee);
}
}
