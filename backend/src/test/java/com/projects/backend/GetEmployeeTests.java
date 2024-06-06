package com.projects.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.projects.backend.employee.ContractType;
import com.projects.backend.employee.Employee;
import com.projects.backend.employee.EmployeeController;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.projects.backend.employee.EmployeeService;
import com.projects.backend.employee.EmploymentType;

@WebMvcTest(EmployeeController.class)
public class GetEmployeeTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @Test
	void shouldGetEmployees() throws Exception {
    Employee emp1 = new Employee();
    emp1.setId(Long.valueOf(1));
    emp1.setFirstName("John1");
    emp1.setMiddleName("Mid");
    emp1.setLastName("Smith1");
    emp1.setEmail("aa@gg.com");
    emp1.setMobileNumber("3334443333");
    emp1.setAddress("2 E");
    emp1.setContractType(ContractType.CONTRACT);
    emp1.setStartDate(new Date());
    emp1.setEmploymentType(EmploymentType.PART_TIME);
    emp1.setHoursPerWeek(40);
		Employee emp2 = new Employee();
    emp2.setId(Long.valueOf(1));
    emp2.setFirstName("John2");
    emp2.setMiddleName("Mid2");
    emp2.setLastName("Smith2");
    emp2.setEmail("aa@gg.com");
    emp2.setMobileNumber("3334443333");
    emp2.setAddress("2 E");
    emp2.setContractType(ContractType.CONTRACT);
    emp2.setStartDate(new Date());
    emp2.setEmploymentType(EmploymentType.PART_TIME);
    emp2.setHoursPerWeek(40);
		Employee emp3 = new Employee();
    emp3.setId(Long.valueOf(1));
    emp3.setFirstName("John3");
    emp3.setMiddleName("Mid3");
    emp3.setLastName("Smith3");
    emp3.setEmail("aa@gg.com");
    emp3.setMobileNumber("3334443333");
    emp3.setAddress("2 E");
    emp3.setContractType(ContractType.CONTRACT);
    emp3.setStartDate(new Date());
    emp3.setEmploymentType(EmploymentType.PART_TIME);
    emp3.setHoursPerWeek(40);
		List<Employee> empList = new ArrayList<Employee>();
    empList.add(emp1);
    empList.add(emp2);
    empList.add(emp3);

    when(this.employeeService.findAll()).thenReturn(empList);
		
    this.mockMvc.perform(get("http://localhost:8080/employees"))
          .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.id").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.firstName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.middleName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.lastName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.email").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.mobileNumber").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.contractType").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.address").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.employmentType").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.startDate").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.finishDate").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.*.hoursPerWeek").exists());
	}	
  
  @Test
	void shouldGetEmployeesWithAnEmptyList() throws Exception {

    when(this.employeeService.findAll()).thenReturn(new ArrayList<Employee>());
		
    this.mockMvc.perform(get("/employees")).andExpect(status().isOk());
    this.mockMvc.perform(get("http://localhost:8080/employees"))
          .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.*").doesNotExist());
	}	
  
@Test
void shouldGetCorrectEmployeeWithId() throws Exception {
    Employee emp = new Employee();
    emp.setId(Long.valueOf(1));
    emp.setFirstName("John");
    emp.setMiddleName("Mid");
    emp.setLastName("Smith");
    emp.setEmail("aa@gg.com");
    emp.setMobileNumber("3334443333");
    emp.setAddress("2 E");
    emp.setContractType(ContractType.CONTRACT);
    emp.setStartDate(new Date());
    emp.setEmploymentType(EmploymentType.PART_TIME);
    emp.setHoursPerWeek(40);
		
    when(this.employeeService.findById(Long.valueOf(1))).thenReturn(Optional.of(emp));
  
    this.mockMvc.perform(get("http://localhost:8080/employees/{id}",1))
          .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
          .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.middleName").value("Mid"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Smith"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("aa@gg.com"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.mobileNumber").value("3334443333"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.contractType").value(ContractType.CONTRACT.getValue()))
          .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("2 E"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.employmentType").value(EmploymentType.PART_TIME.getValue()))
          .andExpect(MockMvcResultMatchers.jsonPath("$.hoursPerWeek").value(40));
                             
 	}	

  
@Test
void shouldReturnNotFoundStatusIfIdDoesnNotExist() throws Exception {
    Employee emp = new Employee();
    emp.setId(Long.valueOf(1));
    emp.setFirstName("John");
    emp.setMiddleName("Mid");
    emp.setLastName("Smith");
    emp.setEmail("aa@gg.com");
    emp.setMobileNumber("3334443333");
    emp.setAddress("2 E");
    emp.setContractType(ContractType.CONTRACT);
    emp.setStartDate(new Date());
    emp.setEmploymentType(EmploymentType.PART_TIME);
    emp.setHoursPerWeek(40);
		
    when(this.employeeService.findById(Long.valueOf(1))).thenReturn(Optional.of(emp));
  
    this.mockMvc.perform(get("http://localhost:8080/employees/{id}",111))
          .andExpect(status().isNotFound());                             
 	}	
 
}
