import React from "react";
import { Employee } from "../../services/APIResponseInterface";
import EmployeeCard from "../EmployeeCard/EmployeeCard";
import { useLocation, useNavigate } from "react-router-dom";
import { useMutation, useQueryClient } from "react-query";
import { deleteEmployee } from "../../services/EmployeeServices";

const EmployeesList = ({ employeesList }: { employeesList: Employee[] }) => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const mutation = useMutation({
    mutationFn: deleteEmployee,
    onSuccess: () => queryClient.invalidateQueries({ queryKey: "employees" }),
  });
  const handleEditEmployee = (employee: Employee) => {
    navigate("/edit", { state: { employeeData: employee } });
  };
  const handleDeleteEmployee = (id: number) => {
    mutation.mutate(id);
  };
  return (
    <div>
      {employeesList.map((employee) => (
        <EmployeeCard
          key={employee.id}
          employee={employee}
          handleEdit={handleEditEmployee}
          handleDelete={handleDeleteEmployee}
        />
      ))}
    </div>
  );
};

export default EmployeesList;
