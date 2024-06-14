import { createSlice } from "@reduxjs/toolkit";

export interface QueryParamsState {
  currentPage: number;
  totalNumberOfPages: number;
}

const initialState: QueryParamsState = {
  currentPage: 0,
  totalNumberOfPages: 1,
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
  },
});

// Action creators are generated for each case reducer function
export const { setPage, nextPage, previousPage, updateTotalNumberOfPages } =
  queryParamsSlice.actions;

export default queryParamsSlice.reducer;
