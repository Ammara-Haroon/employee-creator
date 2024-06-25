import { useDispatch } from "react-redux";
import { AuthState } from "./APIResponseInterface";
import axios, { AxiosResponse } from "axios";
import { AuthTokenState, setToken } from "../features/AuthToken/AuthTokenSlice";
import { SERVER_URL } from "./api-config";

axios.defaults.withCredentials = true;
axios.defaults.xsrfHeaderName = "X-XSRF-TOKEN";
axios.defaults.xsrfCookieName = "XSRF-TOKEN";

export interface SignInInfo {
  username: string;
  password: string;
  _csrf: string | null;
}
export const signIn = async (
  data: SignInInfo,
  token: AuthTokenState
): Promise<AuthState> => {
  console.log(token);
  const headers={ 'X-XSRF-TOKEN': token};  

  //const response = await axios.post(SERVER_URL + "/login",{credentials:"include",headers});
  const response = await axios.post(SERVER_URL + "/login",data);
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
    //await axios.get(SERVER_URL+'/csrf');
};
