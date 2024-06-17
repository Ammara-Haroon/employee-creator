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
import { useDispatch, useSelector } from "react-redux";
import { show } from "../../features/Notifcations/NotificationSlice";
import { RootState } from "../../app/store";
import { isAdmin } from "../../features/Auth/AuthSlice";

const EmployeeFormPage = () => {
  const queryClient = useQueryClient();
  const location = useLocation();
  const navigate = useNavigate();
  const mode = location.pathname === "/edit" ? Mode.EDIT : Mode.ADD;
  const employee = location.state?.employeeData;
  console.log("state ", employee, mode == Mode.EDIT);
  console.log("location ", location.pathname);
  const mutationFn = mode == Mode.EDIT ? updateEmployee : createEmployee;
  const dispatch = useDispatch();

  const { authenticated } = useSelector((state: RootState) => state.auth);
  const roleAdmin = isAdmin();

  console.log("add Page", authenticated);
  if (!authenticated && !roleAdmin) {
    console.log("return null");
    dispatch(show("Unauthorised Access"));
    return <ErrMsg />;
  }
  const mutation = useMutation({
    mutationFn: mutationFn,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["employees"] });
    },
    onError: (error: any) => {
      console.log(error.message);
      dispatch(show("Something went wrong. Unable to save information."));
    },
  });

  const saveEmployee = (emp: Employee): void => {
    console.log("saving ", emp);
    mutation.mutate(emp);

    navigate("/dashboard");
  };

  return (
    <div className="bg-gray-100">
      <Link
        className="text-cyan-500 text-3xl py-5 px-2"
        title="Go Back"
        to="/dashboard"
      >
        <FontAwesomeIcon icon={faArrowAltCircleLeft} />
      </Link>
      <EmployeeForm
        mode={mode}
        employee={employee}
        saveEmployee={saveEmployee}
      />
      <ErrMsg />
    </div>
  );
};

export default EmployeeFormPage;
