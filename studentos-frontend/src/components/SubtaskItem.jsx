function SubtaskItem({ subtask, onToggle, onDelete }) {
  return (
    <div className="d-flex justify-content-between align-items-center mt-1">

      <div>
        <input
          type="checkbox"
          checked={subtask.completed}
          onChange={() => onToggle(subtask)}
        />

        <span className={`ms-2 ${subtask.completed ? "text-decoration-line-through" : ""}`}>
          {subtask.title}
        </span>
      </div>

      <button
        className="btn btn-sm btn-danger"
        onClick={() => onDelete(subtask)}
      >
        x
      </button>
    </div>
  );
}

export default SubtaskItem;