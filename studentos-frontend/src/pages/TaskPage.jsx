import { useEffect, useState } from "react";
import TaskForm from "../components/TaskForm";
import TaskList from "../components/TaskList";
import Dashboard from "../components/Dashboard";
import FilterBar from "../components/FilterBar";

import {
  fetchTasks,
  fetchStats,
  createTaskAPI,
  completeTaskAPI,
  deleteTaskAPI,
} from "../services/api";

const TaskPage = () => {
  const [tasks, setTasks] = useState([]);
  const [stats, setStats] = useState({});
  const [filter, setFilter] = useState("all");

  const [title, setTitle] = useState("");
  const [type, setType] = useState("assignment");
  const [deadline, setDeadLine] = useState("");

  useEffect(() => {
    fetchTasks().then(setTasks);
    fetchStats().then(setStats);
  }, []);

  const createTask = async () => {
    if (!title.trim()) return alert("Title is required");

    const data = await createTaskAPI({
      title,
      type,
      deadline,
    });

    setTasks((prev) => [...prev, data]);

    setTitle("");
    setType("assignment");
    setDeadLine("");
  };

  const completeTask = async (id) => {
    await completeTaskAPI(id);

    setTasks((prev) =>
      prev.map((task) =>
        task._id === id ? { ...task, status: "completed" } : task
      )
    );
  };

  const deleteTask = async (id) => {
    await deleteTaskAPI(id);

    setTasks((prev) =>
      prev.filter((task) => task._id !== id)
    );
  };

  return (
    <div className="container mt-4">
      <h1 className="text-center mb-4">StudentOS</h1>

      <Dashboard stats={stats} />

      <TaskForm
        title={title}
        setTitle={setTitle}
        type={type}
        setType={setType}
        deadline={deadline}
        setDeadLine={setDeadLine}
        createTask={createTask}
      />

      <FilterBar setFilter={setFilter} />

      <TaskList
        tasks={tasks}
        filter={filter}
        completeTask={completeTask}
        deleteTask={deleteTask}
      />
    </div>
  );
};

export default TaskPage;