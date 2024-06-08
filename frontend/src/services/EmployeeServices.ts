import { Employee } from "./APIResponseInterface";

export const getAllEmployees = async (): Promise<Array<Employee>> => {
  const data = await fetch("http://localhost:8080/employees");
  const employees = await data.json();
  console.log(employees);
  return employees;
};
