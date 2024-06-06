package com.projects.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.projects.backend.employee.EmployeeController;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.projects.backend.employee.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class DeleteEmployeeTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  
@Test
void shouldDeleteEmployeeWithCorrectIdOf1() throws Exception {
    
    when(this.employeeService.deleteById(Long.valueOf(1))).thenReturn(true);

    this.mockMvc.perform(delete("http://localhost:8080/employees/{id}",1))
          .andExpect(status().is2xxSuccessful());                   
 	}	

  
@Test
void shouldReturnNotFoundStatusIfIdDoesnNotExist() throws Exception {
    when(this.employeeService.deleteById(Long.valueOf(1))).thenReturn(true);
  
    this.mockMvc.perform(delete("http://localhost:8080/employees/{id}",111))
          .andExpect(status().isNotFound());                             
 	}	
 
}
