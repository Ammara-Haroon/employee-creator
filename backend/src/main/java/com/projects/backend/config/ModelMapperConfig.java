package com.projects.backend.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.projects.backend.employee.ContractType;
import com.projects.backend.employee.CreateEmployeeDTO;
import com.projects.backend.employee.Employee;
import com.projects.backend.employee.EmploymentType;
import com.projects.backend.employee.UpdateEmployeeDTO;

import jakarta.validation.ValidationException;

@Configuration
public class ModelMapperConfig {
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setSkipNullEnabled(true);
    
    mapper.typeMap(CreateEmployeeDTO.class, Employee.class)
        .addMappings(m -> m.using(new NameConverter()).map(CreateEmployeeDTO::getFirstName,
            Employee::setFirstName))
        .addMappings(m -> m.using(new NameConverter()).map(CreateEmployeeDTO::getLastName, Employee::setLastName))
        .addMappings(m -> m.using(new NameConverter()).map(CreateEmployeeDTO::getMiddleName, Employee::setMiddleName))
        .addMappings(m -> m.using(new StringTrimLowerCaseConverter()).map(CreateEmployeeDTO::getEmail, Employee::setEmail))
        .addMappings(m -> m.using(new DateConverter()).map(CreateEmployeeDTO::getStartDate, Employee::setStartDate))
        .addMappings(m -> m.using(new DateConverter()).map(CreateEmployeeDTO::getFinishDate, Employee::setFinishDate))
        .addMappings(m -> m.using(new ContractTypeConverter()).map(CreateEmployeeDTO::getContractType, Employee::setContractType))
        .addMappings(m -> m.using(new StringTrimLowerCaseConverter()).map(CreateEmployeeDTO::getEmploymentType, Employee::setEmploymentType))
        .addMappings(m -> m.using(new AddressConverter()).map(CreateEmployeeDTO::getAddress, Employee::setAddress))
        .addMappings(m -> m.using(new EmployeeTypeConverter()).map(CreateEmployeeDTO::getEmploymentType, Employee::setEmploymentType))
        .addMappings(m -> m.using(new ContractTypeConverter()).map(CreateEmployeeDTO::getContractType, Employee::setContractType))
         ;
        
    mapper.typeMap(UpdateEmployeeDTO.class, Employee.class)
        .addMappings(m -> m.using(new NameConverter()).map(UpdateEmployeeDTO::getFirstName,
            Employee::setFirstName))
        .addMappings(m -> m.using(new NameConverter()).map(UpdateEmployeeDTO::getLastName, Employee::setLastName))
        .addMappings(m -> m.using(new NameConverter()).map(UpdateEmployeeDTO::getMiddleName, Employee::setMiddleName))
        .addMappings(m -> m.using(new StringTrimLowerCaseConverter()).map(UpdateEmployeeDTO::getEmail, Employee::setEmail))
        .addMappings(m -> m.using(new DateConverter()).map(UpdateEmployeeDTO::getStartDate, Employee::setStartDate))
        .addMappings(m -> m.using(new DateConverter()).map(UpdateEmployeeDTO::getFinishDate, Employee::setFinishDate))
        .addMappings(m -> m.using(new ContractTypeConverter()).map(UpdateEmployeeDTO::getContractType, Employee::setContractType))
        .addMappings(m -> m.using(new StringTrimLowerCaseConverter()).map(UpdateEmployeeDTO::getEmploymentType, Employee::setEmploymentType))
        .addMappings(m -> m.using(new AddressConverter()).map(UpdateEmployeeDTO::getAddress, Employee::setAddress))
        .addMappings(m -> m.using(new EmployeeTypeConverter()).map(UpdateEmployeeDTO::getEmploymentType, Employee::setEmploymentType))
        .addMappings(m -> m.using(new ContractTypeConverter()).map(UpdateEmployeeDTO::getContractType, Employee::setContractType))
         ;
    
    return mapper;
  }

  private class StringTrimLowerCaseConverter implements Converter<String, String> {

    @Override
    public String convert(MappingContext<String, String> context) {
      if (context.getSource() == null) {
        return null;
      }
      return context.getSource().trim().toLowerCase();
    }

  }

  private class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(MappingContext<String, Date> context) {
      if (context.getSource() == null) {
        return null;
      }
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date date;
      try {
        date = formatter.parse(context.getSource());
      } catch (ParseException e) {
        
        e.printStackTrace();
        throw new ValidationException("Erro in date format - should be yyyy-MM-dd");
      }
      return date;
    }

  }
  private class EmployeeTypeConverter implements Converter<String, EmploymentType> {

    @Override
    public EmploymentType convert(MappingContext<String, EmploymentType> context) {
      if (context.getSource() == null) {
        return null;
      }
      return EmploymentType.valueOf(context.getSource().trim().toUpperCase());
    }

  }

  private class ContractTypeConverter implements Converter<String, ContractType> {

    @Override
    public ContractType convert(MappingContext<String, ContractType> context) {
      if (context.getSource() == null) {
        return null;
      }
      return ContractType.valueOf(context.getSource().trim().toUpperCase());
    }

  }

  private class NameConverter implements Converter<String, String> {

    @Override
    public String convert(MappingContext<String, String> context) {
      if (context.getSource() == null) {
        return null;
      }
      String trimmed =  context.getSource().trim();
      return String.format("%s%s",trimmed.substring(0,1).toUpperCase(),trimmed.substring(1).toLowerCase());
    }

  }

  private class AddressConverter implements Converter<String, String> {

    @Override
    public String convert(MappingContext<String, String> context) {
      if (context.getSource() == null) {
        return null;
      }
      String[] strArr =  context.getSource().trim().toLowerCase().split(" ");
      
      return Arrays.stream(strArr)
        .filter(val->val.length() != 0)
        .map(str -> 
            String.format("%s%s",str.substring(0,1)
            .toUpperCase(),str.substring(1)))
        .collect(Collectors.joining(" "));    
    }

  }
}
