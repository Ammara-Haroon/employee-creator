import { faAdd } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { zodResolver } from "@hookform/resolvers/zod";
import React, { useState } from "react";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";
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
      hoursPerWeek: mode === Mode.EDIT ? employee?.hoursPerWeek : undefined,
      mobileNumber: mode === Mode.EDIT ? employee?.mobileNumber : "",
    },
  });
  const [startDate, setStartDate] = useState<Date>(
    employee?.startDate || new Date()
  );
  const [finishDate, setFinishDate] = useState<Date | null>(
    employee?.finishDate || null
  );
  const onSubmit: SubmitHandler<Employee> = (data) => {
    if (startDate) data.startDate = startDate;
    data.finishDate = finishDate;
    if (data.middleName === "") data.middleName = null;
    console.log(data);
    if (mode === Mode.EDIT && employee?.id) data.id = employee.id;
    saveEmployee(data);
  };
  const handleChange = (date: Date) => {
    let tmpStartDate: Date;

    if (date) {
      tmpStartDate = date;
    } else {
      tmpStartDate = new Date();
    }
    if (finishDate && finishDate < tmpStartDate) {
      setFinishDate(tmpStartDate);
    }
    setStartDate(tmpStartDate);
  };
  const validateAndSetFinishDate = (date: Date | null) => {
    console.log(date, typeof startDate, date && date < startDate);

    if (date && date < startDate) {
      setFinishDate(startDate);
      return;
    }
    setFinishDate(date);
  };
  const genericInputStyle =
    "p-2 border border-cyan-900 focus:outline-none focus:border-teal-400 focus:ring-2";
  const inputStyleClass = genericInputStyle + " max-w-1/2 w-80";

  const inputWrapperStyleClass = "px-3 flex flex-col";
  const sectionHeadingStyleClass =
    "text-xl font-bold text-cyan-900 uppercase pt-7 pb-4";
  const radioGroupStyleClass = "px-3 py-2";
  const radioButtonStyleClass = "m-2";
  const errorStyleClass = "text-rose-800";
  return (
    <div className="px-2 bg-gray-100">
      <h1 className="py-3 text-2xl font-bold text-cyan-900 uppercase ">
        Employee Details
      </h1>
      <form
        className="text-slate-800 font-semibold p-2"
        onSubmit={handleSubmit(onSubmit)}
      >
        <h2 className={sectionHeadingStyleClass}>Personal Information</h2>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="firstName">First Name:</label>
          <input
            className={inputStyleClass}
            type="text"
            placeholder="John"
            id="firstName"
            {...register("firstName")}
          />
          {errors.firstName ? (
            <small className={errorStyleClass}>
              {errors.firstName.message}
            </small>
          ) : (
            <small>&#8203;</small>
          )}
        </div>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="middleName">Middle name</label>
          <input
            className={inputStyleClass}
            placeholder="---"
            type="text"
            id="middleName"
            {...register("middleName")}
          />
          {errors.middleName ? (
            <small className={errorStyleClass}>
              {errors.middleName.message}
            </small>
          ) : (
            <small>&#8203;</small>
          )}
        </div>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="lastName">Last name</label>
          <input
            className={inputStyleClass}
            type="text"
            placeholder="Smith"
            id="lastName"
            {...register("lastName")}
          />
          {errors.lastName ? (
            <small className={errorStyleClass}>{errors.lastName.message}</small>
          ) : (
            <small>&#8203;</small>
          )}
        </div>
        <h2 className={sectionHeadingStyleClass}>Contact Details</h2>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="email">Email</label>
          <input
            className={inputStyleClass}
            type="text"
            placeholder="john.smith@somemail.com"
            id="email"
            {...register("email")}
          />
          {errors.email ? (
            <small className={errorStyleClass}>{errors.email.message}</small>
          ) : (
            <small>&#8203;</small>
          )}
        </div>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="mobileNumber">Mobile Number</label>
          <input
            className={inputStyleClass}
            type="tel"
            placeholder="04xxxxxxxxxx"
            id="mobileNumber"
            {...register("mobileNumber")}
          />
          {errors.mobileNumber ? (
            <small className={errorStyleClass}>
              {errors.mobileNumber.message}
            </small>
          ) : (
            <small>&#8203;</small>
          )}
        </div>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="address">Address</label>
          <input
            className={genericInputStyle + " w-full"}
            type="text"
            placeholder="1 Example Pl, Sydney, NSW 2000"
            id="address"
            {...register("address")}
          />
          {errors.address ? (
            <small className={errorStyleClass}>{errors.address.message}</small>
          ) : (
            <small>&#8203;</small>
          )}
        </div>
        <h2 className={sectionHeadingStyleClass}>Employment Status</h2>
        <div className={radioGroupStyleClass}>
          <label>What is contract type?</label>
          <div>
            <input
              className={radioButtonStyleClass}
              type="radio"
              id="permanent"
              value="PERMANENT"
              {...register("contractType")}
            />
            <label htmlFor="permanent">Permanent</label>
          </div>

          <div>
            <input
              className={radioButtonStyleClass}
              type="radio"
              id="contract"
              value="CONTRACT"
              {...register("contractType")}
            />
            <label htmlFor="contract">Contract</label>
          </div>
        </div>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="startDate">Starting Date</label>
          <DatePicker
            className={genericInputStyle + " w-40"}
            onChange={handleChange}
            selected={startDate}
            dateFormat="dd/MM/yyyy"
            locale="en-AU"
            id="startDate"
          />
        </div>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="finishDate">Finishing Date</label>
          <DatePicker
            className={genericInputStyle + " w-40"}
            onChange={(date) => {
              validateAndSetFinishDate(date);
            }}
            selected={finishDate}
            dateFormat="dd/MM/yyyy"
            locale="en-AU"
            id="finishDate"
          />
        </div>
        <div className={radioGroupStyleClass}>
          <label>Is it on full-time or part-time basis?</label>
          <div>
            <input
              className={radioButtonStyleClass}
              type="radio"
              id="fullTime"
              value="FULL_TIME"
              {...register("employmentType")}
            />
            <label htmlFor="fullTime">Full-time</label>
          </div>
          <div>
            <input
              className={radioButtonStyleClass}
              type="radio"
              id="partTime"
              value="PART_TIME"
              {...register("employmentType")}
            />
            <label htmlFor="partTime">Part-time</label>
          </div>
        </div>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="hoursPerWeek">Hours per week</label>
          <input
            className="p-2 border border-cyan-900 focus:outline-none focus:border-teal-400 focus:ring-2 w-10"
            type="number"
            placeholder="1-40"
            id="hoursPerWeek"
            {...register("hoursPerWeek")}
          />
          {errors.hoursPerWeek ? (
            <small className={errorStyleClass}>
              {errors.hoursPerWeek.message}
            </small>
          ) : (
            <small>&#8203;</small>
          )}
        </div>
        <div className="flex justify-center">
          <button
            className="w-40 hover:bg-teal-500 mt-16-md mt-5 h-fit bg-cyan-800 px-5 py-3 rounded-full uppercase text-gray-200"
            type="submit"
          >
            Save
          </button>
        </div>
      </form>
    </div>
  );
};

export default EmployeeForm;
