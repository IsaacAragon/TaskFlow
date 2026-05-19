package dev.isaacaragon.taskflow.repository

import dev.isaacaragon.taskflow.model.Task
import dev.isaacaragon.taskflow.model.Priority
import dev.isaacaragon.taskflow.model.Status

class TaskRepository {
    private val tasks = mutableListOf<Task>(
        Task(1, "Task 1", Priority.LOW, Status.TODO, false),
        Task(2, "Task 2", Priority.MEDIUM, Status.DONE, true),
        Task(3, "Task 3", Priority.HIGH, Status.IN_PROGRESS, false),
        Task(4, "Task 4", Priority.LOW, Status.DONE, true),
        Task(5, "Task 5", Priority.MEDIUM, Status.TODO, false)
    )

    fun getTasks(): List<Task>  = tasks

    fun addTask(task: Task) = tasks.add(task)

    fun getTaskId(id: Int): Task? = tasks.find { it.id == id }

    fun removeTask(task: Task) = tasks.remove(task)

    fun updateTask(updatedTask: Task) {
        val index = tasks.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            tasks[index] = updatedTask
        }
    }

    fun toggleTask(task: Task) {
        val index = tasks.indexOf(task)
        if (index != -1) {
            tasks[index] = task.copy(completed = !task.completed)
        }
    }
}
