import React, { useEffect, useState } from "react";
import { Employee } from "../../services/APIResponseInterface";
import { getAllEmployees } from "../../services/EmployeeServices";
import { useQuery, useQueryClient } from "react-query";
import LoadingSpinner from "../../components/LoadingSpinner/LoadingSpinner";
import ErrMsg from "../../components/ErrMsg/ErrMsg";
import { useNavigate } from "react-router-dom";
import EmployeesList from "../../components/EmployeesList/EmployeesList";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAdd } from "@fortawesome/free-solid-svg-icons";
import { useDispatch, useSelector } from "react-redux";
import { show } from "../../features/Notifcations/NotificationSlice";

import { RootState } from "../../app/store";

const HomePage = () => {
  const { authenticated } = useSelector((state: RootState) => state.auth);
  console.log("Home Page", authenticated);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  if (!authenticated) {
    console.log("return null");
    dispatch(show("Unauthorised Access"));
    return <ErrMsg />;
  }

  // Queries
  const { isLoading, isError, data } = useQuery({
    queryKey: ["employees"],
    queryFn: getAllEmployees,
    onError: (err: any) => dispatch(show(err.message)),
    retry: false,
  });
  const handleClick = (): void => {
    navigate("/add");
  };

  return (
    <div className="bg-gray-200">
      <div className="py-5 px-2  flex justify-between flex-wrap">
        <h1 className="text-2xl font-bold text-cyan-900 uppercase ">
          Employees Dashboard
        </h1>
        <button
          className="hover:bg-teal-500 mt-16-md mt-5 h-fit text-right bg-cyan-800 px-3 py-2 rounded-full uppercase text-gray-200"
          onClick={handleClick}
          title="Add New Employee"
        >
          <FontAwesomeIcon className="px-1" icon={faAdd} />
          Add New Empoyee
        </button>
      </div>
      {isLoading && <LoadingSpinner />}
      <ErrMsg />
      {!isLoading && !isError && data && <EmployeesList employeesList={data} />}
    </div>
  );
};

export default HomePage;
