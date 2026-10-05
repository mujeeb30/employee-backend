package com.example.employee.dao;

import com.example.employee.entity.DepartmentEmployeeCount;
import com.example.employee.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public class EmployeeProcedureDao {

    private final EntityManager entityManager;

    public EmployeeProcedureDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Employee> getEmployeesByDepartment(Integer departmentId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getEmployeesByDepartment", Employee.class);
        query.registerStoredProcedureParameter("deptId", Integer.class, ParameterMode.IN);
        query.setParameter("deptId", departmentId);
        return query.getResultList();
    }

    public List<Employee> getEmployeesBySalary(BigDecimal minimumSalary) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getEmployeesBySalary", Employee.class);
        query.registerStoredProcedureParameter("minSalary", BigDecimal.class, ParameterMode.IN);
        query.setParameter("minSalary", minimumSalary);
        return query.getResultList();
    }

    public List<Employee> getEmployeesBySalaryOrder(String direction) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(
                "getEmployeesBySalaryOrder", Employee.class);
        query.registerStoredProcedureParameter("sortDirection", String.class, ParameterMode.IN);
        query.setParameter("sortDirection", direction);
        return query.getResultList();
    }

    public Integer getEmployeeCount() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getEmployeeCount");
        query.registerStoredProcedureParameter("total", Integer.class, ParameterMode.OUT);
        query.execute();
        return (Integer) query.getOutputParameterValue("total");
    }

    public Employee getHighestSalaryEmployee() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getHighestSalaryEmployee", Employee.class);
        List<Employee> employees = query.getResultList();
        return employees.isEmpty() ? null : employees.get(0);
    }

    public List<DepartmentEmployeeCount> getDepartmentEmployeeCount() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getDepartmentEmployeeCount");
        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();
        return rows.stream()
                .map(row -> new DepartmentEmployeeCount(
                        ((Number) row[0]).intValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue()))
                .toList();
    }
}
