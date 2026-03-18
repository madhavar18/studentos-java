const express = require("express");
const router = express.Router();
const task = require("../models/Task");
const Task = require("../models/Task");

router.get("/all", async (req, res) => {
    try {
        const tasks = await Task.find();
        res.json(tasks);
    }
    catch(err) {
        res.send("Error fetching tasks", err);
    }
});

router.post("/", async (req, res) => {
    try {
        const newTask = new Task({
            title: req.body.title,
            type: req.body.type,
            deadline: req.body.deadline
        });

        const savedTask = await newTask.save();
        res.json(savedTask);
    }
    catch(err) {
        res.status(500).send("Error saving task", err);
    }
});

router.put("/:id", async (req, res) => {
    try {
        console.log("id: ", req.params.id);
        console.log("body: ", req.body);

        const updatedTask = await Task.findByIdAndUpdate(
            req.params.id, 
            req.body,
            {returnDocument: 'after'}
        );
        res.json(updatedTask);
    }
    catch(err) {
        console.log(err);
        res.send("Update failed");
    }
});

router.delete("/:id", async (req, res) => {
    try {
        await Task.findByIdAndDelete(req.params.id);
        res.send("Task deleted successfully");
    }
    catch(err) {
        console.log(err);
        res.send("Error deleting task");
    }
});

router.patch("/:id/complete", async (req, res) => {
    try {
        const updatedTask = await Task.findByIdAndUpdate(
            req.params.id,
            { status: "completed" },
            { returnDocument: 'after' }
        );

        if(!updatedTask) {
            return res.status(404).send("Task not found");
        }

        res.json(updatedTask);
    } catch(err) {
        console.log(err);
        return res.status(500).send("Error updating task");
    }
});

router.get("/stats", async (req, res) => {
    try {
        const [
            totalTasks,
            completedTasks,
            pendingTasks,
            assignments,
            projects,
            exams
        ] = await Promise.all([
            Task.countDocuments(),
            Task.countDocuments({ status: "completed" }),
            Task.countDocuments({ status: "pending" }),
            Task.countDocuments({ type: "assignment" }),
            Task.countDocuments({ type: "project" }),
            Task.countDocuments({ type: "exam" })
        ]);

        res.json({
            totalTasks,
            completedTasks,
            pendingTasks,
            assignments,
            projects,
            exams
        })
        }
    catch(err) {
        console.log(err);
         res.status(500).send("Error fetching stats");
        }
});

router.post("/:id/subtasks", async(req, res) => {
    try{
        if(!req.body.title || !req.body.title.trim()) {
            return res.status(400).send("Subtask title is required");
        }

        const task = await Task.findById(req.params.id);
        if(!task) {
            return res.status(404).send("Task not found");
        }

        //push new subtask
        task.subtasks.push({
            title: req.body.title
        });

        await task.save();

        res.json(task);
    } catch(err) {
        console.log(err);
        res.status(500).send("Error adding subtask");
    }
});

router.patch("/:taskId/subtasks/:subtaskId", async(req, res) => {
    try {
        console.log("BODY: ", req.body);
        console.log(typeof req.body.completed);
        const { taskId, subtaskId } = req.params;

        const task = await Task.findById(taskId);

        if(!task) {
            return res.status(404).send("task not found");
        }

        // find subtask inside array
        const subtask = task.subtasks.id(subtaskId);

        if(!subtask) {
            return res.status(404).send("subtask not found");
        }
        console.log("before: ", subtask.completed);
        // update field if provided
        if(req.body.completed !== undefined) {
            subtask.completed = req.body.completed;
        }
        console.log("after: ", subtask.completed);
        await task.save();

        res.json(task);
    } catch(err) {
        console.log(err);
        return res.status(500).send("error updating task");
    }
});

router.delete("/:taskId/subtasks/:subtaskId", async(req, res) => {
    try {
        const { taskId, subtaskId } = req.params;

        const task = await Task.findById(taskId);

        if(!task) {
            return res.status(404).send("task not found");
        }

        task.subtasks.pull(subtaskId);

        await task.save();

        res.json(task);
    } catch(err) {
        console.log(err);
        res.status(500).send("error deleting subtask");
    }
})

module.exports = router;