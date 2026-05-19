package dev.isaacaragon.taskflow.model

enum class Priority {
    LOW, MEDIUM, HIGH
}

enum class Status {
    TODO, IN_PROGRESS, DONE
}

data class Task(
    val id: Int,
    val title: String,
    val priority: Priority = Priority.MEDIUM,
    val status: Status = Status.TODO,
    val completed: Boolean = false
)
