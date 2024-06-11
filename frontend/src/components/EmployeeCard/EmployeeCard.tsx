import React from "react";
import { Employee } from "../../services/APIResponseInterface";
import { deleteEmployee } from "../../services/EmployeeServices";

interface IEmployeeCardProps {
  employee: Employee;
  handleEdit(employee: Employee): unknown;
  handleDelete(id: number): unknown;
}
const EmployeeCard = ({
  employee,
  handleEdit,
  handleDelete,
}: IEmployeeCardProps) => (
  <div>
    <h3>
      {employee.firstName} {employee.middleName} {employee.lastName}
    </h3>
    <p>{employee.email}</p>
    <p>{employee.address}</p>
    <p>{employee.contractType}</p>
    <p>{employee.employmentType}</p>
    <p>{employee.startDate.toString()}</p>
    <p>{employee.finishDate?.toString()}</p>
    <p>{employee.hoursPerWeek}</p>
    <p>{employee.mobileNumber}</p>
    <button onClick={() => handleEdit(employee)}>Edit</button>
    <button onClick={() => handleDelete(employee.id)}>Delete</button>
  </div>
);

export default EmployeeCard;
