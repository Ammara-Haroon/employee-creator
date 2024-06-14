import { AuthState } from "./APIResponseInterface";

const SERVER_URL = import.meta.env.VITE_APP_SERVER_URL;

export interface SignInInfo {
  username: string;
  password: string;
}
export const signIn = async (data: SignInInfo): Promise<AuthState> => {
  console.log("log in with ", data);
  const response = await fetch(SERVER_URL + "/login", {
    method: "POST",
    body: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
  });

  console.log("response.status", response.status);
  if (response.status === 403) {
    throw new Error("Access Denied");
  } else if (!response.ok) {
    throw new Error("Couldn't sign in");
  }
  const res = await response.json();
  if (!res.authenticated) {
    throw new Error("Access Denied. Bad Username or Password");
  }
  console.log("response.status all good");

  return res;
};
