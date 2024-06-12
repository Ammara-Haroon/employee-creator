import React, { useState } from "react";
import { Employee } from "../../services/APIResponseInterface";
import { deleteEmployee } from "../../services/EmployeeServices";
import {
  faAdd,
  faAddressBook,
  faAddressCard,
  faCalendar,
  faClock,
  faDeleteLeft,
  faDoorClosed,
  faEnvelope,
  faPencil,
  faPhone,
  faSubtract,
  faTrash,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { toTitleCase } from "../../services/utility";
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import { faExclamation } from "@fortawesome/free-solid-svg-icons/faExclamation";
dayjs.extend(relativeTime);
dayjs().format();
dayjs.extend(relativeTime);

interface IEmployeeCardProps {
  employee: Employee;
  handleEdit(employee: Employee): unknown;
  handleDelete(id: number): unknown;
}

const EmployeeCard = ({
  employee,
  handleEdit,
  handleDelete,
}: IEmployeeCardProps) => {
  const [hideDetails, setHideDetails] = useState(true);
  const hasAlreadyLeft =
    employee.finishDate && employee.finishDate < new Date();
  console.log(hasAlreadyLeft, employee.finishDate);
  return (
    <>
      <tr
        className={
          (hasAlreadyLeft ? "text-slate-500" : "text-slate-800") +
          " font-semibold w-full text-ellipsis"
        }
      >
        <td className="text-ellipsis overflow-hidden text-nowrap">
          <button className="p-2" onClick={() => setHideDetails(!hideDetails)}>
            {hideDetails ? (
              <FontAwesomeIcon icon={faAdd} />
            ) : (
              <FontAwesomeIcon icon={faSubtract} />
            )}
          </button>
          {employee.firstName} {employee.middleName} {employee.lastName}
        </td>
        <td className="text-center text-ellipsis overflow-hidden text-nowrap  hidden sm:table-cell">
          {toTitleCase(employee.contractType)}
        </td>
        <td className=" hidden sm:table-cell text-center">
          {toTitleCase(employee.employmentType)}
        </td>

        <td className="text-right text-ellipsis overflow-hidden text-nowrap ">
          <button className="p-2" onClick={() => handleEdit(employee)}>
            <FontAwesomeIcon icon={faPencil} />
          </button>
          <button
            className="px-3 py-2"
            onClick={() => handleDelete(employee.id)}
          >
            {" "}
            <FontAwesomeIcon icon={faTrash} />
          </button>
        </td>
      </tr>
      {!hideDetails && (
        <tr className=" text-slate-800 indent-10 text-sm">
          <p className="font-semibold text-ellipsis overflow-hidden text-nowrap">
            {toTitleCase(employee.employmentType) +
              " " +
              toTitleCase(employee.contractType)}
          </p>
          <p className=" text-ellipsis overflow-hidden text-nowrap">
            <FontAwesomeIcon
              className="px-1 text-ellipsis overflow-hidden text-nowrap"
              icon={faClock}
            />
            {employee.hoursPerWeek} hrs/week
          </p>
          <p className=" text-ellipsis overflow-hidden text-nowrap">
            <FontAwesomeIcon
              className="px-1 text-ellipsis overflow-hidden text-nowrap"
              icon={faPhone}
            />
            {employee.mobileNumber}
          </p>
          <p className="text-ellipsis overflow-hidden text-nowrap">
            <FontAwesomeIcon className="px-1" icon={faEnvelope} />
            {employee.email}
          </p>
          <p className=" text-ellipsis overflow-hidden text-nowrap">
            <FontAwesomeIcon className="px-1" icon={faAddressBook} />
            {employee.address}
          </p>
          <p className=" text-ellipsis overflow-hidden text-nowrap">
            <FontAwesomeIcon className="px-1" icon={faCalendar} />
            <span className="hidden md:inline">
              Started {dayjs().to(employee.startDate)} on{" "}
            </span>
            <span className="md:hidden">Start: </span>
            {dayjs(employee.startDate).format("DD/MM/YYYY")}
          </p>
          {employee.finishDate && (
            <p
              className={
                "text-red-700 text-ellipsis overflow-hidden text-nowrap"
              }
            >
              <FontAwesomeIcon className="px-1" icon={faExclamation} />
              Finish: {dayjs(employee.finishDate).format("DD/MM/YYYY")}
            </p>
          )}
        </tr>
      )}
    </>
  );
};

export default EmployeeCard;
