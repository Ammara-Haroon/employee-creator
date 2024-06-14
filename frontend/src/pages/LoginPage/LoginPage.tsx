import { useQuery, useQueryClient } from "@tanstack/react-query";
import { FormEvent, useRef, useState } from "react";
import { SignInInfo, signIn } from "../../services/SignInServices";
import { hide, show } from "../../features/Notifcations/NotificationSlice";
import ErrMsg from "../../components/ErrMsg/ErrMsg";
import { useDispatch, useSelector } from "react-redux";
import { logout, updateAuthState } from "../../features/Auth/AuthSlice";
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
  const formRef = useRef<HTMLFormElement>(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const genericInputStyle =
    "p-2 border border-cyan-900 focus:outline-none focus:border-teal-400 focus:ring-2";
  const inputStyleClass = genericInputStyle + " max-w-1/2 w-80";

  const inputWrapperStyleClass = "px-3 flex flex-col";
  const sectionHeadingStyleClass =
    "text-xl font-bold text-cyan-900 uppercase pt-7 pb-4";

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(formRef.current);
    if (formRef.current)
      console.log(Object.fromEntries(new FormData(formRef.current).entries()));
    signIn(
      Object.fromEntries(new FormData(formRef.current).entries()) as SignInInfo
    )
      .then((data) => {
        console.log("login response", data);
        dispatch(updateAuthState(data));

        console.log("login");
        if (data.authenticated) {
          console.log("login redirec to dashboard", data);

          dispatch(hide());

          navigate("/dashboard");
        } else {
          dispatch(logout());
          dispatch(show("Login Failed. Bad Username or Password"));
        }
      })
      .catch((e: any) => {
        dispatch(logout());
        dispatch(show("Login Failed. Bad Username or Password"));
      });
  };

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
      </form>
    </div>
  );
};

export default LoginPage;
function dispatch(arg0: any) {
  throw new Error("Function not implemented.");
}
