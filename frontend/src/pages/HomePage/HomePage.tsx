import React, { useEffect, useState } from "react";
import { Employee } from "../../services/APIResponseInterface";
import { getAllEmployees } from "../../services/EmployeeServices";
import { useQuery, useQueryClient } from "react-query";
import LoadingSpinner from "../../components/LoadingSpinner/LoadingSpinner";
import ErrMsg from "../../components/ErrMsg/ErrMsg";
import { useNavigate } from "react-router-dom";
import EmployeesList from "../../components/EmployeesList/EmployeesList";

const HomePage = () => {
  const navigate = useNavigate();
  // Queries
  const { isLoading, isError, data, error } = useQuery({
    queryKey: ["employees"],
    queryFn: getAllEmployees,
  });

  const handleClick = (): void => {
    navigate("/add");
  };

  return (
    <div>
      <h1>Employees</h1>
      <button onClick={handleClick} title="Add New Employee">
        Add Employee
      </button>
      {isLoading && <LoadingSpinner />}
      {!isLoading && isError && (
        <ErrMsg
          msg={error.message}
          closeMsg={function (): unknown {
            throw new Error("Function not implemented.");
          }}
        />
      )}
      {!isLoading && !isError && <EmployeesList employeesList={data} />}
    </div>
  );
};

export default HomePage;
