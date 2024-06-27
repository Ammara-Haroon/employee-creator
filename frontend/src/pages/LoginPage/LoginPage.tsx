import { useQuery, useQueryClient } from "@tanstack/react-query";
import { FormEvent, useEffect, useRef, useState } from "react";
import { SignInInfo, getCSRF, signIn } from "../../services/SignInServices";
import { hide, show } from "../../features/Notifcations/NotificationSlice";
import ErrMsg from "../../components/ErrMsg/ErrMsg";
import { useDispatch, useSelector } from "react-redux";
import { logout, updateAuthState } from "../../features/Auth/AuthSlice";
import { useNavigate } from "react-router-dom";
import { resetToken, setToken } from "../../features/AuthToken/AuthTokenSlice";
import { RootState } from "../../app/store";
import { resetFilterParams } from "../../features/QueryParams/QueryParamsSlice";
import { useCookies } from 'react-cookie';
import axios from "axios";

const LoginPage = () => {
const [cookies, setCookie, removeCookie] = useCookies(['XSRF-TOKEN']);
  const formRef = useRef<HTMLFormElement>(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const genericInputStyle =
    "p-2 border border-cyan-900 focus:outline-none focus:border-teal-400 focus:ring-2";
  const inputStyleClass = genericInputStyle + " max-w-1/2 w-80";

  const inputWrapperStyleClass = "px-3 flex flex-col";
  const { token } = useSelector((state: RootState) => state.authToken);

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    dispatch(logout());
    dispatch(resetFilterParams());
    dispatch(resetToken());
    dispatch(hide());

  //   axios.defaults.headers.common['X-XSRF-TOKEN'] = cookies['XSRF-TOKEN'];
  //   axios.defaults.withCredentials=true;
  //  axios.delete('http://localhost:8080/employees/2').then(()=>console.log("here"));
    
getCSRF(cookies['XSRF-TOKEN']).then(() => {
    //dispatch(setToken({token:cookies['XSRF-TOKEN']}));
      signIn(
        Object.fromEntries(
          new FormData(formRef.current).entries()
        ) as SignInInfo,
        cookies['XSRF-TOKEN']
      )
      .then((data) => {
        dispatch(updateAuthState(data));
        if (data.authenticated) {
          dispatch(hide());
          navigate("/dashboard");
        } else {
          dispatch(logout());
          dispatch(show("Login Failed. Bad Username or Password"));
        }
      })
      .catch((e: any) => {
        dispatch(logout());
        dispatch(show(e.message)); //"Login Failed. Bad Username or Password"));
      });
  })};

  return (
    <div className="bg-gray-100 flex flex-col justify-center items-center border border-black w-screen h-screen">
      <form
        className="px-20 pb-10 text-slate-800 font-semibold  bg-gray-100 border border-cyan-900 ring-8 rounded-lg"
        ref={formRef}
        onSubmit={handleSubmit}
      >
        <h1 className="py-10 text-center  text-2xl font-bold text-cyan-900 uppercase">
          Employee Creator
        </h1>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="userName">Username: </label>
          <input
            className={inputStyleClass}
            type="text"
            name="username"
            id="userName"
            required
          />
        </div>
        <div className={inputWrapperStyleClass}>
          <label htmlFor="password">Password: </label>
          <input
            className={inputStyleClass}
            type="password"
            name="password"
            id="password"
            required
          />
        </div>

        <ErrMsg />
        <div className="flex justify-center">
          <button
            className="w-40 hover:bg-teal-500 mt-16-md mt-5 h-fit bg-cyan-800 px-5 py-3 rounded-full uppercase text-gray-200"
            type="submit"
          >
            Sign In
          </button>
        </div>

        <input
          id="_csrf"
          name="_csrf"
          type="hidden"
          value={token || ""}
        ></input>
      </form>
    </div>
  );
};

export default LoginPage;
