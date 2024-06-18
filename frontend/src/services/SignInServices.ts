import { useDispatch } from "react-redux";
import { AuthState } from "./APIResponseInterface";
import { AuthTokenState, setToken } from "../features/AuthToken/AuthTokenSlice";

const SERVER_URL = import.meta.env.VITE_APP_SERVER_URL;

export interface SignInInfo {
  username: string;
  password: string;
  //_csrf: string | null;
}
export const signIn = async (
  data: SignInInfo,
  token: AuthTokenState
): Promise<AuthState> => {
  console.log("log in with ", data, "token:", token);

  // const headers = {
  //   "Content-Type": "application/json",
  //   [token.headerName]: token.token,
  // };
  const response = await fetch(SERVER_URL + "/login", {
    method: "POST",
    body: JSON.stringify(data),
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (response.status === 403) {
    throw new Error("Access Denied");
  } else if (!response.ok) {
    throw new Error("Couldn't sign in");
  }
  const res = await response.json();
  if (!res.authenticated) {
    throw new Error("Access Denied. Bad Username or Password");
  }
  console.log("response.status all good", res);

  return res;
};

export const getCSRF = async () => {
  const response = await fetch(SERVER_URL + "/csrf");
  const data = await response.json();
  const csrf = data.token;
  console.log(data);
  return data;
};
