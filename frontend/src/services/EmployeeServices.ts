import { Employee } from "./APIResponseInterface";

const SERVER_URL = import.meta.env.VITE_APP_SERVER_URL;

export const getAllEmployees = async (): Promise<Employee[]> => {
  const response = await fetch(`${SERVER_URL}/employees`);
  const data = await response.json();
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
  return await response.json();
};
