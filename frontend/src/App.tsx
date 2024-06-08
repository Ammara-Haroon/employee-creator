import { useEffect, useState } from "react";
import "./App.css";
import { getAllEmployees } from "./services/EmployeeServices";
import { Employee } from "./services/APIResponseInterface";
import EmployeeForm from "./components/EmployeeForm/EmployeeForm";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage";
import EmployeeFormPage from "./pages/EmployeeFormPage/EmployeeFormPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/employee-creator" element={<HomePage />} />
        <Route
          path="/employee-creator/eidt/:id"
          element={<EmployeeFormPage />}
        />
        <Route path="/employee-creator/add" element={<EmployeeFormPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
