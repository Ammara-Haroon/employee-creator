import React from "react";
import EmployeeForm from "../../components/EmployeeForm/EmployeeForm";
import { Employee } from "../../services/APIResponseInterface";
import { useMutation, useQueries, useQueryClient } from "react-query";
import {
  createEmployee,
  updateEmployee,
} from "../../services/EmployeeServices";
import {
  Link,
  useLocation,
  useNavigate,
  useNavigation,
} from "react-router-dom";
import { Mode } from "./Mode";

const EmployeeFormPage = () => {
  const queryClient = useQueryClient();
  const location = useLocation();
  const mode = location.pathname === "/edit" ? Mode.EDIT : Mode.ADD;
  const employee = location.state?.employeeData;
  console.log("state ", location.state, mode == Mode.EDIT);
  console.log("location ", location.pathname);
  const mutationFn = mode == Mode.EDIT ? updateEmployee : createEmployee;
  const mutation = useMutation({
    mutationFn: mutationFn,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["employees"] });
    },
    onError: () => {},
  });

  const saveEmployee = (emp: Employee): void => {
    mutation.mutate(emp);
  };

  return (
    <div>
      <Link to="/">Back</Link>
      <EmployeeForm
        mode={mode}
        employee={employee}
        saveEmployee={saveEmployee}
      />
    </div>
  );
};

export default EmployeeFormPage;
