import { findByText, render, screen, waitFor } from "@testing-library/react";
import { vi } from "vitest";
import userEvent from "@testing-library/user-event";
import "@testing-library/jest-dom";
import LoginPage from "./LoginPage";
import {
  BrowserRouter,
  MemoryRouter,
  Route,
  Routes,
  useNavigate,
} from "react-router-dom";
import { Provider } from "react-redux";
import { store } from "../../app/store";
import * as signInServices from "../../services/SignInServices";
import HomePage from "../HomePage/HomePage";
import { AuthState } from "../../services/APIResponseInterface";
import { AxiosError, AxiosResponse } from "axios";
import { QueryClient, QueryClientProvider } from "react-query";
import * as LoginServices from "../../services/SignInServices";
const axios = require("axios").default;

describe("Login Page Tests", () => {
 
  it("Should display error message if login username and password is incorrect", async () => {
    // const failure = {
    //   authenticated: false,
    //   authorities: [],
    //   name: "random",
    // };
    render(
      <Provider store={store}>
        <LoginPage />
      </Provider>,
      { wrapper: BrowserRouter }
    );

  const mAxiosResponse = {
      
  } as AxiosResponse;

    vi.spyOn(LoginServices,"getCSRF").mockResolvedValue();
    vi.spyOn(LoginServices,"signIn").mockRejectedValue(new Error());
    const username = screen.getByLabelText(/username/i);
    const password = screen.getByLabelText(/password/i);
    const btn = screen.getByText(/sign/i);
    const user = userEvent.setup();
    await user.type(username, "wrong-user");
    await user.type(password, "wrong-password");
    await user.click(btn);
    const err = await screen.findByTestId(/error/i);
    expect(err).toBeInTheDocument();
  });
});
