package com.example.employee.specification;

import com.example.employee.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> hasEmail(String email) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("email")), email.toLowerCase());
    }

    public static Specification<Employee> hasName(String name) {
        return (root, query, cb) -> cb.or(
                cb.equal(cb.lower(root.get("firstName")), name.toLowerCase()),
                cb.equal(cb.lower(root.get("lastName")), name.toLowerCase())
        );
    }
}
