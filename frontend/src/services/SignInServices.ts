import { useDispatch } from "react-redux";
import { AuthState } from "./APIResponseInterface";
import axios, { AxiosError, AxiosResponse } from "axios";
import { AuthTokenState, setToken } from "../features/AuthToken/AuthTokenSlice";
import { SERVER_URL } from "./api-config";

 
export interface SignInInfo {
  username: string;
  password: string;
  _csrf: string | null;
}
export const signIn = async (
  data: SignInInfo
): Promise<AuthState> => {
  let response;
try{
  response = await axios.post(SERVER_URL + "/login",data);
 
}catch(error:any){
   console.log(error);
  if (error?.response?.status === 403) {
    throw new Error("Access Denied");
  } else if (error?.response?.status !== 200) {
    throw new Error("Couldn't sign in");
  }
}
if (!response?.data.authenticated) {
  throw new Error("Access Denied. Bad Username or Password");
}
  return response.data;
 };

export const getCSRF = async (token:any) => {
    await axios.get(SERVER_URL+'/csrf');
    axios.defaults.headers.common['X-XSRF-TOKEN'] = token;
    axios.defaults.withCredentials=true;
};
