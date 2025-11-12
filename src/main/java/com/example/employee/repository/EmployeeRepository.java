package com.example.employee.repository;
import com.example.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>  {
    Optional<Employee> findByEmailIgnoreCase(String email);

    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstName)=LOWER(?1) OR LOWER(e.lastName)=LOWER(?1)")
    List<Employee> findByName(String name);

    void deleteByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    // HQL
    @Query("FROM Employee e WHERE LOWER(e.email) = LOWER(:email)")
    Optional<Employee> searchByEmailHQL(@Param("email") String email);

    @Query("FROM Employee e WHERE LOWER(e.firstName) = LOWER(:name) OR LOWER(e.lastName) = LOWER(:name)")
    List<Employee> searchByNameHQL(@Param("name") String name);

    // Native SQL
    @Query(value = "SELECT * FROM employees WHERE LOWER(email) = LOWER(:email)", nativeQuery = true)
    Optional<Employee> searchByEmailNative(@Param("email") String email);

    @Query(value = "SELECT * FROM employees WHERE LOWER(first_name) = LOWER(:name) OR LOWER(last_name) = LOWER(:name)", nativeQuery = true)
    List<Employee> searchByNameNative(@Param("name") String name);



}
