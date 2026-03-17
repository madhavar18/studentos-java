import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import TaskPage from "../pages/TaskPage";
import Dashboard from "../components/Dashboard";

const AppRouter = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<TaskPage />} />
        <Route path="/tasks" element={<TaskPage />} />
      </Routes>
    </Router>
  );
};

export default AppRouter;