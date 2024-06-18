import { createSlice } from "@reduxjs/toolkit";

export interface AuthTokenState {
  token: string | null;
  parameterName: string | null;
  headerName: string | null;
}

const initialState: AuthTokenState = {
  token: null,
  parameterName: null,
  headerName: null,
};

export const authTokenSlice = createSlice({
  name: "authToken",
  initialState,
  reducers: {
    setToken: (state, payload) => {
      state.token = payload.payload.token;
      state.parameterName = payload.payload.parameterName;
      state.headerName = payload.payload.headerName;
    },
  },
});

// Action creators are generated for each case reducer function
export const { setToken } = authTokenSlice.actions;

export default authTokenSlice.reducer;
