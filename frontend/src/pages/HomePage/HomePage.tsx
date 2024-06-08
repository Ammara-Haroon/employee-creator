import React, { useEffect, useState } from "react";
import { Employee } from "../../services/APIResponseInterface";
import { getAllEmployees } from "../../services/EmployeeServices";

const HomePage = () => {
  const [employees, setEmployees] = useState<Array<Employee>>([]);

  useEffect(() => {
    getAllEmployees().then((data) => setEmployees(data));
  }, []);

  return (
    <div>
      <h1>Employees</h1>
      <button title="Add New Employee">Add Employee</button>
      <div>
        <ul>
          {employees.map((employee) => (
            <li key={employee.id}>{employee.firstName}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default HomePage;
