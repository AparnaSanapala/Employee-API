package com.example.employee.controller;

import com.example.employee.dto.*;
import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 1. Create Employee (basic)
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployee dto) {
        Employee saved = employeeService.createEmployee(dto);
        return ResponseEntity.status(201).body(saved);
    }

    // 2. Create Employee (with phone)
    @PostMapping("/full")
    public ResponseEntity<Employee> createEmployeeWithPhone(@Valid @RequestBody CreateEmployeePhone dto) {
        Employee saved = employeeService.createEmployeeWithPhone(dto);
        return ResponseEntity.status(201).body(saved);
    }

    // 3. Fetch Employee by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        return employeeService.getEmployeeByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Fetch Employee by Name
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Employee>> getEmployeesByName(@PathVariable String name) {
        List<Employee> list = employeeService.getEmployeesByName(name);
        if (list.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }

    // 5. Update Employee Details (lastName, phone, address)
    @PutMapping("/{email}")
    public ResponseEntity<Employee> updateEmployeeDetails(
            @PathVariable String email,
            @Valid @RequestBody UpdateEmployeeDetails dto) {
        return employeeService.updateEmployeeDetails(email, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 6. Update Employee Phone Only (partial update)
    @PatchMapping("/phone/{email}")
    public ResponseEntity<Employee> updateEmployeePhone(
            @PathVariable String email,
            @Valid @RequestBody UpdateEmployeePhone dto) {
        return employeeService.updateEmployeePhone(email, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 7. Delete Employee by Email
    @DeleteMapping("/email/{email}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String email) {
        boolean deleted = employeeService.deleteEmployeeByEmail(email);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok("Employee deleted successfully");
    }
    // JPA Specification
    @GetMapping("/spec/email/{email}")
    public ResponseEntity<List<Employee>> getByEmailSpec(@PathVariable String email) {
        List<Employee> list = employeeService.searchByEmailSpec(email);
        if (list.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
     }

    @GetMapping("/spec/name/{name}")
    public ResponseEntity<List<Employee>> getByNameSpec(@PathVariable String name) {
        List<Employee> list = employeeService.searchByNameSpec(name);
        if (list.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
     }
     // HQL
     @GetMapping("/hql/email/{email}")
     public ResponseEntity<Employee> getByEmailHQL(@PathVariable String email) {
        return employeeService.searchByEmailHQL(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }   
    @GetMapping("/hql/name/{name}")
    public ResponseEntity<List<Employee>> getByNameHQL(@PathVariable String name) {
        List<Employee> list = employeeService.searchByNameHQL(name);
        if (list.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }

    // Native SQL
    @GetMapping("/native/email/{email}")
    public ResponseEntity<Employee> getByEmailNative(@PathVariable String email) {
        return employeeService.searchByEmailNative(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/native/name/{name}")
    public ResponseEntity<List<Employee>> getByNameNative(@PathVariable String name) {
        List<Employee> list = employeeService.searchByNameNative(name);
        if (list.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(list);
    }     
 }