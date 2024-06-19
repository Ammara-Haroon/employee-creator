package com.projects.backend;

import com.projects.backend.config.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.projects.backend.employee.EmployeeController;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

import com.projects.backend.employee.EmployeeService;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;


@ExtendWith(SpringExtension.class) 
@ContextConfiguration 
@WebMvcTest(EmployeeController.class)
@WithMockUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
public class DeleteEmployeeTests {
   @Autowired
    protected WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    MockMvc mockMvc;

    

   
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	
  }
  @MockBean
  private EmployeeService employeeService;
  
  @Test
  public void test1() throws Exception {
    
    mockMvc.perform(post("http://localhost:8080/login")
        .with(httpBasic("user", "password")))
        .andExpect(MockMvcResultMatchers.content().string("hello"));
  } 
  
@Test
@WithMockUser
void shouldDeleteEmployeeWithCorrectIdOf1() throws Exception {
     when(this.employeeService.deleteById(Long.valueOf(1))).thenReturn(true);
mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	
    this.mockMvc.perform(delete("http://localhost:8080/employees/{id}",1).with(csrf().asHeader()))
          .andExpect(status().is2xxSuccessful());                   
 	}	

  
@Test
@WithMockUser
void shouldReturnNotFoundStatusIfIdDoesnNotExist() throws Exception {
    when(this.employeeService.deleteById(Long.valueOf(1))).thenReturn(true);
  
    this.mockMvc.perform(delete("http://localhost:8080/employees/{id}",111))
          .andExpect(status().isNotFound());                             
 	}	
 
}
