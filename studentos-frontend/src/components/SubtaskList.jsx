import SubtaskItem from "./SubtaskItem";
import SubtaskForm from "./SubtaskForm";

function SubtaskList({ task, addSubtask, toggleSubtask, deleteSubtask }) {
  return (
    <div className="mt-2">

      {task.subtasks?.map((sub) => (
        <SubtaskItem
          key={sub._id}
          subtask={sub}
          onToggle={(s) => toggleSubtask(task._id, s)}
          onDelete={(s) => deleteSubtask(task._id, s)}
        />
      ))}

      <SubtaskForm onAdd={(title) => addSubtask(task._id, title)} />

    </div>
  );
}

export default SubtaskList;