import { faAdd } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { zodResolver } from "@hookform/resolvers/zod";
import React, { useState } from "react";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { Link } from "react-router-dom";
import schema from "./schema";
import DatePicker, { registerLocale } from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { enAU } from "date-fns/locale/en-AU";

import {
  ContractType,
  Employee,
  EmploymentType,
} from "../../services/APIResponseInterface";
import { useQueryClient } from "@tanstack/react-query";
import { Mode } from "../../pages/EmployeeFormPage/Mode";

registerLocale("en-AU", enAU);
interface IEmployeeFormProps {
  mode: Mode;
  saveEmployee(emp: Employee): unknown;
  employee: Employee | undefined;
}
const EmployeeForm = ({ mode, employee, saveEmployee }: IEmployeeFormProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<Employee>({
    resolver: zodResolver(schema),
    defaultValues: {
      startDate: mode === Mode.EDIT ? employee?.startDate : new Date(),
      employmentType:
        mode === Mode.EDIT
          ? employee?.employmentType
          : EmploymentType.FULL_TIME,
      contractType:
        mode === Mode.EDIT ? employee?.contractType : ContractType.PERMANENT,
      firstName: mode === Mode.EDIT ? employee?.firstName : "",
      middleName: mode === Mode.EDIT ? employee?.middleName : "",
      lastName: mode === Mode.EDIT ? employee?.lastName : "",
      address: mode === Mode.EDIT ? employee?.address : "",
      email: mode === Mode.EDIT ? employee?.email : "",
      finishDate: mode === Mode.EDIT ? employee?.finishDate : null,
      hoursPerWeek: mode === Mode.EDIT ? employee?.hoursPerWeek : undefined,
      mobileNumber: mode === Mode.EDIT ? employee?.mobileNumber : "",
    },
  });
  //const queryClient = useQueryClient();
  const [startDate, setStartDate] = useState<Date>(new Date());
  const [finishDate, setFinishDate] = useState<Date | null>(null);
  const onSubmit: SubmitHandler<Employee> = (data) => {
    if (startDate) data.startDate = startDate;
    data.finishDate = finishDate;
    if (data.middleName === "") data.middleName = null;
    console.log(data);
    if (mode === Mode.EDIT && employee?.id) data.id = employee.id;
    saveEmployee(data);
  };

  return (
    <div>
      <h1>Employee Details</h1>
      <form onSubmit={handleSubmit(onSubmit)}>
        <h2>Personal Information</h2>
        <div>
          <label htmlFor="firstName">First name</label>
          <input
            type="text"
            placeholder="John"
            id="firstName"
            {...register("firstName")}
          />
          {errors.firstName && <span>{errors.firstName.message}</span>}
        </div>
        <div>
          <label htmlFor="middleName">Middle name</label>
          <input
            placeholder="---"
            type="text"
            id="middleName"
            {...register("middleName")}
          />
          {errors.middleName && <span>{errors.middleName.message}</span>}
        </div>
        <div>
          <label htmlFor="lastName">Last name</label>
          <input
            type="text"
            placeholder="Smith"
            id="lastName"
            {...register("lastName")}
          />
          {errors.lastName && <span>{errors.lastName.message}</span>}
        </div>
        <h2>Contact Details</h2>
        <div>
          <label htmlFor="email">Email</label>
          <input
            type="text"
            placeholder="john.smith@somemail.com"
            id="email"
            {...register("email")}
          />
          {errors.email && <span>{errors.email.message}</span>}
        </div>
        <div>
          <label htmlFor="mobileNumber">Mobile Number</label>
          <input
            type="text"
            placeholder="04xxxxxxxxxx"
            id="mobileNumber"
            {...register("mobileNumber")}
          />
          {errors.mobileNumber && <span>{errors.mobileNumber.message}</span>}
        </div>
        <div>
          <label htmlFor="address">Address</label>
          <input
            type="text"
            placeholder="1 Example Pl, Sydney, NSW 2000"
            id="address"
            {...register("address")}
          />
          {errors.address && <span>{errors.address.message}</span>}
        </div>
        <h2>Employee Status</h2>
        <div>
          <label>Contract Type</label>
          <div>
            <input
              type="radio"
              id="permanent"
              value="PERMANENT"
              {...register("contractType")}
            />
            <label htmlFor="permanent">Permanent</label>
          </div>

          <div>
            <input
              type="radio"
              id="contract"
              value="CONTRACT"
              {...register("contractType")}
            />
            <label htmlFor="contract">Contract</label>
          </div>
        </div>
        <div>
          <label htmlFor="startDate">Starting Date</label>
          <DatePicker
            onChange={(date) => {
              if (date) return setStartDate(date);
              else return new Date();
            }}
            selected={startDate}
            dateFormat="dd/MM/yyyy"
            locale="en-AU"
            id="startDate"
          />
        </div>
        <div>
          <label htmlFor="finishDate">Finishing Date</label>
          <DatePicker
            onChange={(date) => {
              setFinishDate(date);
            }}
            selected={finishDate}
            dateFormat="dd/MM/yyyy"
            locale="en-AU"
            id="finishDate"
          />
        </div>
        <div>
          <label>Employement Type</label>
          <div>
            <input
              type="radio"
              id="fullTime"
              value="FULL_TIME"
              {...register("employmentType")}
            />
            <label htmlFor="fullTime">Full-time</label>
          </div>
          <div>
            <input
              type="radio"
              id="partTime"
              value="PART_TIME"
              {...register("employmentType")}
            />
            <label htmlFor="partTime">Part-time</label>
          </div>
        </div>
        <div>
          <label htmlFor="hoursPerWeek">Hours per week</label>
          <input
            type="number"
            placeholder="1-40"
            id="hoursPerWeek"
            {...register("hoursPerWeek")}
          />
          {errors.hoursPerWeek && <span>{errors.hoursPerWeek.message}</span>}
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default EmployeeForm;
