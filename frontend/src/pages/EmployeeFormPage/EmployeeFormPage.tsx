import React, { useState } from "react";
import EmployeeForm from "../../components/EmployeeForm/EmployeeForm";
import { Employee } from "../../services/APIResponseInterface";
import { useMutation, useQueryClient } from "react-query";
import {
  createEmployee,
  updateEmployee,
} from "../../services/EmployeeServices";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { Mode } from "./Mode";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowAltCircleDown } from "@fortawesome/free-regular-svg-icons";
import { faArrowAltCircleLeft } from "@fortawesome/free-solid-svg-icons";
import ErrMsg from "../../components/ErrMsg/ErrMsg";

const EmployeeFormPage = () => {
  const queryClient = useQueryClient();
  const location = useLocation();
  const navigate = useNavigate();
  const mode = location.pathname === "/edit" ? Mode.EDIT : Mode.ADD;
  const employee = location.state?.employeeData;
  console.log("state ", employee, mode == Mode.EDIT);
  console.log("location ", location.pathname);
  const [error, setError] = useState<string | null>(null);
  const mutationFn = mode == Mode.EDIT ? updateEmployee : createEmployee;
  const mutation = useMutation({
    mutationFn: mutationFn,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["employees"] });
    },
    onError: (error: any) => {
      setError(error.message);
    },
  });

  const saveEmployee = (emp: Employee): void => {
    mutation.mutate(emp);

    navigate("/");
  };

  return (
    <div className="bg-gray-100">
      <Link className="text-cyan-500 text-3xl py-5 px-2" title="Go Back" to="/">
        <FontAwesomeIcon icon={faArrowAltCircleLeft} />
      </Link>
      <EmployeeForm
        mode={mode}
        employee={employee}
        saveEmployee={saveEmployee}
      />
      {error && <ErrMsg msg={error} onClose={() => navigate("")} />}
    </div>
  );
};

export default EmployeeFormPage;
