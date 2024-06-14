import { useEffect, useState } from "react";
import "./App.css";
import { getAllEmployees } from "./services/EmployeeServices";
import { AuthState, Employee } from "./services/APIResponseInterface";
import EmployeeForm from "./components/EmployeeForm/EmployeeForm";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage";
import EmployeeFormPage from "./pages/EmployeeFormPage/EmployeeFormPage";
import { QueryClient, QueryClientProvider } from "react-query";
import LoginPage from "./pages/LoginPage/LoginPage";
import { useSelector } from "react-redux";
import { useAuth } from "./features/Auth/AuthSlice";

// Create a client
const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/dashboard" element={<HomePage />} />
          <Route path="/edit" element={<EmployeeFormPage />} />
          <Route path="/add" element={<EmployeeFormPage />} />
        </Routes>
      </BrowserRouter>
    </QueryClientProvider>
  );
}

export default App;
