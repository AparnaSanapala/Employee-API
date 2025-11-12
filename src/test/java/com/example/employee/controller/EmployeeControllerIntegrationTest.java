package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository repo;

    private Employee emp;

    @BeforeEach
    void setup() {
        repo.deleteAll();

        emp = new Employee();
        emp.setFirstName("Aparna");
        emp.setLastName("Sanapala");
        emp.setEmail("aparna@example.com");
        emp.setPhone("1234567890");
        emp.setAddress("123 Main St");
        repo.save(emp);
    }

    // Test GET by email using native 
    @Test
    void testGetByEmailNative() throws Exception {
        mockMvc.perform(get("/employees/native/email/aparna@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Aparna"));
    }

    // Test GET by name using HQL 
    @Test
    void testGetByNameHQL() throws Exception {
        mockMvc.perform(get("/employees/hql/name/Aparna"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("aparna@example.com"));
    }

    // Test POST (create employee) 
    @Test
    void testCreateEmployee() throws Exception {
        String json = """
                {
                    "firstName": "Priya",
                    "lastName": "Sharma",
                    "email": "priya@example.com"
                }
                """;

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Priya"));
    }

    // Test DELETE 
    @Test
    void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employees/email/aparna@example.com"))
                .andExpect(status().isOk());
    }
}
