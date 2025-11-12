package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository repo;

    private Employee emp;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        emp = new Employee();
        emp.setId(1L);
        emp.setFirstName("Aparna");
        emp.setLastName("Sanapala");
        emp.setEmail("aparna@example.com");
        emp.setPhone("1234567890");
        emp.setAddress("123 Main St");
    }

    // Test JPA Spec search by email 
    
    @Test
    void testSearchByEmailSpec() {
        when(repo.findAll()).thenReturn(Collections.singletonList(emp));

        var result = employeeService.searchByEmailSpec("aparna@example.com");
        assertEquals(1, result.size());
        verify(repo, times(1)).findAll();
    }

    // Test HQL search by email 
    @Test
    void testSearchByEmailHQL() {
        when(repo.searchByEmailHQL("aparna@example.com")).thenReturn(Optional.of(emp));

        var result = employeeService.searchByEmailHQL("aparna@example.com");
        assertTrue(result.isPresent());
        assertEquals("Aparna", result.get().getFirstName());
    }

    // Test Native SQL search by email 
    @Test
    void testSearchByEmailNative() {
        when(repo.searchByEmailNative("aparna@example.com")).thenReturn(Optional.of(emp));

        var result = employeeService.searchByEmailNative("aparna@example.com");
        assertTrue(result.isPresent());
        assertEquals("Aparna", result.get().getFirstName());
    }

    // Test Delete 
    @Test
    void testDeleteEmployeeByEmail() {
        when(repo.findByEmailIgnoreCase("aparna@example.com")).thenReturn(Optional.of(emp));
        doNothing().when(repo).delete(emp);

        boolean deleted = employeeService.deleteEmployeeByEmail("aparna@example.com");
        assertTrue(deleted);
    }
}
