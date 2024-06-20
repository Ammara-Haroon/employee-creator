# Employee Creator
[![Front End Tests](https://github.com/Ammara-Haroon/employee-creator/actions/workflows/main.yml/badge.svg)](https://github.com/Ammara-Haroon/employee-creator/actions/workflows/main.yml)
[![Backend Testing](https://github.com/Ammara-Haroon/employee-creator/actions/workflows/maven.yml/badge.svg)](https://github.com/Ammara-Haroon/employee-creator/actions/workflows/maven.yml)
## Demo & Snippets

## Purpose of the Project

To create a web application that creates, lists, modifies and deletes employees.

## MVP

The application should consist of a spring RESTful API and a React Typescript frontend.
Backend should have at least three end points:

- create
- get all employees
- delete an employ

## Build Steps

## Design Goals / Approach

### Backend

- Added an Employee controller to manage CRUD operations
- Used Validation I/O and annotations to implement basic validations
- Created custom validator annotations to validate enum values
- Used ModelMapper to clean and created custom converters to map incoming data in data transfer objects (CreateEmployeeDTO, UpdateEmployeeDTO) to entity Employee.
- The validations on DTOs include:
  - All fields except middle name and finish date must be non-null for CreateEmployeeDTO. This is achieved by using Not-Blank annotation.
  - String fields (like first name) must contain at least 1 non-white-space character. This achieved by using Pattern annotation.
  - Address must have at least two non-white-space characters.
  - Email is validated to be of proper format by using Email annotation.
  - Mobile number must be only numbers with minimum length 8 and maximum length 20
  - Number of hours worked should not be less than 1 and greater than 40.
  - Dates should be of format yyyy-MM-dd
  - Custom Validators are implemented to validate contract type and employment type to match a set of enum values.
- The converters used by model mapper perform the following cleanup conversions:
  - All strings are trimmed
  - All names are converted to proper case
  - Addresses are converted to title case
  - Employment and contract types are converted to respective enum values
  - Date strings are converted to dates

## Features

---

## Known issues

---

## Future Goals

---

## Change logs

### 05/06/2024

- Created Employee Controller managing CRUD
- Added ModelMapper and DTOs
- Added Exception handling
- Added custom Validators in DTOs and custom Converters to Model Mapper

### 06/06/2024

- Added swagger to auto-document API
- Added test cases at backend to test Employee Controller

### 07/06/2024 - Built a basic employee form using react-hook-form

### 12/06/2024

- Added styling to frontend components
- Fixed DTO validations issues for finish date

## What did you struggle with?

---
