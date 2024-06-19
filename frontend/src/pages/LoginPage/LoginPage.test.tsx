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
import { AxiosResponse } from "axios";
import { QueryClient, QueryClientProvider } from "react-query";

const axios = require("axios").default;

describe("Login Page Tests", () => {
  // it("", async () => {
  //   const success: AuthState = {
  //     authenticated: true,
  //     authorities: ["ROLE_ADMIN"],
  //     name: "user",
  //   };
  //   const mAxiosResponse = {
  //     data: success,
  //   } as AxiosResponse;

  //   vi.spyOn(axios, "post").mockResolvedValue(mAxiosResponse);

  //   render(
  //     <QueryClientProvider client={new QueryClient()}>
  //       <Provider store={store}>
  //         <Routes>
  //           <Route path="/dashboard" element={<HomePage />} />
  //           <Route path="/" element={<LoginPage />} />
  //         </Routes>
  //       </Provider>
  //     </QueryClientProvider>,
  //     { wrapper: BrowserRouter }
  //   );
  //   const username = screen.getByLabelText(/username/i);
  //   const password = screen.getByLabelText(/password/i);
  //   const btn = screen.getByText(/sign/i);
  //   const user = userEvent.setup();
  //   await user.type(username, "user");
  //   await user.type(password, "password");
  //   await user.click(btn);
  //   screen.debug();
  //   const heading = await screen.findByText(/.*dashboard.*/i);
  //   expect(heading).toBeInTheDocument();
  // });

  it("Should display error message if login username and password is incorrect", async () => {
    const failure: AuthState = {
      authenticated: false,
      authorities: [],
      name: "random",
    };
    render(
      <Provider store={store}>
        <LoginPage />
      </Provider>,
      { wrapper: BrowserRouter }
    );

    const mAxiosResponse = {
      data: failure,
    } as AxiosResponse;

    vi.spyOn(axios, "post").mockResolvedValue(mAxiosResponse);

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
