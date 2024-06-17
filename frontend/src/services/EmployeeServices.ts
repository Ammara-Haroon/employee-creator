import { QueryParamsState } from "../features/QueryParams/QueryParamsSlice";
import { Employee, EmployeePageResponse } from "./APIResponseInterface";

const SERVER_URL = import.meta.env.VITE_APP_SERVER_URL;

// export const getAllEmployees = async (
//   currentPage: number = 0
// ): Promise<EmployeePageResponse> => {
//   console.log(currentPage);
//   const response = await fetch(`${SERVER_URL}/employees?page=${currentPage}`);
//   const data = await response.json();
//   console.log(data);
//   data.content = data.content.map((entry: any) => ({
//     ...entry,
//     startDate: new Date(entry.startDate),
//     finishDate: entry.finishDate && new Date(entry.finishDate),
//   }));
//   return data;
// };

export const getQueryParamsString = (
  queryParams: Partial<QueryParamsState>
): string => {
  console.log(queryParams);
  if (
    (!queryParams.admin && !queryParams.finance && !queryParams.it) ||
    (!queryParams.fullTime && !queryParams.partTime) ||
    (!queryParams.contract && !queryParams.permanent)
  ) {
    return "";
  }
  const searchStr = queryParams.search?.trim();
  let queryStr = `page=${queryParams.currentPage}&sort=${queryParams.sort}&name=${searchStr}`;
  queryStr += "&department=";
  if (queryParams.admin) {
    queryStr += `${queryParams.admin},`;
  }
  if (queryParams.finance) {
    queryStr += `${queryParams.finance},`;
  }
  if (queryParams.it) {
    queryStr += `${queryParams.it},`;
  }
  queryStr = queryStr.substring(0, queryStr.length - 1);
  queryStr += "&employmentType=";
  if (queryParams.fullTime) {
    queryStr += `${queryParams.fullTime},`;
  }
  if (queryParams.partTime) {
    queryStr += `${queryParams.partTime},`;
  }
  queryStr = queryStr.substring(0, queryStr.length - 1);
  queryStr += "&contractType=";
  if (queryParams.contract) {
    queryStr += `${queryParams.contract},`;
  }
  if (queryParams.permanent) {
    queryStr += `${queryParams.permanent},`;
  }
  queryStr = queryStr.substring(0, queryStr.length - 1);
  return queryStr;
};
// export const getAllEmployees = async (
//   filterParams: FilterParams,
//   currentPage: number = 0
// ): Promise<EmployeePageResponse> => {
//   console.log(filterParams, currentPage);
//   const filterParamsString = getFilterParamsString(filterParams);
//   if (filterParamsString.length === 0)
//     throw new Error("Nothing found with these filters");
//   const response = await fetch(
//     `${SERVER_URL}/employees?page=${currentPage}&${filterParamsString}`
//   );
//   const data = await response.json();
//   console.log(data);
//   data.content = data.content.map((entry: any) => ({
//     ...entry,
//     startDate: new Date(entry.startDate),
//     finishDate: entry.finishDate && new Date(entry.finishDate),
//   }));
//   return data;
// };

export const getAllEmployees = async (
  queryParams: Partial<QueryParamsState>
): Promise<EmployeePageResponse> => {
  console.log("queryParams:", queryParams);
  const queryParamsString = getQueryParamsString(queryParams);
  console.log("queryParams string:", queryParamsString);
  if (queryParamsString.length === 0)
    throw new Error("Nothing found with these filters");
  const response = await fetch(`${SERVER_URL}/employees?${queryParamsString}`);
  const data = await response.json();
  console.log(data);
  data.content = data.content.map((entry: any) => ({
    ...entry,
    startDate: new Date(entry.startDate),
    finishDate: entry.finishDate && new Date(entry.finishDate),
  }));
  return data;
};

export const createEmployee = async (data: Employee): Promise<Employee> => {
  const response = await fetch(SERVER_URL + "/employees", {
    method: "POST",
    body: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
  });
  return await response.json();
};

export const deleteEmployee = async (id: number): Promise<void> => {
  await fetch(`${SERVER_URL}/employees/${id}`, {
    method: "DELETE",
  });
};

export const updateEmployee = async (data: Employee): Promise<Employee> => {
  console.log("data chabge0", data);

  const response = await fetch(`${SERVER_URL}/employees/${data.id}`, {
    method: "PATCH",
    body: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
  });
  if (!response.ok) console.log(response.text);
  return await response.json();
};
