import { useDispatch } from "react-redux";
import { AuthState } from "./APIResponseInterface";
import axios, { AxiosResponse } from "axios";
import { AuthTokenState, setToken } from "../features/AuthToken/AuthTokenSlice";
import { SERVER_URL } from "./api-config";

export interface SignInInfo {
  username: string;
  password: string;
  //_csrf: string | null;
}
export const signIn = async (
  data: SignInInfo,
  token: AuthTokenState
): Promise<AuthState> => {
  // const headers = {
  //   "Content-Type": "application/json",
  //   [token.headerName]: token.token,
  // };

  // const response = axios.get(SERVER_URL + "/login", {
  //   method: "POST",
  //   body: JSON.stringify(data),
  //   headers: {
  //     "Content-Type": "application/json",
  //   },
  // });

  const response = await axios.post(SERVER_URL + "/login", data);
  if (response.status === 403) {
    throw new Error("Access Denied");
  } else if (response.status !== 200) {
    throw new Error("Couldn't sign in");
  }
  if (!response.data.authenticated) {
    throw new Error("Access Denied. Bad Username or Password");
  }
  console.log(response.data);
  return response.data;
};

export const getCSRF = async () => {
  const response = await fetch(SERVER_URL + "/csrf");
  const data = await response.json();
  const csrf = data.token;
  console.log(data);
  return data;
};
