import { FormEvent, MouseEvent, useRef } from "react";
import { getAllEmployees } from "../../services/EmployeeServices";
import { useQuery } from "react-query";
import LoadingSpinner from "../../components/LoadingSpinner/LoadingSpinner";
import ErrMsg from "../../components/ErrMsg/ErrMsg";
import { useNavigate } from "react-router-dom";
import EmployeesList from "../../components/EmployeesList/EmployeesList";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faAdd,
  faArrowLeft,
  faArrowRight,
} from "@fortawesome/free-solid-svg-icons";
import { useDispatch, useSelector } from "react-redux";
import { show } from "../../features/Notifcations/NotificationSlice";

import { RootState } from "../../app/store";
import {
  nextPage,
  previousPage,
  setPage,
  updateFilterParams,
  updateTotalNumberOfPages,
} from "../../features/QueryParams/QueryParamsSlice";
import { isAdmin } from "../../features/Auth/AuthSlice";

const HomePage = () => {
  const inputStyleClass =
    "p-1 border border-cyan-900 focus:outline-none focus:border-teal-400 focus:ring-2  max-w-1/2 w-80";

  const inputWrapperStyleClass = "flex flex-col justify-center items-center";
  const sectionHeadingStyleClass =
    "px-2 text-md font-bold text-cyan-900 uppercase";
  const checkBoxGroupStyleClass = "px-1 py-2";
  const checkBoxStyleClass = "m-1";
  const formRef = useRef<HTMLFormElement>(null);
  const arr = new Array(7).fill(0).map((el, index) => index);

  const { authenticated } = useSelector((state: RootState) => state.auth);
  const roleAdmin = isAdmin();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  if (!authenticated) {
    dispatch(show("Unauthorised Access"));
    return <ErrMsg />;
  }

  const queryParams = useSelector((state: RootState) => state.queryParams);

  const { isLoading, isError, data } = useQuery({
    queryKey: ["employees", queryParams],
    queryFn: () => getAllEmployees(queryParams),
    onError: (err: any) => dispatch(show(err.message)),
    retry: false,
    keepPreviousData: true,
  });
  dispatch(updateTotalNumberOfPages(data?.totalPages));

  const handleClick = (): void => {
    navigate("/add");
  };

  const handleSubmit = (event: FormEvent) => {
    event.preventDefault();
    if (formRef?.current)
      dispatch(
        updateFilterParams(
          Object.fromEntries(
            new FormData(formRef.current || undefined).entries()
          )
        )
      );
  };
  const resetForm = (event: MouseEvent) => {
    event.preventDefault();
    formRef.current?.reset();
    dispatch(
      updateFilterParams(
        Object.fromEntries(new FormData(formRef.current || undefined).entries())
      )
    );
  };

  return (
    <div className="bg-gray-200">
      <div className="py-5 px-2  flex justify-between flex-wrap">
        <h1 className="text-2xl font-bold text-cyan-900 uppercase ">
          Employees Dashboard
        </h1>
        {roleAdmin && (
          <button
            className="hover:bg-teal-500 mt-16-md mt-5 h-fit text-right bg-cyan-800 px-3 py-2 rounded-full uppercase text-gray-200"
            onClick={handleClick}
            title="Add New Employee"
          >
            <FontAwesomeIcon className="px-1" icon={faAdd} />
            Add New Employee
          </button>
        )}
      </div>
      {isLoading ? <span> Loading...</span> : null}{" "}
      {isLoading && <LoadingSpinner />}
      <ErrMsg />
      <form
        ref={formRef}
        onSubmit={handleSubmit}
        className="border border-slate-300"
      >
        <h6 className={sectionHeadingStyleClass}>Filters:</h6>
        <div className={inputWrapperStyleClass}>
          <label
            htmlFor="search"
            className="text-cyan-900 text-xs uppercase font-bold"
          >
            Search For :
          </label>
          <input
            className={inputStyleClass}
            id="search"
            type="text"
            name="search"
            minLength={1}
          />
        </div>
        <div className="text-sm text-slate-800 flex justify-around text-right">
          <div className={checkBoxGroupStyleClass}>
            <div className={checkBoxStyleClass}>
              <label htmlFor="admin">Admin</label>
              <input
                className="mx-1"
                defaultChecked={true}
                type="checkbox"
                id="admin"
                name="admin"
                value="ADMIN"
              />
            </div>
            <div className={checkBoxStyleClass}>
              <label htmlFor="finance">Finance</label>
              <input
                className="mx-1"
                defaultChecked={true}
                type="checkbox"
                id="finance"
                name="finance"
                value="FINANCE"
              />
            </div>
            <div className={checkBoxStyleClass}>
              <label htmlFor="it">I.T.</label>
              <input
                className="mx-1"
                type="checkbox"
                defaultChecked={true}
                id="it"
                name="it"
                value="IT"
              />
            </div>
          </div>
          <div className={checkBoxGroupStyleClass}>
            <div className={checkBoxStyleClass}>
              <label htmlFor="permanent">Permanent</label>
              <input
                className="mx-1"
                defaultChecked={true}
                type="checkbox"
                id="permanent"
                name="permanent"
                value="PERMANENT"
              />
            </div>
            <div className={checkBoxStyleClass}>
              <label htmlFor="contract">Contract</label>
              <input
                className="mx-1"
                defaultChecked={true}
                type="checkbox"
                id="contract"
                name="contract"
                value="CONTRACT"
              />
            </div>
          </div>
          <div className={checkBoxGroupStyleClass}>
            <div className={checkBoxStyleClass}>
              <label htmlFor="partTime">Part-Time</label>
              <input
                className="mx-1"
                defaultChecked={true}
                type="checkbox"
                id="partTime"
                name="partTime"
                value="PART_TIME"
              />
            </div>
            <div className={checkBoxStyleClass}>
              <label htmlFor="fullTime">Full-Time</label>
              <input
                className="mx-1"
                type="checkbox"
                defaultChecked={true}
                id="fullTime"
                name="fullTime"
                value="FULL_TIME"
              />
            </div>
          </div>
          <div className={inputWrapperStyleClass}>
            <button
              type="submit"
              className="hover:bg-teal-500 m-1 h-fit bg-cyan-800 px-3 py-1 rounded-full uppercase text-gray-200  w-20 text-center"
            >
              Search
            </button>
            <button
              onClick={resetForm}
              className="hover:bg-teal-500  m-1 h-fit bg-cyan-800 text-center px-3 py-1 rounded-full uppercase text-gray-200 w-20"
            >
              Reset
            </button>
          </div>
        </div>
      </form>
      {!isLoading && !isError && data && (
        <EmployeesList employeesList={data.content} />
      )}
      {data?.totalPages !== undefined && data?.totalPages > 1 && (
        <div className="flex justify-center hover:text-">
          <button
            className="text-cyan-900 hover:text-teal-500  disabled:text-slate-700"
            onClick={() => dispatch(previousPage())}
            disabled={data?.first}
          >
            <FontAwesomeIcon icon={faArrowLeft} />
          </button>
          <select
            className="text-cyan-900 font-semibold "
            value={queryParams.currentPage}
            onChange={(e) => {
              dispatch(setPage(parseInt(e.currentTarget.value)));
            }}
            name=""
            id=""
          >
            {new Array(data?.totalPages).fill(0).map((el, index) => (
              <option key={index} value={index}>
                {index + 1}
              </option>
            ))}
          </select>
          <button
            className="text-cyan-900  hover:text-teal-500 disabled:text-slate-700"
            onClick={() => dispatch(nextPage())}
            // Disable the Next Page button until we know a next page is available
            disabled={data?.last}
          >
            <FontAwesomeIcon icon={faArrowRight} />
          </button>
        </div>
      )}
    </div>
  );
};

export default HomePage;
