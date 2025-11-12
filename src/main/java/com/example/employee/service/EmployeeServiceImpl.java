package com.example.employee.service;

import com.example.employee.dto.*;
import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.specification.EmployeeSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    // 1. Create Employee (basic)
    public Employee createEmployee(CreateEmployee dto) {
        Employee e = new Employee();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setAddress(dto.getAddress());
        return repo.save(e);
    }

    // 2. Create Employee (with phone)
    public Employee createEmployeeWithPhone(CreateEmployeePhone dto) {
        Employee e = new Employee();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setPhone(dto.getPhone());
        return repo.save(e);
    }

    // 3. Fetch Employee by Email
    public Optional<Employee> getEmployeeByEmail(String email) {
        return repo.findByEmailIgnoreCase(email);
    }

    // 4. Fetch Employee by Name
    public List<Employee> getEmployeesByName(String name) {
        return repo.findByName(name);
    }

    // 5. Update Employee Details (lastName, phone, address)
    public Optional<Employee> updateEmployeeDetails(String email, UpdateEmployeeDetails dto) {
        Optional<Employee> empOpt = repo.findByEmailIgnoreCase(email);
        if (empOpt.isEmpty()) return Optional.empty();

        Employee emp = empOpt.get();
        if (dto.getLastName() != null) emp.setLastName(dto.getLastName());
        if (dto.getPhone() != null) emp.setPhone(dto.getPhone());
        if (dto.getAddress() != null) emp.setAddress(dto.getAddress());

        return Optional.of(repo.save(emp));
    }

    // 6. Update Employee Phone Only
    public Optional<Employee> updateEmployeePhone(String email, UpdateEmployeePhone dto) {
        Optional<Employee> empOpt = repo.findByEmailIgnoreCase(email);
        if (empOpt.isEmpty()) return Optional.empty();

        Employee emp = empOpt.get();
        emp.setPhone(dto.getPhone());
        return Optional.of(repo.save(emp));
    }

    // 7. Delete Employee by Email
    public boolean deleteEmployeeByEmail(String email) {
        Optional<Employee> empOpt = repo.findByEmailIgnoreCase(email);
        if (empOpt.isEmpty()) return false;

        repo.delete(empOpt.get());
        return true;
    }
    // JPA Specification
    @Override
    public List<Employee> searchByEmailSpec(String email) {
        return repo.findAll(EmployeeSpecification.hasEmail(email));
     }

    @Override
    public List<Employee> searchByNameSpec(String name) {
            return repo.findAll(EmployeeSpecification.hasName(name));
        }
    // HQL
    @Override
    public Optional<Employee> searchByEmailHQL(String email) {
            return repo.searchByEmailHQL(email);
    }

    @Override
    public List<Employee> searchByNameHQL(String name) {
            return repo.searchByNameHQL(name);
    }

    // Native SQL
    @Override
    public Optional<Employee> searchByEmailNative(String email) {
            return repo.searchByEmailNative(email);
     }
    @Override
    public List<Employee> searchByNameNative(String name) {
            return repo.searchByNameNative(name);
    }

}
