package com.projects.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.projects.backend.employee.ContractType;
import com.projects.backend.employee.Employee;
import com.projects.backend.employee.EmployeeController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import com.projects.backend.employee.EmployeeService;
import com.projects.backend.employee.EmploymentType;
import com.projects.backend.employee.UpdateEmployeeDTO;

@WebMvcTest(EmployeeController.class)
public class UpdateEmployeeTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @MockBean
  private SecurityFilterChain securityFilterChain;

  @BeforeEach
  public void setupService() {
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
    when(this.employeeService.updateById(eq(Long.valueOf(1)), any(UpdateEmployeeDTO.class)))
        .thenReturn(java.util.Optional.of(emp));
  }

  @Test
  void shouldUpdateEmployeeByIdAndReturnTheUpdatedEmployee() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isOk())
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
  void shouldGiveNotFoundErrorResponseIfIdIsNotFound() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40}";

    mockMvc.perform(patch("/employees/111")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldGiveErrorReponseWhenFirstNameIsLeftBlank() throws Exception {

    String jsonEmp = "{\"firstName\":\"   \"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenLastNameIsLeftBlank() throws Exception {

    String jsonEmp = "{\"lastName\":\"    \"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenMiddleNameIsLeftBlank() throws Exception {

    String jsonEmp = "{\"middleName\":\"   \"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenEmailIsOfWrongFormat() throws Exception {

    String jsonEmp = "{\"email\":\"aagg.com\"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenMobileNumberIsWrongFormat() throws Exception {

    String jsonEmp = "{\"mobileNumber\":\"12%%2323abc2112\"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenMobileNumberIsTooSmall() throws Exception {

    String jsonEmp = "{\"mobileNumber\":\"2112\"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenMobileNumberIsTooBig() throws Exception {

    String jsonEmp = "{\"mobileNumber\":\"2112983983584309859834580843085039845083084508430985094830580438590840398509843085098049385098409385\"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenAddressDoesNotHAveAtLeast2WhiteSpaceCharacters() throws Exception {

    String jsonEmp = "{\"address\":\"  2 \"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenContractTypeIsInCorrect() throws Exception {

    String jsonEmp = "{\"contractType\":\"something\"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenEmploymentTypeIsIncorrect() throws Exception {

    String jsonEmp = "{\"employmentType\":\"SOME_TIME\"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenStartDateIsWrongFormat() throws Exception {

    String jsonEmp = "{\"startDate\":\"20241212\"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenFinishDateIsOfWrongFormat() throws Exception {

    String jsonEmp = "{\"finishDate\":\"20201212\"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenHoursPerWeekAreWrongFormat() throws Exception {

    String jsonEmp = "{\"hoursPerWeek\":\"hours\"}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenHoursPerWeekAreLessThan1() throws Exception {

    String jsonEmp = "{\"hoursPerWeek\":0}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenHoursPerWeekMoreThan40() throws Exception {

    String jsonEmp = "{\"hoursPerWeek\":41}";

    mockMvc.perform(patch("/employees/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

}
