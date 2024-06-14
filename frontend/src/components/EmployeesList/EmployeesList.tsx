import React from "react";
import { Employee } from "../../services/APIResponseInterface";
import EmployeeCard from "../EmployeeCard/EmployeeCard";
import { useLocation, useNavigate } from "react-router-dom";
import { useMutation, useQueryClient } from "react-query";
import { deleteEmployee } from "../../services/EmployeeServices";
import { isAdmin } from "../../features/Auth/AuthSlice";

const EmployeesList = ({ employeesList }: { employeesList: Employee[] }) => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const roleAdmin = isAdmin();

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
    <table className="table-fixed border w-full  border-black">
      <thead className="bg-teal-500  text-slate-900">
        <tr>
          <th className="uppercase p-2">Employee Name</th>
          <th className="uppercase hidden sm:table-cell">Contract Type</th>
          <th className="uppercase  hidden sm:table-cell">Employement Type</th>
          {roleAdmin && <th className="uppercase"></th>}
        </tr>
      </thead>
      <tbody>
        {employeesList.map((employee) => (
          <EmployeeCard
            key={employee.id}
            employee={employee}
            handleEdit={handleEditEmployee}
            handleDelete={handleDeleteEmployee}
          />
        ))}
      </tbody>
    </table>
  );
};

export default EmployeesList;
