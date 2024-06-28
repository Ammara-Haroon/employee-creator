package com.projects.backend;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.backend.department.Department;
import com.projects.backend.employee.ContractType;
import com.projects.backend.employee.Employee;
import com.projects.backend.employee.CreateEmployeeDTO;
import com.projects.backend.employee.DepartmentType;
import com.projects.backend.employee.EmployeeController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.projects.backend.employee.EmployeeService;
import com.projects.backend.employee.EmploymentType;

@WebMvcTest(EmployeeController.class)
public class CreateEmployeeTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @MockBean
  private SecurityFilterChain securityFilterChain;

  @BeforeEach
  public void setupService() {
    Department dept = new Department();
    dept.setId(Long.valueOf(1));
    dept.setName("ADMIN");
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
    emp.setDepartment(dept);
    emp.setRole("admin");
    try {
      when(this.employeeService.create(any(CreateEmployeeDTO.class))).thenReturn(Optional.of(emp));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  void shouldCreateEmployeeAndReturnTheCreatedEmployee() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    System.out.println(jsonEmp);
    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
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

  public static String asJsonString(final Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      mapper.setDateFormat(df);
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void shouldGiveErrorReponseWhenFirstNameIsMissing() throws Exception {

    String jsonEmp = "{\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenFirstNameIsLeftBlank() throws Exception {

    String jsonEmp = "{\"firstName\":\"   \",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenLastNameIsMissing() throws Exception {

    String jsonEmp = "{\"middleName\":\"Mid\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenLastNameIsLeftBlank() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"    \",\"email\":\"aa@gg.com\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenMiddleNameIsLeftBlank() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"   \",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenEmailMissing() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenEmailIsOfWrongFormat() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"    \",\"email\":\"aagg.com\",\"mobileNumber\":\"3334443333\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenMobileNumberIsMissing() throws Exception {
    String jsonEmp = "{\"firstName\":\"John\"\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenMobileNumberIsWrongFormat() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"12%%2323abc2112\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenMobileNumberIsTooSmall() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"2112\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenMobileNumberIsTooBig() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"2112983983584309859834580843085039845083084508430985094830580438590840398509843085098049385098409385\",\"address\":\"2 E\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenAddressIsMissing() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenAddressDoesNotHAveAtLeast2WhiteSpaceCharacters() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"  2 \",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenContractTypeIsMissing() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenContractTypeIsInCorrect() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"something\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldGiveErrorReponseWhenEmploymentTypeIsMissing() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenEmploymentTypeIsIncorrect() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"SOME_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenStartDateIsMissing() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"CONTRACT\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenStartDateIsWrongFormat() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"CONTRACT\",\"startDate\":\"20241212\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldNotGiveErrorReponseWhenfinishDateIsMissing() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isCreated());
  }

  @Test
  void shouldGiveErrorReponseWhenFinishDateIsOfWrongFormat() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":\"20201212\",\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":40,\"department\":\"ADMIN\",\"role\":\"admin\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenHoursPerWeekAreWrongFormat() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":\"hours\"}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenHoursPerWeekAreLessThan1() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":0}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

  @Test
  void shouldGiveErrorReponseWhenHoursPerWeekMoreThan40() throws Exception {

    String jsonEmp = "{\"firstName\":\"John\",\"middleName\":\"Mid\",\"lastName\":\"Smith\",\"email\":\"aa@gg.com\",\"mobileNumber\":\"55555555555\",\"address\":\"2 e\",\"contractType\":\"CONTRACT\",\"startDate\":\"2024-12-12\",\"finishDate\":null,\"employmentType\":\"PART_TIME\",\"hoursPerWeek\":41}";

    mockMvc.perform(post("/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonEmp))
        .andExpect(status().isBadRequest());

  }

}
