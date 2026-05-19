package dev.isaacaragon.taskflow.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.isaacaragon.taskflow.model.Priority
import dev.isaacaragon.taskflow.model.Status
import dev.isaacaragon.taskflow.model.Task
import dev.isaacaragon.taskflow.repository.TaskRepository

class TaskViewModel : ViewModel() {

    private val repository = TaskRepository()

    var tasks by mutableStateOf(listOf<Task>())
        private set

    var id by mutableStateOf("")
        private set

    var title by mutableStateOf("")
        private set

    var priority by mutableStateOf(Priority.MEDIUM)
        private set

    var status by mutableStateOf(Status.TODO)
        private set

    var completed by mutableStateOf(false)
        private set

    var isEditing by mutableStateOf(false)
        private set

    init {
        loadTasks()
    }

    fun onIdChange(newId: String) {
        id = newId
    }

    fun onTitleChange(newTitle: String) {
        title = newTitle
    }

    fun onPriorityChange(newPriority: Priority) {
        priority = newPriority
    }

    fun onStatusChange(newStatus: Status) {
        status = newStatus
    }

    fun onCompletedChange(newCompleted: Boolean) {
        completed = newCompleted
    }

    private fun loadTasks() {
        tasks = repository.getTasks()
            .sortedByDescending { it.priority } // HIGH first
            .toList()
    }

    fun loadTask(taskId: Int?) {
        if (taskId == null) {
            clearForm()
            isEditing = false
            return
        }

        val task = repository.getTaskId(taskId)
        task?.let {
            id = it.id.toString()
            title = it.title
            priority = it.priority
            status = it.status
            completed = it.completed
            isEditing = true
        }
    }

    fun addTask() {
        if (id.isNotEmpty() && title.isNotEmpty()) {
            val task = Task(
                id = id.toInt(),
                title = title,
                priority = priority,
                status = status,
                completed = completed
            )
            repository.addTask(task)
            loadTasks()
            clearForm()
        }
    }

    fun updateTask() {
        if (id.isNotEmpty() && title.isNotEmpty()) {
            val task = Task(
                id = id.toInt(),
                title = title,
                priority = priority,
                status = status,
                completed = completed
            )
            repository.updateTask(task)
            loadTasks()
            clearForm()
            isEditing = false
        }
    }

    fun removeTask(task: Task) {
        repository.removeTask(task)
        loadTasks()
    }

    fun toggleTask(task: Task) {
        repository.toggleTask(task)
        loadTasks()
    }

    fun clearForm() {
        id = ""
        title = ""
        priority = Priority.MEDIUM
        status = Status.TODO
        completed = false
        isEditing = false
    }
}