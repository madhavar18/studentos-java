const BASE_URL = "http://localhost:3000";

export const fetchTasks = async () => {
  const res = await fetch(`${BASE_URL}/tasks/all`);
  return res.json();
};

export const fetchStats = async () => {
  const res = await fetch(`${BASE_URL}/tasks/stats`);
  return res.json();
};

export const createTaskAPI = async (task) => {
  const res = await fetch(`${BASE_URL}/tasks`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(task)
  });

  return res.json();
};

export const completeTaskAPI = async (id) => {
  await fetch(`${BASE_URL}/tasks/${id}/complete`, {
    method: "PATCH"
  });
};

export const deleteTaskAPI = async (id) => {
  await fetch(`${BASE_URL}/tasks/${id}`, {
    method: "DELETE"
  });
};

export const addSubtaskAPI = async (taskId, title) => {
  const res = await fetch(`${BASE_URL}/tasks/${taskId}/subtasks`, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({title})
  });
  return res.json();
};

export const toggleSubtaskAPI = async (taskId, subtaskId, completed) => {
  const res = await fetch(`${BASE_URL}/tasks/${taskId}/subtasks/${subtaskId}`, {
    method: "PATCH",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({completed})
  });
  return res.json();
};

export const deleteSubtaskAPI = async (taskId, subtaskId) => {
  const res = await fetch(`${BASE_URL}/tasks/${taskId}/subtasks/${subtaskId}`, {
    method: "DELETE" 
  });
  return res.json();
};