import { useState } from "react";

function SubtaskForm({ onAdd }) {
  const [title, setTitle] = useState("");

  const handleAdd = () => {
    if (!title.trim()) return;
    onAdd(title);
    setTitle("");
  };

  return (
    <div className="mt-2 d-flex">
      <input
        className="form-control me-2"
        placeholder="Add subtask"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <button className="btn btn-sm btn-primary" onClick={handleAdd}>
        Add
      </button>
    </div>
  );
}

export default SubtaskForm;