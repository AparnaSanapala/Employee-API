package com.example.employee.service;
import java.util.List;
import java.util.Optional;

import com.example.employee.dto.CreateEmployee;
import com.example.employee.dto.CreateEmployeePhone;
import com.example.employee.dto.UpdateEmployeeDetails;
import com.example.employee.dto.UpdateEmployeePhone;
import com.example.employee.entity.Employee;

public interface EmployeeService {
    // Create
    Employee createEmployee(CreateEmployee dto);
    Employee createEmployeeWithPhone(CreateEmployeePhone dto);

    // Read
    Optional<Employee> getEmployeeByEmail(String email);
    List<Employee> getEmployeesByName(String name);

    // Update
    Optional<Employee> updateEmployeeDetails(String email, UpdateEmployeeDetails dto);
    Optional<Employee> updateEmployeePhone(String email, UpdateEmployeePhone dto);

    // Delete
    boolean deleteEmployeeByEmail(String email);

    // JPA Specification
    List<Employee> searchByEmailSpec(String email);
    List<Employee> searchByNameSpec(String name);

    // HQL
    Optional<Employee> searchByEmailHQL(String email);
    List<Employee> searchByNameHQL(String name);

    // Native SQL
    Optional<Employee> searchByEmailNative(String email);
    List<Employee> searchByNameNative(String name);

}
