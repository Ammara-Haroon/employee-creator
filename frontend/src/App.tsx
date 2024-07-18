import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomePage from "./pages/HomePage/HomePage";
import EmployeeFormPage from "./pages/EmployeeFormPage/EmployeeFormPage";
import { QueryClient, QueryClientProvider } from "react-query";
import LoginPage from "./pages/LoginPage/LoginPage";

// Create a client
const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/dashboard" element={<HomePage />} />
          <Route path="/edit" element={<EmployeeFormPage />} />
          <Route path="/add" element={<EmployeeFormPage />} />
        </Routes>
      </BrowserRouter>
    </QueryClientProvider>
  );
}

export default App;
