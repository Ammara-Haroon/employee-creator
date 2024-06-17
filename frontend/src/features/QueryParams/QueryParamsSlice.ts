import { createSlice } from "@reduxjs/toolkit";
import { SortType } from "../../services/APIResponseInterface";

export interface QueryParamsState {
  currentPage: number;
  totalNumberOfPages: number;
  admin: string;
  contract: string;
  finance: string;
  fullTime: string;
  it: string;
  partTime: string;
  permanent: string;
  search: string;
  sort: SortType;
}

const initialState: QueryParamsState = {
  currentPage: 0,
  totalNumberOfPages: 1,
  admin: "ADMIN",
  contract: "CONTRACT",
  finance: "FINANCE",
  fullTime: "FULL_TIME",
  it: "IT",
  partTime: "PART_TIME",
  permanent: "PERMANENT",
  search: "",
  sort: SortType.ASC,
};
export const queryParamsSlice = createSlice({
  name: "queryParams",
  initialState,
  reducers: {
    setPage: (state, payload) => {
      state.currentPage = payload.payload;
      console.log(state, payload);
    },
    nextPage: (state) => {
      state.currentPage =
        (state.currentPage + 1) % (state.totalNumberOfPages + 1);
    },
    previousPage: (state) => {
      state.currentPage = Math.max(1, state.currentPage - 1);
    },
    updateTotalNumberOfPages: (state, payload) => {
      state.totalNumberOfPages = payload.payload;
    },
    updateFilterParams: (state, payload) => {
      state.admin = payload.payload.admin;
      state.contract = payload.payload.contract;
      state.finance = payload.payload.finance;
      state.fullTime = payload.payload.fullTime;
      state.it = payload.payload.it;
      state.partTime = payload.payload.partTime;
      state.permanent = payload.payload.permanent;
      state.search = payload.payload.search;
      state.sort = payload.payload.sort;
    },
    toggleSort: (state) => {
      if (state.sort === SortType.DESC) state.sort = SortType.ASC;
      else state.sort = SortType.DESC;
    },
  },
});

// Action creators are generated for each case reducer function
export const {
  setPage,
  nextPage,
  previousPage,
  updateTotalNumberOfPages,
  updateFilterParams,
  toggleSort,
} = queryParamsSlice.actions;

export default queryParamsSlice.reducer;
